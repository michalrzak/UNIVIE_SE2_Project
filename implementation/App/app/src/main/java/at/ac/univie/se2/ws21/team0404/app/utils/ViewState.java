package at.ac.univie.se2.ws21.team0404.app.utils;

/**
 * Provides the state of a view as observable.
 */
public class ViewState extends ChangingData<ViewState.EState> {

  public ViewState() {
    super(EState.STOPPED);
  }

  /**
   * The states of a view.
   */
  public enum EState {
    /**
     * IView is visible and should receive notifications.
     */
    STARTED,
    /**
     * IView is not visible. Notifications should be stopped.
     */
    STOPPED,
    /**
     * IView got destroyed. Observers must be unregistered.
     */
    DESTROYED
  }

}
