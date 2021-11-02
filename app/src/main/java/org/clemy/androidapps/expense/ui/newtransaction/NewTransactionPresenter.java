package org.clemy.androidapps.expense.ui.newtransaction;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.ui.BasePresenter;

public class NewTransactionPresenter extends BasePresenter<NewTransactionContract.View> implements NewTransactionContract.Presenter {
    private final Repository repository;
    private final Integer accountId;

    public NewTransactionPresenter(Repository repository, Integer accountId) {
        this.repository = repository;
        this.accountId = accountId;
    }

    @Override
    public void createNewTransaction(String transactionName) {
        final Transaction newTransaction =
                new Transaction(accountId, transactionName, 1.2);
        repository.addTransaction(newTransaction);
    }
}
