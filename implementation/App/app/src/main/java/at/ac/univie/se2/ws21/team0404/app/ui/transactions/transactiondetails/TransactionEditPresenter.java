package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import android.util.Log;
import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class TransactionEditPresenter extends
    ABasePresenter<ITransactionActivityContract.IView> implements
    ITransactionActivityContract.IPresenter {

  private final Repository repository;

  @NonNull
  private final Transaction editing;

  @NonNull
  private final AppAccount owner;

  public TransactionEditPresenter(@NonNull AppAccount owner, @NonNull Transaction editing, Repository repository) {
    this.editing = editing;
    this.owner = owner;
    this.repository = repository;
  }

  @Override
  public void clickedSave(Transaction transaction) {
    IChangingData<ERepositoryReturnStatus> result = repository.updateTransaction(owner, editing.getId(), transaction);

    result.observe((newStatus) -> {
      switch (newStatus) {
        case SUCCESS:
          Log.d("TransactionEdit", "Successfully updated transaction");
          view.showTransactionInsertionSuccessful();
          break;
        case ERROR:
          view.showTransactionInsertionFailed();
          Log.e("TransactionNew", "Tried to add transaction, but it failed.");
          break;
        case UPDATING:
          // do nothing
      }
    });
  }

  @Override
  public void clickedDelete() {
    IChangingData<ERepositoryReturnStatus> result = repository.deleteTransaction(owner, editing.getId());

    result.observe((newStatus) -> {
      switch (newStatus) {
        case SUCCESS:
          Log.d("TransactionEdit", "Successfully deleted transaction");
          view.showTransactionDeletionSuccessful();
          break;
        case ERROR:
          view.showTransactionDeletionFailed();
          Log.e("TransactionEdit", "Tried to delete transaction, but it failed.");
          break;
        case UPDATING:
          // do nothing
      }
    });
  }
}
