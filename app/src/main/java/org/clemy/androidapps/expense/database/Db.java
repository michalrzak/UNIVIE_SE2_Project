package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface Db {
    @NonNull
    List<Account> getAccounts();

    Optional<Account> getAccount(@NonNull Integer accountId);

    void createOrUpdateAccount(@NonNull final Account account);

    List<Transaction> getTransactionsForAccount(@NonNull Integer accountId);

    void createTransaction(@NonNull final Transaction transaction);

}
