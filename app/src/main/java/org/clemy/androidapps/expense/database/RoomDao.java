package org.clemy.androidapps.expense.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import org.clemy.androidapps.expense.database.room.ExpenseRoomDatabase;
import org.clemy.androidapps.expense.database.room.RoomAccount;
import org.clemy.androidapps.expense.database.room.RoomAccountDao;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.utils.AppContextStore;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RoomDao implements Dao {
    private final RoomAccountDao accountDao;

    public RoomDao() {
        ExpenseRoomDatabase db = Room.databaseBuilder(AppContextStore.getContext(),
                ExpenseRoomDatabase.class, "expense-db-v002").build();
        accountDao = db.accountDao();
    }

    @NonNull
    @Override
    public AccountList getAccounts() {
        return new AccountList(Collections.unmodifiableList(accountDao.getAccounts()));
    }

    @Override
    public void addAccount(@NonNull Account account) {
        accountDao.addAccount(new RoomAccount(account));
    }
}
