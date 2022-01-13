package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import android.util.Log;
import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class TransactionAddPresenter extends
    ABasePresenter<ITransactionActivityContract.IView> implements
    ITransactionActivityContract.IPresenter {

  private final Repository repository;

  @NonNull
  private final AppAccount owner;

  public TransactionAddPresenter(@NonNull AppAccount owner, Repository repository) {
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
