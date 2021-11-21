package at.ac.univie.se2.ws21.team0404.app.utils.android;

import android.os.Handler;
import android.os.Looper;
import androidx.core.os.HandlerCompat;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataOnMainThread;

public class MainThreadExecutorAndroid extends ChangingDataOnMainThread.MainThreadExecutor {

  Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

  @Override
  public void runOnMainThread(Runnable runnable) {
    mainThreadHandler.post(runnable);
  }
}
