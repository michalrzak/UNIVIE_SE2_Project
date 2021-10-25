package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.AccountList;

public interface Dao {
    @NonNull
    public AccountList getAccounts();
}
