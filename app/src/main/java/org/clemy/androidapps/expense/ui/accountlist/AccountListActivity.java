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
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.ui.LifecycleHandler;
import org.clemy.androidapps.expense.ui.newaccount.NewAccountActivity;
import org.clemy.androidapps.expense.ui.transactionlist.TransactionListActivity;

import java.util.List;

/**
 * The AccountList view based on Android Activity.
 */
public class AccountListActivity extends AppCompatActivity implements AccountListContract.View {
    /**
     * The presenter connected to this view.
     */
    private final AccountListContract.Presenter presenter =
            new AccountListPresenter(Repository.getInstance());
    /**
     * Connects the view lifecycle to the presenter.
     */
    private final LifecycleHandler<AccountListActivity> lifecycleHandler =
            new LifecycleHandler<>(presenter, this);

    private AccountListAdapter accountListAdapter;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        RecyclerView list = findViewById(R.id.account_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        accountListAdapter = new AccountListAdapter(presenter::clickAccount);
        list.setAdapter(accountListAdapter);

        findViewById(R.id.floating_button).setOnClickListener(view -> presenter.newAccount());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account_list, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * Used by the presenter to update the account list.
     *
     * @param accounts the new account list.
     */
    @Override
    public void showAccountList(@NonNull List<Account> accounts) {
        accountListAdapter.submitList(accounts);
    }

    /**
     * Used by the presenter to show the new account interface.
     */
    @Override
    public void showNewAccount() {
        Intent intent = new Intent(AccountListActivity.this, NewAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void showEditAccount(@NonNull Account account) {
        Intent intent = new Intent(AccountListActivity.this, TransactionListActivity.class);
        intent.putExtra(TransactionListActivity.INTENT_EXTRA_ACCOUNT_ID, account.getId());
        startActivity(intent);
    }
}
