package com.example.userlisttest.Data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccountDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(Account account);

    @Delete
    public void delete(Account account);

    @Query("SELECT * FROM account_table ORDER BY name ASC")
    public LiveData<List<Account>> getAll();
}
