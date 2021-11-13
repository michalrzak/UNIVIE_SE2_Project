package org.clemy.androidapps.expense.utils.android;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import org.clemy.androidapps.expense.utils.ChangingDataOnMainThread;

public class MainThreadExecutorAndroid extends ChangingDataOnMainThread.MainThreadExecutor {
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    @Override
    public void runOnMainThread(Runnable runnable) {
        mainThreadHandler.post(runnable);
    }
}
