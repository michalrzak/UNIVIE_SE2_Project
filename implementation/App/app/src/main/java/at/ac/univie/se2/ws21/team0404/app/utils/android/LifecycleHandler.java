package at.ac.univie.se2.ws21.team0404.app.utils.android;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import at.ac.univie.se2.ws21.team0404.app.ui.BaseContract;

/**
 * Automates connecting a presenter to Android views and
 * calling the {@link BaseContract.PresenterLifecycle} interface according
 * the Android {@link Lifecycle}. This is normally instantiated by the view.
 *
 * @param <V> an Android view with a {@link Lifecycle} and
 *            implementing the {@link BaseContract.View} interface.
 */
public class LifecycleHandler<V extends LifecycleOwner & BaseContract.View> {
    /**
     * Connects a presenter with an Android view.
     *
     * @param presenter A presenter instance for the view.
     * @param view      An Android view with a {@link Lifecycle}.
     */
    public LifecycleHandler(@NonNull BaseContract.Presenter<? super V> presenter, @NonNull V view) {
        presenter.setView(view);
        view.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            void OnCreate(LifecycleOwner source) {
                presenter.onCreate();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            void OnStart(LifecycleOwner source) {
                presenter.onStart();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            void OnStop(LifecycleOwner source) {
                presenter.onStop();
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            void OnDestroy(LifecycleOwner source) {
                presenter.onDestroy();
            }
        });
    }
}
