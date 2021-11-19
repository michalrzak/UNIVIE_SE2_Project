package at.ac.univie.se2.ws21.team0404.app.ui;

import androidx.annotation.NonNull;

import at.ac.univie.se2.ws21.team0404.app.utils.ViewState;

/**
 * An abstract implementation of the presenter base interface {@link BaseContract.Presenter} providing
 * basic functionality to store the {@link #view} and clean up the reference on view destruction.
 * <p>
 * Further it stores the {@link #viewState} as an observable, which can be used with the
 * {@link at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataWithViewState ChangingDataWithViewState}
 * decorator for the {@link at.ac.univie.se2.ws21.team0404.app.utils.ChangingData ChangingData} interface.
 * This allows stopping notifying
 * {@link at.ac.univie.se2.ws21.team0404.app.utils.ChangingData.Observer ChangingData.Observer} if the view is not
 * visible and also removes all observers if the view gets destroyed to prevent leaks.
 *
 * @param <V> the MVP {@link BaseContract.View} interface this presenter gets connected to.
 */
public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {
    /**
     * The state of the linked view available for subclasses. This is an observable.
     */
    protected final ViewState viewState = new ViewState();
    /**
     * The linked view available for subclasses.
     */
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
