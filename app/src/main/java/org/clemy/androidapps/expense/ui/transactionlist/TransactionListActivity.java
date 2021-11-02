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
import org.clemy.androidapps.expense.model.TransactionList;
import org.clemy.androidapps.expense.ui.LifecycleHandler;
import org.clemy.androidapps.expense.ui.newtransaction.NewTransactionActivity;

public class TransactionListActivity extends AppCompatActivity implements TransactionListContract.View {
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

        Intent intent = getIntent();
        String accountName = intent.getStringExtra("AccountName");
        setTitle(accountName);

        //TODO: handle invalid account id
        Integer accountId = intent.getIntExtra("AccountId", 0);
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
    public void showTransactionList(@NonNull TransactionList transactions) {
        transactionListAdapter.submitList(transactions.getTransactionsList());
    }

    @Override
    public void showNewTransaction(@NonNull Integer accountId) {
        Intent intent = new Intent(TransactionListActivity.this, NewTransactionActivity.class);
        intent.putExtra("AccountId", accountId);
        startActivity(intent);
    }
}