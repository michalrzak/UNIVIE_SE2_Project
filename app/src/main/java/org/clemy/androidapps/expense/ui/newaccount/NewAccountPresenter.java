package org.clemy.androidapps.expense.ui.newaccount;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.ui.BasePresenter;

public class NewAccountPresenter extends BasePresenter<NewAccountContract.View> implements NewAccountContract.Presenter {
    private Repository repository;

    @Override
    public void viewCreated() {
        repository = Repository.getInstance();
    }

    @Override
    public void createNewAccount(String accountName) {
        final Account newAccount =
                new Account(accountName, AccountType.BANK);
        repository.addAccount(newAccount);
    }
}
