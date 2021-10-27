package org.clemy.androidapps.expense.ui.transactionlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.clemy.androidapps.expense.R;

public class TransactionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        Intent intent = getIntent();
        String accountName = intent.getStringExtra("AccountName");
        setTitle(accountName);
    }
}