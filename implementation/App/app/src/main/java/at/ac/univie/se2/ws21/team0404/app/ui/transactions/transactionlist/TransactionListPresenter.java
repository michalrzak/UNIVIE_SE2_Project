package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivityPresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.IListActivityContract;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist.IAccountListContract;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataWithViewState;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import java.util.List;

public class TransactionListPresenter extends AListActivityPresenter<Transaction> implements
    ITransactionListContract.IPresenter {

  private AppAccount owner;

  public TransactionListPresenter(AppAccount owner, Repository repository) {
    super(repository);
    this.owner = owner;
  }

  /**
   * This needs to be implemented to provide the correct data for the list
   */
  @Override
  public void viewCreated() {
    final IChangingData<List<Transaction>> transactionData;
    try {
      transactionData = new ChangingDataWithViewState<>(repository.getTransactionList(owner), viewState);
    } catch (DataDoesNotExistException e) {
      // cannot call finnish() from here :/. So for now just crash;
      throw new RuntimeException("Goodbye. " + e.getMessage());
    }
    transactionData.observe(data -> view.showList(data));
  }

  @Override
  public void editAccount() {
    //TODO: Surely this can be done better
    assert (view instanceof ITransactionListContract.IView);
    ITransactionListContract.IView myView = (ITransactionListContract.IView) view;
    myView.showEditAccount();
  }
}
