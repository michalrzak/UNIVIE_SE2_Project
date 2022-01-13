package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails.IAccountActivityContract.IView;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class AccountEditPresenter extends ABasePresenter<IView> implements
    IAccountActivityContract.IPresenter{

  private final Repository repository;

  public AccountEditPresenter(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void clickedSave(AppAccount account) {
      IChangingData<ERepositoryReturnStatus> result = repository.updateAppAccount(account);

      result.observe((newStatus) -> {
        switch (newStatus) {
          case SUCCESS:
            Log.d("AccountEdit", "Inserting new account successful");
            view.showAccountInsertionSuccessful();
            break;
          case ERROR:
            Log.d("AccountEdit", "Inserting new account failed");
            view.showAccountInsertionFailed();
            break;
          case UPDATING:
            // do nothing
        }
      });
  }

  @Override
  public void clickedDelete(AppAccount account) {
    IChangingData<ERepositoryReturnStatus> result = repository.deleteAppAccount(account);

    result.observe((newStatus) -> {
      switch (newStatus) {
        case SUCCESS:
          view.showAccountDeletionSuccessful();
          break;
        case ERROR:
          view.showAccountDeletionFailed();
          break;
        case UPDATING:
          // do nothing
      }
    });
  }
}
