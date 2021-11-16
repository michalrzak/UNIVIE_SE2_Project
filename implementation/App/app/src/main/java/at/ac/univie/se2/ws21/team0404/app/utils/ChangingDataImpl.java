package at.ac.univie.se2.ws21.team0404.app.utils;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Basic implementation of the {@link ChangingData} interface. Wraps data into an observable container.
 * See {@link ChangingData} for a detailed description.
 *
 * @param <T> data type to be wrapped
 */
public class ChangingDataImpl<T> implements ChangingData<T> {
    private final Set<Observer<T>> observers = new HashSet<>();
    private T data;

    /**
     * Constructs {@link ChangingDataImpl} with {@code null} data.
     */
    public ChangingDataImpl() {
    }

    /**
     * Constructs {@link ChangingDataImpl} and sets initial data.
     *
     * @param data initial data to set.
     */
    public ChangingDataImpl(T data) {
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
