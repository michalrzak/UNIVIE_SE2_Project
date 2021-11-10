package org.clemy.androidapps.expense.ui.transactionlist;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.AccountWithTransactions;
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
        final ChangingDataWithViewState<AccountWithTransactions> transactionsData =
                new ChangingDataWithViewState<>(repository.getAccountWithTransactions(accountId), viewState);
        transactionsData.observe(data -> {
            if (!data.getAccount().isPresent()) {
                view.showAccountLoading();
            }
            data.getAccount().ifPresent(account ->
                    view.showAccountInformation(account.getName(), data.getOverdue()));
            view.showTransactionList(data.getTransactionsList());
        });
    }

    /**
     * Handles the user wish to create a new account.
     */
    @Override
    public void newTransaction() {
        view.showNewTransaction(accountId);
    }
}
