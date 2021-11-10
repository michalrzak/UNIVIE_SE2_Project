package org.clemy.androidapps.expense.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {RoomAccount.class, RoomTransaction.class}, version = 1, exportSchema = false)
public abstract class ExpenseRoomDatabase extends RoomDatabase {
    // should use singleton pattern according documentation
    // https://developer.android.com/training/data-storage/room
    // https://developer.android.com/codelabs/android-room-with-a-view#7

    public abstract RoomAccountDao accountDao();
    public abstract RoomTransactionDao transactionsDao();
}
