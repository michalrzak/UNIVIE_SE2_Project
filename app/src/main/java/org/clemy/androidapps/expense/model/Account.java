package org.clemy.androidapps.expense.model;

import androidx.annotation.NonNull;

public class Account {
    private final Integer id;
    @NonNull
    private final String name;
    @NonNull
    private final AccountType type;
    private final Double overdueLimit;

    public Account(@NonNull String name, @NonNull AccountType type, Double overdueLimit) {
        this(null, name, type, overdueLimit);
    }

    public Account(Integer id, @NonNull String name, @NonNull AccountType type, Double overdueLimit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.overdueLimit = overdueLimit;
    }

    public Account(Account account) {
        this(account.id, account.name, account.type, account.overdueLimit);
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

    @NonNull
    public Double getOverdueLimit() {
        return overdueLimit;
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
