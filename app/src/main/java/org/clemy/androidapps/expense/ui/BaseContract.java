package org.clemy.androidapps.expense.ui;

import androidx.annotation.NonNull;

public interface BaseContract {
    interface View {
    }

    interface PresenterLifecycle<V extends View> {
        void setView(@NonNull V view);

        void onCreate();

        void onStart();

        void onStop();

        void onDestroy();
    }

    interface Presenter<V extends View> extends PresenterLifecycle<V> {
        void viewCreated();

        void viewStarted();

        void viewBeforeStop();

        void viewBeforeDestroy();
    }
}
