package org.clemy.androidapps.expense.model;

import androidx.annotation.NonNull;

public class Transaction {
    private final Integer id;
    @NonNull
    private final Integer accountId;
    @NonNull
    private final String name;
    @NonNull
    private final Double amount;

    public Transaction(@NonNull Integer accountId, @NonNull String name, @NonNull Double amount) {
        this(null, accountId, name, amount);
    }

    public Transaction(Integer id, @NonNull Integer accountId, @NonNull String name, @NonNull Double amount) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.amount = amount;
    }

    public Transaction(Transaction transaction) {
        this(transaction.id, transaction.accountId, transaction.name, transaction.amount);
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public Integer getAccountId() {
        return accountId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Double getAmount() {
        return amount;
    }

    // optimization: transaction ids will not be reused and transactions are immutable
    // -> compare only id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction transaction = (Transaction) o;

        return id.equals(transaction.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
