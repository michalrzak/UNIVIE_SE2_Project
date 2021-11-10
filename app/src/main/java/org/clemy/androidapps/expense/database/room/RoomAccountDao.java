package org.clemy.androidapps.expense.database.room;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RoomAccountDao {
    @Query("SELECT * FROM accounts ORDER BY id ASC")
    List<RoomAccount> getAccounts();

    @Query("SELECT * FROM accounts WHERE id == :accountId")
    RoomAccount getAccount(@NonNull Integer accountId);

    @Insert
    void addAccount(RoomAccount account);
}
