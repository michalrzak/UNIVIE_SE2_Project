package com.example.userlisttest.Data;

/*
 Tbh I don't completely understand why this is needed. From what I gathered:
    Split onto different threads
    Abstract multiple data sources (will we even need this?)
    Manage network?
    https://developer.android.com/codelabs/android-training-livedata-viewmodel?index=..%2F..%2Fandroid-training#7
 */

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepository {
    private AccountDao accountDao;
    private LiveData<List<Account>> allAccounts;
    // once again I am unsure why it is handeled like this
    // from what I gathered, LiveData is some sort of observer. Does this list get updated when it changes
    // on the background?

    public AccountRepository(Application application) {
        AccountDB db = AccountDB.getInstance(application);
        accountDao = db.accountDao();
        allAccounts = accountDao.getAll();
    }

    public LiveData<List<Account>> getAllAccounts() {
        return allAccounts;
    }

    public void insert(Account account) {
        // TODO: why not ust doInBackground?
        new insertAsyncTask(accountDao).execute(account);
    }

    private static class insertAsyncTask extends AsyncTask<Account, Void, Void> {

        private AccountDao accountDao;

        public insertAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insert(accounts[0]);
            return null;
        }
    }



}
