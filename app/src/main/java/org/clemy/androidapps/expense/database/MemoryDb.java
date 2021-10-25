package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;

import java.util.ArrayList;
import java.util.List;

public class MemoryDb implements Db {
    private Integer accountId = 1;
    private List<Account> accounts = new ArrayList<>();

    public MemoryDb() {
    }

    @NonNull
    @Override
    public synchronized AccountList getAccounts() {
        return new AccountList(accounts);
    }

    @Override
    public synchronized void addAccount(@NonNull Account account) {
        // do not change the old list!
        accounts = new ArrayList<>(accounts);
        accounts.add(new Account(accountId++, account.getName(), account.getType()));
    }
}
