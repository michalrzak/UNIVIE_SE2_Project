package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.utils.ChangingData;
import org.clemy.androidapps.expense.utils.ChangingDataImpl;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Central access to the data of the application. Allows selecting different database strategies.
 *
 * Executes all access on background threads and wraps return data in {@link ChangingData} containers
 * to provide them as observables for asynchronous notification of the caller.
 */
public class Repository {
    private static final int NUMBER_OF_THREADS = 4;
    private static final Repository INSTANCE = new Repository();

    private final ExecutorService executor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private final ChangingData<AccountList> accountList = new ChangingDataImpl<>(new AccountList(new ArrayList<>()));
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
        updateAccounts();
    }

    public ChangingData<AccountList> getAccounts() {
        return accountList;
    }

    private void updateAccounts() {
        executor.execute(() -> accountList.setData(db.getAccounts()));
    }

    public void addAccount(@NonNull final Account account) {
        executor.execute(() -> {
            db.addAccount(account);
            updateAccounts();
        });
    }
}
