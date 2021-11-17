package org.clemy.androidapps.expense.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Automates connecting a presenter to Android views and
 * calling the {@link BaseContract.PresenterLifecycle} interface according
 * the Android {@link Lifecycle}. This is normally instantiated by the view.
 *
 * @param <V> an Android with a {@link Lifecycle} and
 *           implementing the {@link BaseContract.View} interface.
 */
public class LifecycleHandler<V extends LifecycleOwner & BaseContract.View> {
    private static final String TAG = "LifecycleHandler";

    /**
     * Connects a presenter with an Android view.
     *
     * @param presenter A presenter instance for the view.
     * @param view An Android view with a {@link Lifecycle}.
     */
    public LifecycleHandler(@NonNull BaseContract.Presenter<? super V> presenter, @NonNull V view) {
        presenter.setView(view);
        view.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            void OnCreate(LifecycleOwner source) {
                Log.d(TAG, "OnCreate");
                presenter.onCreate();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            void OnStart(LifecycleOwner source) {
                Log.d(TAG, "OnStart");
                presenter.onStart();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            void OnStop(LifecycleOwner source) {
                Log.d(TAG, "OnStop");
                presenter.onStop();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            void OnDestroy(LifecycleOwner source) {
                Log.d(TAG, "OnDestroy");
                presenter.onDestroy();
            }
        });
    }
}