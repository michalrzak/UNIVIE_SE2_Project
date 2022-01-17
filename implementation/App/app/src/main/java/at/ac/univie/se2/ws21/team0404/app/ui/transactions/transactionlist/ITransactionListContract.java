package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.IListActivityContract;

/**
 * The contract to be used with {@link TransactionList} and {@link TransactionListPresenter}
 */
public interface ITransactionListContract {

  interface IView extends IListActivityContract.IView<Transaction> {

    /**
     * Tell the view to show the {@link at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails.AccountEdit}
     * activity
     */
    void showEditAccount();

    /**
     * Tell the view to finish()
     */
    void finishActivity();
  }

  interface IPresenter extends IListActivityContract.IPresenter<Transaction> {

    /**
     * Tell the presenter that the edit account was pressed
     */
    void editAccount();

    /**
     * Tell the presenter that the passed account was deleted
     */
    void accountDeleted();
  }

}
