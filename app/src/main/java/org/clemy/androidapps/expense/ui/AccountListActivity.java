package org.clemy.androidapps.expense.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.database.MemoryDb;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.database.RoomDb;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.utils.ChangingDataWithLifecycle;

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

        Repository repository = Repository.getInstance();

        final ChangingDataWithLifecycle<AccountList> accountsData =
                new ChangingDataWithLifecycle<>(repository.getAccounts(), getLifecycle());
        accountsData.observe(data -> adapter.submitList(data.getAccountList()));
        final Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "Timer");
                repository.addAccount(new Account("newNew", AccountType.BANK));
                //timer.cancel();
            }
        }, 3000, 3000);

        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_switch_to_mem_db) {
            Repository.getInstance().setDatabaseStrategy(new MemoryDb());
            return true;
        }
        if (item.getItemId() == R.id.action_switch_to_saved_db) {
            Repository.getInstance().setDatabaseStrategy(new RoomDb());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
