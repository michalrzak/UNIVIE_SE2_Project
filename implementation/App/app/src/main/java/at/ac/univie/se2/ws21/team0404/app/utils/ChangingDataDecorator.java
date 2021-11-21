package at.ac.univie.se2.ws21.team0404.app.utils;

/**
 * Abstract decorator implementation for {@link ChangingData}. To be used to implement decorators
 * for implementations of the interface, mainly for {@link ChangingDataImpl}. This can be used to
 * add features to the basic implementation.
 * <p>
 * See {@link ChangingDataWithViewState} and {@link ChangingDataOnMainThread} for use cases.
 *
 * @param <T> data to be wrapped
 */
abstract class ChangingDataDecorator<T> implements ChangingData<T> {

  @NonNull
  protected final ChangingData<T> decoratedChangingData;

  /**
   * Constructs a decorator for {@link ChangingData}.
   *
   * @param changingData the {@link ChangingData} which should be decorated.
   */
  protected ChangingDataDecorator(@NonNull ChangingData<T> changingData) {
    this.decoratedChangingData = changingData;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public T getData() {
    return decoratedChangingData.getData();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setData(T data) {
    decoratedChangingData.setData(data);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void observe(@NonNull Observer<T> observer) {
    decoratedChangingData.observe(observer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unobserve(@NonNull Observer<T> observer) {
    decoratedChangingData.unobserve(observer);
  }
}
