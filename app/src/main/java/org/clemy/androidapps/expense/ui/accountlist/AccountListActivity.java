package org.clemy.androidapps.expense.ui.accountlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.ui.LifecycleHandler;
import org.clemy.androidapps.expense.ui.newaccount.NewAccountActivity;

public class AccountListActivity extends AppCompatActivity implements AccountListContract.View {
    private final AccountListContract.Presenter presenter = new AccountListPresenter();
    private final LifecycleHandler<AccountListActivity> lifecycleHandler =
            new LifecycleHandler<>(presenter, this);
    private AccountListAdapter accountListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        RecyclerView list = findViewById(R.id.account_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        accountListAdapter = new AccountListAdapter();
        list.setAdapter(accountListAdapter);

        findViewById(R.id.floating_button).setOnClickListener(view -> presenter.newAccount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_switch_to_mem_db) {
            presenter.switchToMemoryDb();
            return true;
        }
        if (item.getItemId() == R.id.action_switch_to_saved_db) {
            presenter.switchToSavedDb();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showAccountList(@NonNull AccountList accounts) {
        accountListAdapter.submitList(accounts.getAccountList());
    }

    @Override
    public void showNewAccount() {
        Intent intent = new Intent(AccountListActivity.this, NewAccountActivity.class);
        startActivity(intent);
    }
}
