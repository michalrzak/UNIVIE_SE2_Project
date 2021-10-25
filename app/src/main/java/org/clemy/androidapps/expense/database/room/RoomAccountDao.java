package org.clemy.androidapps.expense.database.room;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomAccountDao {
    @Query("SELECT * FROM accounts ORDER BY id ASC")
    List<RoomAccount> getAccounts();
}
