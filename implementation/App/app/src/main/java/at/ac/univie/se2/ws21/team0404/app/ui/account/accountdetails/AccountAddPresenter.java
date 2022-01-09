package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class AccountAddPresenter extends ABasePresenter<IAccountActivityContract.IView> implements
    IAccountActivityContract.IPresenter {

  private final Repository repository;

  public AccountAddPresenter(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void clickedSave(AppAccount account) {
    try {
      repository.createAppAccount(account);
      view.showAccountInsertionSuccessful();
    } catch (DataExistsException e) {
      e.printStackTrace();
      view.showAccountInsertionFailed();
    }
  }

  /**
   * Not implemented as it is not needed in this view.
   * @param account to be deleted;
   */
  @Override
  public void clickedDelete(AppAccount account) {}
}
