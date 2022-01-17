package at.ac.univie.se2.ws21.team0404.app.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import androidx.room.TypeConverters;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomAppAccount;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomCategory;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransaction;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.UUIDConverter;

@Database(
        entities = {RoomAppAccount.class, RoomTransaction.class, RoomCategory.class},
        version = 13,
        exportSchema = false)
@TypeConverters({UUIDConverter.class, Converters.class})
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract AccountDao accountDao();

    public abstract TransactionDao transactionDao();

    public abstract CategoryDao categoryDao();
}
