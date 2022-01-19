package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Function;

import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails.IAccountActivityContract.IView;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class AccountEditPresenter extends ABasePresenter<IView> implements
    IAccountActivityContract.IPresenter{

  private final Repository repository;

  private static Function<Repository, AccountEditPresenter> factory = AccountEditPresenter::new;

  public static AccountEditPresenter create(Repository repository){
    return factory.apply(repository);
  }

  public AccountEditPresenter(Repository repository) {
    this.repository = repository;
  }

  /**
   * Allows replacing the factory for dependency injection during unit tests
   *
   * @param factory mocked factory
   */
  public static void setFactory(
          Function<Repository, AccountEditPresenter> factory) {
    AccountEditPresenter.factory = factory;
  }


  @Override
  public void clickedSave(@Nullable AppAccount account) {
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
  public void clickedDelete(@NonNull AppAccount account) {
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
