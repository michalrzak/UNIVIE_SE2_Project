package org.clemy.androidapps.expense.ui.newaccount;

import android.util.Log;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.ui.BasePresenter;

import java.util.Arrays;

public class NewAccountPresenter extends BasePresenter<NewAccountContract.View> implements NewAccountContract.Presenter {
    private Repository repository;

    @Override
    public void viewCreated() {
        repository = Repository.getInstance();
        view.showAccountTypes(AccountType.values());
    }

    @Override
    public void createNewAccount(String accountName, AccountType accountType, Double overdueLimit) {
        final Account newAccount =
                new Account(accountName, accountType, overdueLimit);
        repository.addAccount(newAccount);
    }
}
