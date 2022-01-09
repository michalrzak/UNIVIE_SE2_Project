package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import androidx.recyclerview.widget.RecyclerView;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails.IAccountActivityContract.IView;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class AccountEditPresenter extends ABasePresenter<IView> implements
    IAccountActivityContract.IPresenter{

  private final Repository repository;

  public AccountEditPresenter(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void clickedSave(AppAccount account) {
    try {
      repository.updateAppAccount(account);
      view.showAccountInsertionSuccessful();
    } catch (DataDoesNotExistException e) {
      e.printStackTrace();
      view.showAccountInsertionFailed();
    }
  }

  @Override
  public void clickedDelete(AppAccount account) {
    try {
      repository.deleteAppAccount(account);
      view.showAccountDeletionSuccessful();
    } catch (DataDoesNotExistException e) {
      view.showAccountDeletionFailed();
    }
  }
}
