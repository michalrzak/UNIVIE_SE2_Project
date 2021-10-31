package com.se2.tracker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transaction.class}, version = 1)
public abstract class TransactionDatabase extends RoomDatabase {
    public abstract TransactionDAO transactionDAO();

    private static volatile TransactionDatabase INSTANCE;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(2);

    static TransactionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TransactionDatabase.class, "transactionDB")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
