package org.clemy.androidapps.expense.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AccountWithTransactions {
    @NonNull
    private final Optional<Account> account;
    @NonNull
    private final List<Transaction> transactions;
    @NonNull
    private final Double sum;

    public AccountWithTransactions() {
        this(null, new ArrayList<>());
    }

    public AccountWithTransactions(Account account, @NonNull List<Transaction> transactions) {
        this.account = Optional.ofNullable(account);
        this.transactions = transactions;
        Double sum = 0.0;
        for (final Transaction transaction : transactions) {
            sum += transaction.getAmount();
        }
        this.sum = sum;
    }

    public Optional<Account> getAccount() {
        return account;
    }

    @NonNull
    public List<Transaction> getTransactionsList() {
        return Collections.unmodifiableList(transactions);
    }

    public Double getSum() {
        return sum;
    }

    public boolean getOverdue() {
        return account.map(account -> {
            return account.getOverdueLimitOptional().map(limit -> limit < sum).orElse(false);
        }).orElse(false);
    }
}
