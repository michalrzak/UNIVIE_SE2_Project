package org.clemy.androidapps.expense.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

public class ChangingDataWithViewState<T> extends ChangingDataDecorator<T> {
    private static final String TAG = "ChangingDataViewState";
    private final Set<Observer<T>> lifecycleObservers = new HashSet<>();
    private final ViewState viewState;

    public ChangingDataWithViewState(@NonNull ChangingData<T> changingData, @NonNull ViewState viewState) {
        super(changingData);
        this.viewState = viewState;
        viewState.observe(state -> {
            switch (state) {
                case STARTED:
                    Log.d(TAG, "Started");
                    observeAll();
                    break;
                case STOPPED:
                    Log.d(TAG, "Stopped");
                    unobserveAll();
                    break;
                case DESTROYED:
                    Log.d(TAG, "Destroyed");
                    unobserveAllAndClear();
                    break;
            }
        });
    }

    @Override
    public synchronized void observe(@NonNull Observer<T> observer) {
        Log.d(TAG, "observe " + observer);
        lifecycleObservers.add(observer);
        if (viewState.getData() == ViewState.State.STARTED) {
            super.observe(observer);
        }
    }

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
