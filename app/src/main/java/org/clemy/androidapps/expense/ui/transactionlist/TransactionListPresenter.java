package org.clemy.androidapps.expense.ui.transactionlist;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.TransactionList;
import org.clemy.androidapps.expense.ui.BasePresenter;
import org.clemy.androidapps.expense.utils.ChangingDataWithViewState;

/**
 * The Presenter for the AccountList activity.
 */
public class TransactionListPresenter
        extends BasePresenter<TransactionListContract.View>
        implements TransactionListContract.Presenter {

    private final Repository repository;
    private final Integer accountId;

    /**
     * @param repository the data repository for reading and updating the data.
     */
    public TransactionListPresenter(Repository repository, Integer accountId) {
        this.repository = repository;
        this.accountId = accountId;
    }

    /**
     * Setups the model -> view connection.
     */
    @Override
    public void viewCreated() {
        final ChangingDataWithViewState<TransactionList> transactionsData =
                new ChangingDataWithViewState<>(repository.getTransactionsForAccount(accountId), viewState);
        transactionsData.observe(data -> view.showTransactionList(data));
    }

    /**
     * Handles the user wish to create a new account.
     */
    @Override
    public void newTransaction() {
        view.showNewTransaction(accountId);
    }
}
