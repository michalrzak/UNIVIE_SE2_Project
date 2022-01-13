package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

public interface ITransactionActivityContract {
  interface IView extends IBaseContract.IView {
    void showTransactionInsertionSuccessful();
    void showTransactionInsertionFailed();

    void showTransactionDeletionSuccessful();
    void showTransactionDeletionFailed();
  }

  interface IPresenter extends IBaseContract.IPresenter<IView> {
    void clickedSave(@Nullable Transaction transaction);
    void clickedDelete();
  }

}
