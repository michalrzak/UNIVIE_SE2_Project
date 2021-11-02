package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.model.TransactionList;

public interface Db {
    @NonNull
    AccountList getAccounts();
    void addAccount(@NonNull final Account account);

    TransactionList getTransactionsForAccount(Integer accountId);
    void addTransaction(@NonNull final Transaction transaction);
}
