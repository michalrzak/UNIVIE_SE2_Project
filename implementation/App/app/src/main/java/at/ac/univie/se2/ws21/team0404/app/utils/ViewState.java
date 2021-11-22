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
