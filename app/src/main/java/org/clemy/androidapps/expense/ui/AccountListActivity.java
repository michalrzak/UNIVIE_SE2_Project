package org.clemy.androidapps.expense.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.utils.ChangingData;

import java.util.List;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class AccountListActivity extends AppCompatActivity {
    private static final String TAG = "AccountListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        RecyclerView list = findViewById(R.id.account_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        AccountListAdapter adapter = new AccountListAdapter();

        Repository repository = new Repository();

        repository.getAccounts().observe(new ChangingData.Observer<AccountList>() {
            @Override
            public void changed(AccountList data) {
                Log.d(TAG, "changed " + data.getAccountList().size());
                adapter.submitList(data.getAccountList());
            }
        });

        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "Timer");
                repository.addAccount(new Account(99, "newNew", AccountType.BANK));
                timer.cancel();
            }
        }, 3000);

        list.setAdapter(adapter);
    }
}
