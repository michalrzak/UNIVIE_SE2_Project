package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivityPresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataWithViewState;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import java.util.List;
import java.util.function.BiFunction;

public class TransactionListPresenter extends AListActivityPresenter<Transaction> implements
    ITransactionListContract.IPresenter {

  private final AppAccount owner;

  private static BiFunction<AppAccount, Repository, TransactionListPresenter> factory = TransactionListPresenter::new;

  public static TransactionListPresenter create(AppAccount owner, Repository repository) {
    return factory.apply(owner, repository);
  }

  /**
   * Allows replacing the factory for dependency injection during unit tests
   *
   * @param factory mocked factory
   */
  public static void setFactory(
      BiFunction<AppAccount, Repository, TransactionListPresenter> factory) {
    TransactionListPresenter.factory = factory;
  }

  private TransactionListPresenter(AppAccount owner, Repository repository) {
    super(repository);
    this.owner = owner;
  }

  /**
   * This needs to be implemented to provide the correct data for the list
   */
  @Override
  public void viewCreated() {
    final IChangingData<List<Transaction>> transactionData = new ChangingDataWithViewState<>(
        repository.getTransactionList(owner), viewState);
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
