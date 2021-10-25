package org.clemy.androidapps.expense.database;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.AccountType;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private Dao dao = new SampleDao();

    public AccountList getAccounts() {
        return dao.getAccounts();
    }
}
