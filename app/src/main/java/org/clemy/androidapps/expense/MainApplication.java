package org.clemy.androidapps.expense;

import android.app.Application;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.database.RoomDb;
import org.clemy.androidapps.expense.utils.ChangingDataOnMainThread;
import org.clemy.androidapps.expense.utils.android.AppContextStore;
import org.clemy.androidapps.expense.utils.android.MainThreadExecutorAndroid;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // This does android specific initializations and dependency injection to
        // keep the remaining code platform independent

        // Store the android specific application context centrally
        // This will be used by other android specific code (e.g. RoomDb)
        AppContextStore.setContext(getApplicationContext());

        // install the android specific main thread executor
        ChangingDataOnMainThread.setMainThreadExecutor(new MainThreadExecutorAndroid());

        // switch to the RoomDb as default on startup
        Repository.getInstance().setDatabaseStrategy(new RoomDb());
    }
}
