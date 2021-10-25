package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;
import androidx.room.Room;

import org.clemy.androidapps.expense.database.room.ExpenseRoomDatabase;
import org.clemy.androidapps.expense.database.room.RoomAccount;
import org.clemy.androidapps.expense.database.room.RoomAccountDao;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.utils.AppContextStore;

import java.util.Collections;

public class RoomDb implements Db {
    // Facade Pattern: hide package ..expense.database.room
    private final RoomAccountDao accountDao;

    public RoomDb() {
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
