package org.clemy.androidapps.expense.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RoomAccount.class}, version = 1, exportSchema = false)
public abstract class ExpenseRoomDatabase extends RoomDatabase {
    // should use singleton pattern according documentation
    // https://developer.android.com/training/data-storage/room
    // https://developer.android.com/codelabs/android-room-with-a-view#7

    public abstract RoomAccountDao accountDao();
}
