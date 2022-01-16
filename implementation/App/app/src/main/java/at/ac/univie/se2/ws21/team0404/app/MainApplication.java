package at.ac.univie.se2.ws21.team0404.app;

import android.app.Application;
import androidx.room.Room;
import at.ac.univie.se2.ws21.team0404.app.database.MemoryDatabase;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.database.RoomDatabase;
import at.ac.univie.se2.ws21.team0404.app.database.room.AppRoomDatabase;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataOnMainThread;
import at.ac.univie.se2.ws21.team0404.app.utils.android.MainThreadExecutorAndroid;

public class MainApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    // This main function does android specific initializations and dependency injection to
    // keep the remaining code platform independent.

    // install the android specific main thread executor
    ChangingDataOnMainThread.setMainThreadExecutor(new MainThreadExecutorAndroid());
    //Repository.create(new MemoryDatabase());
    Repository.create(new RoomDatabase(
        () -> Room.databaseBuilder(this, AppRoomDatabase.class, "room-db")
            .fallbackToDestructiveMigration().build()));
  }
}
