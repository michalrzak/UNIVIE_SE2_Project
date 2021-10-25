package org.clemy.androidapps.expense.model;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class AccountList {
    @NonNull
    private final List<Account> accounts;

    public AccountList(@NonNull List<Account> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    public List<Account> getAccountList() {
        return Collections.unmodifiableList(accounts);
    }
}
