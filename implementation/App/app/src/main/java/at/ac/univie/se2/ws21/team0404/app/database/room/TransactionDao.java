package at.ac.univie.se2.ws21.team0404.app.database.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransaction;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransactionWithCategory;

@Dao
public interface TransactionDao {
    @Transaction
    @Query("SELECT * FROM transactions WHERE accountId == :accountId ORDER BY id ASC")
    List<RoomTransactionWithCategory> getTransactions(int accountId);

    @Insert
    void addTransaction(RoomTransaction transaction);

    @Update
    void updateTransaction(RoomTransaction transaction);

    @Delete
    void deleteTransaction(RoomTransaction transaction);
}
