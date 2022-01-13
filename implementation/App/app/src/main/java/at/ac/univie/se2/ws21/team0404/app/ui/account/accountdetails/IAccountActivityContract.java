package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

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
    void clickedSave(@Nullable AppAccount account);
    void clickedDelete(@NonNull AppAccount account);
  }

}
