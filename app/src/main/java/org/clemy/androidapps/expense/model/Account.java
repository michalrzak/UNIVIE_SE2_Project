package org.clemy.androidapps.expense.model;

import androidx.annotation.NonNull;

public class Account {
    @NonNull
    private final Integer id;
    @NonNull
    private final String name;
    @NonNull
    private final AccountType type;

    public Account(@NonNull Integer id, @NonNull String name, @NonNull AccountType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public AccountType getType() {
        return type;
    }

    // optimization: account ids will not be reused and accounts are immutable
    // -> compare only id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
