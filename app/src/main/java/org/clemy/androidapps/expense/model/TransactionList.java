package org.clemy.androidapps.expense.model;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class TransactionList {
    @NonNull
    private final List<Transaction> transactions;

    public TransactionList(@NonNull List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    public List<Transaction> getTransactionsList() {
        return Collections.unmodifiableList(transactions);
    }
}
