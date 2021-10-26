package org.clemy.androidapps.expense.ui;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.utils.ViewState;

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {
    protected final ViewState viewState = new ViewState();
    protected V view;

    @Override
    public final void setView(@NonNull V view) {
        this.view = view;
    }

    @Override
    public final void onCreate() {
        viewState.setData(ViewState.State.STOPPED);
        viewCreated();
    }

    @Override
    public final void onStart() {
        viewStarted();
        viewState.setData(ViewState.State.STARTED);
    }

    @Override
    public final void onStop() {
        viewState.setData(ViewState.State.STOPPED);
        viewBeforeStop();
    }

    @Override
    public final void onDestroy() {
        viewState.setData(ViewState.State.DESTROYED);
        viewBeforeDestroy();
        this.view = null;
    }

    // empty default implementations
    // override in Presenter if necessary
    @Override
    public void viewCreated() {
    }

    @Override
    public void viewStarted() {
    }

    @Override
    public void viewBeforeStop() {
    }

    @Override
    public void viewBeforeDestroy() {
    }
}
