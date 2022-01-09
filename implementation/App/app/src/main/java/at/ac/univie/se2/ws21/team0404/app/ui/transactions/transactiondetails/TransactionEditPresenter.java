package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import android.util.Log;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
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
    try {
      repository.updateTransaction(owner, editing.getId(), transaction);
      Log.d("TransactionEdit", "Successfully updated transaction");
      view.showTransactionInsertionSuccessful();
    } catch (DataDoesNotExistException e) {
      Log.e("TransactionEdit",
          "Tried to update a transaction from an account which does not exist. Message: "
              + e.getMessage());
      view.showTransactionInsertionFailed();
    }
  }

  @Override
  public void clickedDelete() {
    try {
      repository.deleteTransaction(owner, editing.getId());
      Log.d("TransactionEdit", "Successfully deleted transaction");
      view.showTransactionDeletionSuccessful();
    } catch (DataDoesNotExistException e) {
      Log.e("TransactionEdit",
          "Tried to delete a transaction from an account which does not exist or the"
              + "to be deleted transaction does not exists. Message: "
              + e.getMessage());
      view.showTransactionDeletionFailed();
    }
  }
}
