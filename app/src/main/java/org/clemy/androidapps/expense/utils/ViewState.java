package org.clemy.androidapps.expense.utils;

public class ViewState extends ChangingDataBase<ViewState.State> {
    public ViewState() {
        super(State.STOPPED);
    }

    public enum State {STARTED, STOPPED, DESTROYED}
}
