package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;

public class AccountList extends AppCompatActivity {

    private AccountListAdapter accountListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        RecyclerView recyclerView = findViewById(R.id.account_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        accountListAdapter = new AccountListAdapter(new AccountListAdapter.AppAccountDiff());
        // will use repository from the database in the future
        recyclerView.setAdapter(accountListAdapter);

        // temporary for testing purposes
        ArrayList<AppAccount> list = new ArrayList<>();
        list.add(new AppAccount(EAccountType.BANK, "Investment"));
        list.add(new AppAccount(EAccountType.Card, "Savings"));
        accountListAdapter.submitList(list);

    }
}