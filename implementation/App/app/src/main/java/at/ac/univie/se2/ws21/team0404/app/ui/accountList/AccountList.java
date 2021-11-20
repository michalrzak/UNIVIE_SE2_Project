package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.newOrAddAccount.NewOrAddAccountActivity;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

// Part of the code here is temporary. It will be later moved into the Presenter Class

public class AccountList extends AppCompatActivity {

    private AccountListAdapter accountListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        RecyclerView recyclerView = findViewById(R.id.account_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        accountListAdapter = new AccountListAdapter(account -> {
            Intent intent = new Intent(this, TemporaryIntermediaryActivity.class);
            intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(account));
            startActivity(intent);
        });
        recyclerView.setAdapter(accountListAdapter);

        ChangingData<List<AppAccount>> accountListData = Repository.getInstance().getAccountList();
        accountListData.observe(data -> accountListAdapter.submitList(data));

        FloatingActionButton fab = findViewById(R.id.floating_button);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, NewOrAddAccountActivity.class);
            startActivity(intent);
        });
    }
}