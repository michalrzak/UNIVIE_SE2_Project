package com.se2.tracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TransactionViewModel transactionViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        setRecycleView();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Transaction transaction = new Transaction((int) (Math.random() * 1000), 100, "CategoryHere");
            transactionViewModel.insert(transaction);
        });
    }

    private void setRecycleView(){
        TransactionListAdapter transactionListAdapter = new TransactionListAdapter(new TransactionListAdapter.TransactionDiff());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(transactionListAdapter);

        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        transactionViewModel.getTransactionList().observe(this, transactionList -> transactionListAdapter.submitList(transactionList));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_db:
                transactionViewModel.deleteAll();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}