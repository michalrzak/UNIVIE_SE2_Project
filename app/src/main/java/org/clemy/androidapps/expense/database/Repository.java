package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.utils.ChangingData;

public class Repository {
    // consider Strategy pattern for switching DB implementation
    private final Dao dao = new SampleDao();
    private final ChangingData<AccountList> accountList = new ChangingData<>();

    public Repository() {
        accountList.setData(dao.getAccounts());
    }

    public ChangingData<AccountList> getAccounts() {
        return accountList;
    }

    public void addAccount(@NonNull final Account account) {
        dao.addAccount(account);
        accountList.setData(dao.getAccounts());
    }
}
