package at.ac.univie.se2.ws21.team0404.app.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Decorator for {@link ChangingData} adding the functionality to be aware of the view state
 * according {@link ViewState}.
 * <p>
 * It stops notifications in case the view state gets set to {@link ViewState.State#STOPPED STOPPED}
 * and resumes notifications if the view state gets set to {@link ViewState.State#STARTED STARTED}.
 * It also sends a notification with the current data during the transition to {@link
 * ViewState.State#STARTED STARTED}.
 * <p>
 * If the view state gets set to {@link ViewState.State#DESTROYED DESTROYED} the observers get
 * unregistered to prevent memory leaks.
 *
 * @param <T> type of data to be wrapped
 */
public class ChangingDataWithViewState<T> extends ChangingDataDecorator<T> {

  private final Set<Observer<T>> lifecycleObservers = new HashSet<>();
  private final ViewState viewState;

  /**
   * Constructs a {@link ChangingDataWithViewState} decorator for the provided {@link ChangingData}
   * and links it to the provided {@link ViewState}.
   *
   * @param changingData the {@link ChangingData} interface to be decorated.
   * @param viewState    the {@link ViewState} of the observer.
   */
  public ChangingDataWithViewState(@NonNull ChangingData<T> changingData,
      @NonNull ViewState viewState) {
    super(changingData);
    this.viewState = viewState;
    viewState.observe(state -> {
      switch (state) {
        case STARTED:
          observeAll();
          break;
        case STOPPED:
          unobserveAll();
          break;
        case DESTROYED:
          unobserveAllAndClear();
          break;
      }
    });
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void observe(@NonNull Observer<T> observer) {
    lifecycleObservers.add(observer);
    if (viewState.getData() == ViewState.State.STARTED) {
      super.observe(observer);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void unobserve(@NonNull Observer<T> observer) {
    super.unobserve(observer);
    lifecycleObservers.remove(observer);
  }

  private synchronized void observeAll() {
    for (Observer<T> observer : lifecycleObservers) {
      super.observe(observer);
    }
  }

  private synchronized void unobserveAll() {
    for (Observer<T> observer : lifecycleObservers) {
      super.unobserve(observer);
    }
  }

  private synchronized void unobserveAllAndClear() {
    unobserveAll();
    lifecycleObservers.clear();

  }
}
