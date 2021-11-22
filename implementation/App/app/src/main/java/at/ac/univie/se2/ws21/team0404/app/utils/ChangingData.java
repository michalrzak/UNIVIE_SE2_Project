package at.ac.univie.se2.ws21.team0404.app.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Basic implementation of the {@link IChangingData} interface. Wraps data into an observable
 * container. See {@link IChangingData} for a detailed description.
 *
 * @param <T> data type to be wrapped
 */
public class ChangingData<T> implements IChangingData<T> {

  private final Set<Observer<T>> observers = new HashSet<>();
  private T data;

  /**
   * Constructs {@link ChangingData} with {@code null} data.
   */
  public ChangingData() {
  }

  /**
   * Constructs {@link ChangingData} and sets initial data.
   *
   * @param data initial data to set.
   */
  public ChangingData(T data) {
    this.data = data;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized T getData() {
    return data;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void setData(T data) {
    this.data = data;
    for (Observer<T> observer : observers) {
      observer.changed(this.data);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void observe(@NonNull Observer<T> observer) {
    observers.add(observer);
    observer.changed(this.data);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void unobserve(@NonNull Observer<T> observer) {
    observers.remove(observer);
  }
}
