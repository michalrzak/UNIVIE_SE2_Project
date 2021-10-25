package org.clemy.androidapps.expense.database;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.AccountType;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    public AccountList getAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account(1, "bla", AccountType.BANK));
        accounts.add(new Account(2, "blurb", AccountType.CASH));
        return new AccountList(accounts);
    }
}
