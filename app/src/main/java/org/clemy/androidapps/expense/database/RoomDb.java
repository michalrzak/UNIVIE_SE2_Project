package org.clemy.androidapps.expense.database;

import androidx.annotation.NonNull;
import androidx.room.Room;

import org.clemy.androidapps.expense.database.room.ExpenseRoomDatabase;
import org.clemy.androidapps.expense.database.room.RoomAccount;
import org.clemy.androidapps.expense.database.room.RoomAccountDao;
import org.clemy.androidapps.expense.database.room.RoomTransaction;
import org.clemy.androidapps.expense.database.room.RoomTransactionDao;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.utils.android.AppContextStore;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RoomDb implements Db {
    // Facade Pattern: hide package ..expense.database.room
    private final RoomAccountDao accountDao;
    private final RoomTransactionDao transactionsDao;

    public RoomDb() {
        ExpenseRoomDatabase db = Room.databaseBuilder(AppContextStore.getContext(),
                ExpenseRoomDatabase.class, "expense-db-v005").build();
        accountDao = db.accountDao();
        transactionsDao = db.transactionsDao();
    }

    @NonNull
    @Override
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accountDao.getAccounts());
    }

    @Override
    public Optional<Account> getAccount(@NonNull Integer accountId) {
        return Optional.ofNullable(accountDao.getAccount(accountId));
    }

    @Override
    public void createOrUpdateAccount(@NonNull Account account) {
        if (account.getId() == null) {
            accountDao.addAccount(new RoomAccount(account));
        } else {
            accountDao.updateAccount(new RoomAccount(account));
        }
    }

    @Override
    public List<Transaction> getTransactionsForAccount(@NonNull Integer accountId) {
        return Collections.unmodifiableList(transactionsDao.getTransactionsForAccount(accountId));
    }

    @Override
    public void createTransaction(@NonNull Transaction transaction) {
        transactionsDao.addTransaction(new RoomTransaction(transaction));
    }
}
