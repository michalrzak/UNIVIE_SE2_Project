package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.IListActivityContract;

public interface ITransactionListContract {
  interface IView extends IListActivityContract.IView<Transaction> {
    void showEditAccount();
  }

  interface IPresenter extends IListActivityContract.IPresenter<Transaction> {
    void editAccount();
  }

}
