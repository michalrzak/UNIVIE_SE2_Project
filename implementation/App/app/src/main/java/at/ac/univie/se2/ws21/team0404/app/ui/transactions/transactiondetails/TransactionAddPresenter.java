package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import android.util.Log;
import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import java.util.function.BiFunction;

public class TransactionAddPresenter extends
    ABasePresenter<ITransactionActivityContract.IView> implements
    ITransactionActivityContract.IPresenter {

  private final Repository repository;

  private static BiFunction<AppAccount, Repository, TransactionAddPresenter> factory = TransactionAddPresenter::new;

  public static TransactionAddPresenter create(AppAccount owner, Repository repository) {
    return factory.apply(owner, repository);
  }

  /**
   * Allows replacing the factory for dependency injection during unit tests
   *
   * @param factory mocked factory
   */
  public static void setFactory(
      BiFunction<AppAccount, Repository, TransactionAddPresenter> factory) {
    TransactionAddPresenter.factory = factory;
  }

  @NonNull
  private final AppAccount owner;

  private TransactionAddPresenter(@NonNull AppAccount owner, Repository repository) {
    this.owner = owner;
    this.repository = repository;
  }

  @Override
  public void clickedSave(Transaction transaction) {
    IChangingData<ERepositoryReturnStatus> result = repository.createTransaction(owner, transaction);

    result.observe((newStatus) -> {
      switch (newStatus) {
        case SUCCESS:
          Log.d("TransactionAdd", "Successfully updated transaction");
          view.showTransactionInsertionSuccessful();
          break;
        case ERROR:
          Log.e("TransactionAdd", "Tried to add transaction, but it failed.");
          view.showTransactionInsertionFailed();
          break;
        case UPDATING:
          // do nothing
      }
    });
  }

  /**
   * Not implemented as it is not needed.
   */
  @Override
  public void clickedDelete() {}
}
