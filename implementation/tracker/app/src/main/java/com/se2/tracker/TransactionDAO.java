package com.se2.tracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Query("SELECT * FROM 'transaction'")
    LiveData<List<Transaction>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("DELETE FROM 'transaction'")
    void deleteAll();
}
