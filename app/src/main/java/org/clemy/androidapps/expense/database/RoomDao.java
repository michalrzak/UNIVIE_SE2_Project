package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;

public class RoomDao implements Dao {

    @NonNull
    @Override
    public AccountList getAccounts() {
        return null;
    }

    @Override
    public void addAccount(@NonNull Account account) {

    }
}
