package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import android.util.Log;

import java.util.function.Function;

import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

public class AccountAddPresenter extends ABasePresenter<IAccountActivityContract.IView> implements
    IAccountActivityContract.IPresenter {

  private final Repository repository;

  private static Function<Repository, AccountAddPresenter> factory = AccountAddPresenter::new;

  public static AccountAddPresenter create(Repository repository){
    return factory.apply(repository);
  }

  /**
   * Allows replacing the factory for dependency injection during unit tests
   *
   * @param factory mocked factory
   */
  public static void setFactory(
          Function<Repository, AccountAddPresenter> factory) {
    AccountAddPresenter.factory = factory;
  }

  public AccountAddPresenter(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void clickedSave(@Nullable AppAccount account) {
    if (account == null) {
      Log.d("AccountAdd", "Tried to save with invalid account");
      view.showAccountInsertionFailed();
      return;
    }

    IChangingData<ERepositoryReturnStatus> result = repository.createAppAccount(account);

    result.observe((newStatus) -> {
      switch (newStatus) {
        case SUCCESS:
          Log.d("AccountAdd", "Inserting new account successful");
          view.showAccountInsertionSuccessful();
          break;
        case ERROR:
          Log.d("AccountAdd", "Inserting new account failed");
          view.showAccountInsertionFailed();
          break;
        case UPDATING:
          // do nothing
      }
    });
  }

  /**
   * Not implemented as it is not needed in this view.
   *
   * @param account to be deleted;
   */
  @Override
  public void clickedDelete(@NonNull AppAccount account) {
  }
}
