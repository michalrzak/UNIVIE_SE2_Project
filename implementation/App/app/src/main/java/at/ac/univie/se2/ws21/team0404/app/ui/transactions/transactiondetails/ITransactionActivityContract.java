package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

public interface ITransactionActivityContract {

  interface IView extends IBaseContract.IView {

    /**
     * Tell the view to display that the transaction insertion was successful
     */
    void showTransactionInsertionSuccessful();

    /**
     * Tell the view to displayed that the transaction insertion failed
     */
    void showTransactionInsertionFailed();


    /**
     * Tell the view to display that the transaction deletion was successful
     */
    void showTransactionDeletionSuccessful();

    /**
     * Tell the view to display that the transaction deletion failed
     */
    void showTransactionDeletionFailed();
  }

  interface IPresenter extends IBaseContract.IPresenter<IView> {

    /**
     * Tell the presenter that the save button was pressed
     * @param transaction the transaction which was displayed on the activity
     */
    void clickedSave(@Nullable Transaction transaction);

    /**
     * Tell the presenter that the delete button was pressed
     */
    void clickedDelete();
  }

}
