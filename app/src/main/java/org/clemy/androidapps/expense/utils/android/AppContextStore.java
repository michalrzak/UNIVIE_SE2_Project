package org.clemy.androidapps.expense.utils.android;

import android.content.Context;

public class AppContextStore {
    private static Context context;

    public static Context getContext() {
        if (context == null) {
            throw new RuntimeException("context not stored yet");
        }
        return context;
    }

    public static void setContext(Context context) {
        if (AppContextStore.context == null) {
            AppContextStore.context = context;
        }
    }
}
