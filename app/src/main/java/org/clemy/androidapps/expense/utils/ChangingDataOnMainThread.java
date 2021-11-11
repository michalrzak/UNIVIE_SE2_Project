package org.clemy.androidapps.expense.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;

import java.util.HashMap;
import java.util.Map;

/**
 * Decorator for {@link ChangingData} adding the functionality to call observers always on the main thread.
 *
 * @param <T> type of data to be wrapped
 */
public class ChangingDataOnMainThread<T> extends ChangingDataDecorator<T> {
    private static final String TAG = "ChangingDataViewState";
    private final Map<Observer<T>, Observer<T>> observerMap = new HashMap<>();
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    /**
     * Constructs a {@link ChangingDataOnMainThread} decorator for the provided {@link ChangingData}.
     *
     * @param changingData the {@link ChangingData} interface to be decorated.
     */
    public ChangingDataOnMainThread(@NonNull ChangingData<T> changingData) {
        super(changingData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void observe(@NonNull Observer<T> observer) {
        //Log.d(TAG, "observe " + observer);
        Observer<T> newObserver = observerMap.get(observer);
        if (newObserver == null) {
            newObserver = data -> mainThreadHandler.post(() -> observer.changed(data));
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
}
