package org.clemy.androidapps.expense.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

public class ChangingDataBase<T> implements ChangingData<T> {
    private static final String TAG = "ChangingDataImpl";

    private T data;
    private final Set<Observer<T>> observers = new HashSet<>();

    public ChangingDataBase() {
    }

    public ChangingDataBase(T data) {
        this.data = data;
    }

    @Override
    public synchronized T getData() {
        return data;
    }
    @Override
    public synchronized void setData(T data) {
        this.data = data;
        for (Observer<T> observer: observers) {
            observer.changed(this.data);
        }
    }

    @Override
    public synchronized void observe(@NonNull Observer<T> observer) {
        final boolean result = observers.add(observer);
        Log.d(TAG, "observe " + observer + " result: " + result);
        observer.changed(this.data);
    }
    @Override
    public synchronized void unobserve(@NonNull Observer<T> observer) {
        final boolean result = observers.remove(observer);
        Log.d(TAG, "unobserve " + observer + " result: " + result);
    }
}
