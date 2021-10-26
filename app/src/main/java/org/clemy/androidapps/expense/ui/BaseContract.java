package org.clemy.androidapps.expense.ui;

import androidx.annotation.NonNull;

/**
 * Contract interface for all MVP classes containing the base interfaces for views and presenter.
 */
public interface BaseContract {
    /**
     * Contract interface for all MVP view classes.
     */
    interface View {
    }

    /**
     * Interface to inform the presenter about the view lifecycle.
     *
     * @param <V> an MVP {@link View} interface.
     */
    interface PresenterLifecycle<V extends View> {
        void setView(@NonNull V view);

        void onCreate();

        void onStart();

        void onStop();

        void onDestroy();
    }

    /**
     * Interface for presenter implementations to get informed about lifecycle changes.
     *
     * @param <V> an MVP {@link View} interface.
     */
    interface Presenter<V extends View> extends PresenterLifecycle<V> {
        void viewCreated();

        void viewStarted();

        void viewBeforeStop();

        void viewBeforeDestroy();
    }
}
