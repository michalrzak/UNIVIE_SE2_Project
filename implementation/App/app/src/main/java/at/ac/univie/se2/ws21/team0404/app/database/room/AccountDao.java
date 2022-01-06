package at.ac.univie.se2.ws21.team0404.app.database.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomAppAccount;

@Dao
public interface AccountDao {
    @Query("SELECT * FROM accounts ORDER BY id ASC")
    List<RoomAppAccount> getAccounts();

    @Query("SELECT * FROM accounts WHERE id == :accountId")
    RoomAppAccount getAccount(Integer accountId);

    @Insert
    void addAccount(RoomAppAccount account);

    @Update
    void updateAccount(RoomAppAccount account);

    @Delete
    void deleteAccount(RoomAppAccount account);
}
