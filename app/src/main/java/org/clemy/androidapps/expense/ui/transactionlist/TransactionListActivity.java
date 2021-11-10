package org.clemy.androidapps.expense.ui.transactionlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.ui.LifecycleHandler;
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

        findViewById(R.id.floating_button).setOnClickListener(view -> presenter.newTransaction());
    }

    @Override
    public void showAccountLoading() {
        setTitle("Loading..");
    }

    @Override
    public void showAccountInformation(@NonNull String accountName, boolean overdue) {
        setTitle(accountName + (overdue ? " !Overdue!" : ""));
    }

    @Override
    public void showTransactionList(@NonNull List<Transaction> transactions) {
        transactionListAdapter.submitList(transactions);
    }

    @Override
    public void showNewTransaction(@NonNull Integer accountId) {
        Intent intent = new Intent(TransactionListActivity.this, NewTransactionActivity.class);
        intent.putExtra(NewTransactionActivity.INTENT_EXTRA_ACCOUNT_ID, accountId);
        startActivity(intent);
    }
}