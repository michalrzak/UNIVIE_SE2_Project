package org.clemy.androidapps.expense.model;

import androidx.annotation.NonNull;

import java.util.Objects;
import java.util.Optional;

public class Account {
    private final Integer id;
    @NonNull
    private final String name;
    @NonNull
    private final AccountType type;
    // Room does not support Optional, so internally this can be null
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

    public Account(@NonNull Account account) {
        this(account.id, account.name, account.type, account.overdueLimit);
    }

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
    public Optional<Double> getOverdueLimitOptional() {
        return Optional.ofNullable(overdueLimit);
    }

    // access function for Room DB
    @NonNull
    public Double getOverdueLimit() {
        return overdueLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && name.equals(account.name) && type == account.type && Objects.equals(overdueLimit, account.overdueLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, overdueLimit);
    }
}