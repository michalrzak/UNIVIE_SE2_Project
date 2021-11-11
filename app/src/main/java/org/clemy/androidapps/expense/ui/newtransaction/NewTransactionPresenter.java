package org.clemy.androidapps.expense.ui.newtransaction;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.ui.BasePresenter;

public class NewTransactionPresenter extends BasePresenter<NewTransactionContract.View> implements NewTransactionContract.Presenter {
    @NonNull
    private final Repository repository;
    @NonNull
    private final Integer accountId;

    public NewTransactionPresenter(@NonNull Repository repository, @NonNull Integer accountId) {
        this.repository = repository;
        this.accountId = accountId;
    }

    @Override
    public void createNewTransaction(String transactionName) {
        final Transaction newTransaction =
                new Transaction(accountId, transactionName, 1.2);
        repository.reloadTransaction(newTransaction);
    }
}
