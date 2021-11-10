package org.clemy.androidapps.expense.model;

import androidx.annotation.NonNull;

public enum AccountType {
    CASH("Cash Account"),
    BANK("Bank Account");

    private final String name;

    AccountType(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
