package org.clemy.androidapps.expense.database.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RoomAccount.class}, version = 1, exportSchema = false)
public abstract class ExpenseRoomDatabase extends RoomDatabase {
    
}
