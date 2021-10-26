package org.clemy.androidapps.expense;

import android.app.Application;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.database.RoomDb;
import org.clemy.androidapps.expense.utils.AppContextStore;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppContextStore.setContext(getApplicationContext());
        Repository.getInstance().setDatabaseStrategy(new RoomDb());
    }
}
