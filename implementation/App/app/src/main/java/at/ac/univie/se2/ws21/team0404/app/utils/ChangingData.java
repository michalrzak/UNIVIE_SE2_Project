package at.ac.univie.se2.ws21.team0404.app.utils;

/**
 * Wraps data in an observable container. Observers implementing {@link Observer} will be notified
 * in case the data changes. Notifications will also be sent on initial registration of an observer.
 * <p>
 * Do not forget to {@link #unobserve(Observer)} to prevent memory leaks. Tip: For convenience the
 * {@link ChangingDataWithViewState} decorator can be used to automatically remove observers on
 * view destruction and also pause notifications for invisible views.
 *
 * @param <T> data type to be wrapped
 */
// Observer Pattern: notifies observer if data changes
public interface ChangingData<T> {
    /**
     * @return the current data
     */
    T getData();

    /**
     * Changes the data and notifies all observers.
     *
     * @param data the new data
     */
    void setData(T data);

    /**
     * Registers a new observer. The observer is called once initially with the current data.
     *
     * @param observer the observer to be registered. A lambda expression can be used.
     */
    void observe(@NonNull Observer<T> observer);

    /**
     * Unregisters an observer. This is necessary to prevent memory leaks. Consider using the
     * {@link ChangingDataWithViewState} decorator.
     *
     * @param observer the observer to be unregistered.
     */
    void unobserve(@NonNull Observer<T> observer);

    /**
     * Interface to be implemented by observers. You can use lambda expressions for easy use.
     *
     * @param <TObserve> data type to be wrapped
     */
    interface Observer<TObserve> {
        /**
         * Called if data changes. also called during {@link #observe(Observer)}.
         *
         * @param data the new data
         */
        void changed(TObserve data);
    }
}
