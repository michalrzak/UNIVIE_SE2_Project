package at.ac.univie.se2.ws21.team0404.app.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomAppAccount;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomCategory;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransaction;

@Database(
        entities = {RoomAppAccount.class, RoomTransaction.class, RoomCategory.class},
        version = 4,
        exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract AccountDao accountDao();

    public abstract TransactionDao transactionDao();

    public abstract CategoryDao categoryDao();
}
