package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.utils.ChangingData;
import org.clemy.androidapps.expense.utils.ChangingDataBase;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private static final int NUMBER_OF_THREADS = 4;
    private static final Repository INSTANCE = new Repository();

    // consider Strategy pattern for switching DB implementation
    private final ExecutorService executor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private Db db;
    private final ChangingData<AccountList> accountList = new ChangingDataBase<>(new AccountList(new ArrayList<>()));

    @NonNull
    public static Repository getInstance() {
        return INSTANCE;
    }

    private Repository() {}

    // Strategy Pattern: select the DB access type
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
