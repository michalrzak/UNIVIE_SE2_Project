package com.example.userlisttest.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.security.cert.PKIXRevocationChecker;
import java.util.Optional;

// exportSchema = I don't care about saving old schema versions on my drive in this demo
@Database(entities = {Account.class}, version = 1, exportSchema = false)
public abstract class AccountDB extends RoomDatabase{

    // define over which dao the database is supposed to work?
    // this is a bit weird to me
    public abstract AccountDao accountDao();

    private static Optional<AccountDB> INSTANCE = Optional.empty();

    // even though this is a simpleton cannot have a private constructor!
    //private AccountDB(){}

    public static AccountDB getInstance(final Context context) {
        if (!INSTANCE.isPresent()) {
            synchronized (AccountDB.class) {
                if (!INSTANCE.isPresent()) {
                    INSTANCE = Optional.of(Room.databaseBuilder(context, AccountDB.class, "account_database")
                            .fallbackToDestructiveMigration() // This might need to be set differently. If this is set
                            // the db schema will just get destroyed if it gets updated!
                            .build());
                }
            }
        }
        return INSTANCE.get();
    }


}
