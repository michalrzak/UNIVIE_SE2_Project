package org.clemy.androidapps.expense.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.HashSet;
import java.util.Set;

public class ChangingDataWithLifecycle<T> extends ChangingDataDecorator<T> {
    private static final String TAG = "ChangingDataWLifecycle";
    private final Set<Observer<T>> lifecycleObservers = new HashSet<>();

    public ChangingDataWithLifecycle(@NonNull ChangingData<T> changingData, @NonNull Lifecycle lifecycle) {
        super(changingData);
        lifecycle.addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            void OnStart(LifecycleOwner source) {
                Log.d(TAG, "OnStart");
                for (Observer<T> observer : lifecycleObservers) {
                    changingData.observe(observer);
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            void OnStop(LifecycleOwner source) {
                Log.d(TAG, "OnStop");
                for (Observer<T> observer : lifecycleObservers) {
                    changingData.unobserve(observer);
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            void OnDestroy(LifecycleOwner source) {
                Log.d(TAG, "OnDestroy");
                for (Observer<T> observer : lifecycleObservers) {
                    changingData.unobserve(observer);
                }
                lifecycleObservers.clear();
            }
        });
    }

    @Override
    public void observe(@NonNull Observer<T> observer) {
        Log.d(TAG, "observe " + observer);
        lifecycleObservers.add(observer);
        super.observe(observer);
    }

    @Override
    public void unobserve(@NonNull Observer<T> observer) {
        super.unobserve(observer);
        lifecycleObservers.remove(observer);
    }
}
