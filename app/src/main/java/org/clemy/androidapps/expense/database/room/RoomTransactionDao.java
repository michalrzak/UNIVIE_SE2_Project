package org.clemy.androidapps.expense.database.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomTransactionDao {
    @Query("SELECT * FROM transactions WHERE accountId == :accountId ORDER BY id ASC")
    List<RoomTransaction> getTransactionsForAccount(Integer accountId);

    @Insert
    void addTransaction(RoomTransaction transaction);
}
