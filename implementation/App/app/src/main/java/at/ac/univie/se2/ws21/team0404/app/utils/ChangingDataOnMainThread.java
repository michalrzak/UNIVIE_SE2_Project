package at.ac.univie.se2.ws21.team0404.app.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Decorator for {@link ChangingData} adding the functionality to call observers always on the main
 * thread.
 *
 * @param <T> type of data to be wrapped
 */
public class ChangingDataOnMainThread<T> extends ChangingDataDecorator<T> {

  private static MainThreadExecutor mainThreadExecutor = new MainThreadExecutor();
  private final Map<Observer<T>, Observer<T>> observerMap = new HashMap<>();

  /**
   * Constructs a {@link ChangingDataOnMainThread} decorator for the provided {@link ChangingData}.
   *
   * @param changingData the {@link ChangingData} interface to be decorated.
   */
  public ChangingDataOnMainThread(@NonNull ChangingData<T> changingData) {
    super(changingData);
  }

  /**
   * Sets the platform specific main thread executor. If not set, it will execute on the same thread
   * as called.
   *
   * @param mainThreadExecutor the new main thread executor. If null it will reset to default.
   */
  public static void setMainThreadExecutor(MainThreadExecutor mainThreadExecutor) {
    if (mainThreadExecutor == null) {
      ChangingDataOnMainThread.mainThreadExecutor = new MainThreadExecutor();
    } else {
      ChangingDataOnMainThread.mainThreadExecutor = mainThreadExecutor;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void observe(@NonNull Observer<T> observer) {
    Observer<T> newObserver = observerMap.get(observer);
    if (newObserver == null) {
      newObserver = data -> mainThreadExecutor.runOnMainThread(() -> observer.changed(data));
      observerMap.put(observer, newObserver);
    }
    super.observe(newObserver);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void unobserve(@NonNull Observer<T> observer) {
    Observer<T> internalObserver = observerMap.get(observer);
    if (internalObserver != null) {
      super.unobserve(internalObserver);
    }
    observerMap.remove(observer);
  }

  /**
   * A default executor which is used if no other executor is set. It will always execute on the
   * same thread.
   */
  public static class MainThreadExecutor {

    public void runOnMainThread(Runnable runnable) {
      runnable.run();
    }
  }
}
