package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.AccountType;

import java.util.ArrayList;
import java.util.List;

public class SampleDao implements Dao {
    private List<Account> accounts = new ArrayList<>();

    public SampleDao() {
        accounts.add(new Account(1, "bla", AccountType.BANK));
        accounts.add(new Account(2, "blurb", AccountType.CASH));
    }

    @NonNull
    @Override
    public AccountList getAccounts() {
        return new AccountList(new ArrayList<>(accounts));
    }

    @Override
    public void addAccount(@NonNull Account account) {
        // TODO: check for synchronization
        // do not change the old list!
        accounts = new ArrayList<>(accounts);
        accounts.add(account);
    }
}
