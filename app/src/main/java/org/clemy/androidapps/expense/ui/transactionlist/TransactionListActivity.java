package org.clemy.androidapps.expense.ui.transactionlist;

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
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.ui.LifecycleHandler;
import org.clemy.androidapps.expense.ui.neweditaccount.NewEditAccountActivity;
import org.clemy.androidapps.expense.ui.newtransaction.NewTransactionActivity;

import java.util.List;

public class TransactionListActivity extends AppCompatActivity implements TransactionListContract.View {
    public static final String INTENT_EXTRA_ACCOUNT_ID = "AccountId";

    /**
     * The presenter connected to this view.
     */
    private TransactionListContract.Presenter presenter;
    /**
     * Connects the view lifecycle to the presenter.
     */
    private LifecycleHandler<TransactionListActivity> lifecycleHandler;

    private TransactionListAdapter transactionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        //TODO: handle invalid account id
        Intent intent = getIntent();
        Integer accountId = intent.getIntExtra(INTENT_EXTRA_ACCOUNT_ID, 0);
        presenter = new TransactionListPresenter(Repository.getInstance(), accountId);
        lifecycleHandler = new LifecycleHandler<>(presenter, this);

        RecyclerView list = findViewById(R.id.transactions_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        transactionListAdapter = new TransactionListAdapter((transaction) -> {
        });
        list.setAdapter(transactionListAdapter);

        findViewById(R.id.floating_button).setOnClickListener(view -> presenter.clickedNewTransaction());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transaction_list, menu);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_edit_account) {
            presenter.clickedEditAccount();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showAccountLoading() {
        setTitle(R.string.account_loading);
    }

    @Override
    public void showAccountInformation(@NonNull String accountName, boolean overdue) {
        setTitle(getResources().getString(R.string.account_title) +
                " " +
                accountName +
                (overdue ? " " + getResources().getString(R.string.overdue_warning) : ""));
    }

    @Override
    public void showTransactionList(@NonNull List<Transaction> transactions) {
        transactionListAdapter.submitList(transactions);
    }

    @Override
    public void showEditAccount(@NonNull Integer accountId) {
        Intent intent = new Intent(TransactionListActivity.this, NewEditAccountActivity.class);
        intent.putExtra(NewEditAccountActivity.INTENT_EXTRA_ACCOUNT_ID, accountId);
        startActivity(intent);
    }

    @Override
    public void showNewTransaction(@NonNull Integer accountId) {
        Intent intent = new Intent(TransactionListActivity.this, NewTransactionActivity.class);
        intent.putExtra(NewTransactionActivity.INTENT_EXTRA_ACCOUNT_ID, accountId);
        startActivity(intent);
    }
}