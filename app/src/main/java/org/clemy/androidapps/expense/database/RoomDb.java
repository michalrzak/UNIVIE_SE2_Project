package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;
import androidx.room.Room;

import org.clemy.androidapps.expense.database.room.ExpenseRoomDatabase;
import org.clemy.androidapps.expense.database.room.RoomAccount;
import org.clemy.androidapps.expense.database.room.RoomAccountDao;
import org.clemy.androidapps.expense.database.room.RoomTransaction;
import org.clemy.androidapps.expense.database.room.RoomTransactionDao;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.model.TransactionList;
import org.clemy.androidapps.expense.utils.AppContextStore;

import java.util.Collections;

public class RoomDb implements Db {
    // Facade Pattern: hide package ..expense.database.room
    private final RoomAccountDao accountDao;
    private final RoomTransactionDao transactionsDao;

    public RoomDb() {
        ExpenseRoomDatabase db = Room.databaseBuilder(AppContextStore.getContext(),
                ExpenseRoomDatabase.class, "expense-db-v004").build();
        accountDao = db.accountDao();
        transactionsDao = db.transactionsDao();
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

    @Override
    public TransactionList getTransactionsForAccount(Integer accountId) {
        return new TransactionList(Collections.unmodifiableList(transactionsDao.getTransactionsForAccount(accountId)));
    }

    @Override
    public void addTransaction(@NonNull Transaction transaction) {
        transactionsDao.addTransaction(new RoomTransaction(transaction));
    }
}
