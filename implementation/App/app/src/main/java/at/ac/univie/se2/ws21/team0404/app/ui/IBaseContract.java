package at.ac.univie.se2.ws21.team0404.app.ui;

import androidx.annotation.NonNull;

/**
 * Contract interface for all MVP classes containing the base interfaces for views and presenter.
 */
public interface IBaseContract {

  /**
   * Contract interface for all MVP view classes.
   */
  interface IView {

  }

  /**
   * Interface to inform the presenter about the view lifecycle.
   *
   * @param <V> an MVP {@link IView} interface.
   */
  interface IPresenterLifecycle<V extends IView> {

    void setView(@NonNull V view);

    void onCreate();

    void onStart();

    void onStop();

    void onDestroy();
  }

  /**
   * Interface for presenter implementations to get informed about lifecycle changes.
   *
   * @param <V> an MVP {@link IView} interface.
   */
  interface IPresenter<V extends IView> extends IPresenterLifecycle<V> {

    void viewCreated();

    void viewStarted();

    void viewBeforeStop();

    void viewBeforeDestroy();
  }
}
