package org.clemy.androidapps.expense.utils;

/**
 * Provides the state of a view as observable.
 */
public class ViewState extends ChangingDataImpl<ViewState.State> {
    public ViewState() {
        super(State.STOPPED);
    }

    /**
     * The states of a view.
     */
    public enum State {
        /**
         * View is visible and should receive notifications.
         */
        STARTED,
        /**
         * View is not visible. Notifications should be stopped.
         */
        STOPPED,
        /**
         * View got destroyed. Observers must be unregistered.
         */
        DESTROYED
    }

}
