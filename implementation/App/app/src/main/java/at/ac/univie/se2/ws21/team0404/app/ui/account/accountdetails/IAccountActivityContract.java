package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;

public interface IAccountActivityContract {

  /**
   * As far as I can tell this does not need any methods
   */
  interface IView extends IBaseContract.IView {
    void showAccountInsertionSuccessful();
    void showAccountInsertionFailed();

    void showAccountDeletionSuccessful();
    void showAccountDeletionFailed();
  }

  interface IPresenter extends IBaseContract.IPresenter<IView> {
    void clickedSave(AppAccount account);
    void clickedDelete(AppAccount account);
  }

}
