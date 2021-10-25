package org.clemy.androidapps.expense.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.database.Repository;

import java.util.List;

public class AccountListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        RecyclerView list = findViewById(R.id.account_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        AccountListAdapter adapter = new AccountListAdapter();

        Repository repository = new Repository();

        adapter.submitList(repository.getAccounts().getAccountList());
        list.setAdapter(adapter);
    }
}