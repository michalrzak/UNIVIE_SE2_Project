package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountWithTransactions;
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.utils.ChangingData;
import org.clemy.androidapps.expense.utils.ChangingDataImpl;
import org.clemy.androidapps.expense.utils.ChangingDataOnMainThread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Central access to the data of the application. Allows selecting different database strategies.
 * <p>
 * Executes all access on background threads and wraps return data in {@link ChangingData} containers
 * to provide them as observables for asynchronous notification of the caller.
 */
public class Repository {
    private static final int NUMBER_OF_THREADS = 4;
    private static final Repository INSTANCE = new Repository();

    private final ExecutorService executor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private final ChangingData<List<Account>> accountList =
        new ChangingDataOnMainThread<>(new ChangingDataImpl<>(new ArrayList<>()));
    private final Map<Integer, ChangingData<AccountWithTransactions>> transactionLists = new HashMap<>();
    private Db db;

    /**
     * Singleton: get an instance via {@link #getInstance()}.
     */
    private Repository() {
    }

    @NonNull
    public static Repository getInstance() {
        return INSTANCE;
    }

    /**
     * Sets the database strategy to be used.
     *
     * @param database The database strategy to be used for persistence.
     */
    public void setDatabaseStrategy(@NonNull Db database) {
        db = database;
        reloadAccounts();
    }

    public ChangingData<List<Account>> getAccounts() {
        return accountList;
    }

    private void reloadAccounts() {
        executor.execute(() -> accountList.setData(db.getAccounts()));
    }

    public void createOrUpdateAccount(@NonNull final Account account) {
        executor.execute(() -> {
            db.createOrUpdateAccount(account);
            reloadAccounts();
            if (account.getId() != null) {
                reloadAccountWithTransactions(account.getId());
            }
        });
    }

    public ChangingData<AccountWithTransactions> getAccountWithTransactions(@NonNull Integer accountId) {
        ChangingData<AccountWithTransactions> data = transactionLists.get(accountId);
        if (data == null) {
            data = new ChangingDataOnMainThread<>(new ChangingDataImpl<>(new AccountWithTransactions()));
            transactionLists.put(accountId, data);
            reloadAccountWithTransactions(accountId);
        }
        return data;
    }

    private void reloadAccountWithTransactions(@NonNull Integer accountId) {
        ChangingData<AccountWithTransactions> data = transactionLists.get(accountId);
        if (data != null) {
            executor.execute(() -> {
                final Optional<Account> account = db.getAccount(accountId);
                final List<Transaction> transactionList = db.getTransactionsForAccount(accountId);
                // TODO: a missing account should generate an error condition and not a "loading.." condition
                data.setData(new AccountWithTransactions(account.orElse(null), transactionList));
            });
        }
    }

    public void reloadTransaction(@NonNull final Transaction transaction) {
        executor.execute(() -> {
            db.createTransaction(transaction);
            reloadAccountWithTransactions(transaction.getAccountId());
        });
    }
}
