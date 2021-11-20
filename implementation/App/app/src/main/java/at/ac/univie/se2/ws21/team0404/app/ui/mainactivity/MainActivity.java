package at.ac.univie.se2.ws21.team0404.app.ui.mainactivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.AccountList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // temporary for testing
        // Intent intent = new Intent(this, AccountList.class);

        /*Transaction transaction = new Transaction(0, new Category(), ETransactionType.EXPENSE, 250);
        Intent intent = new Intent(this, TransactionDetails.class);
        intent.putExtra(Transaction.class.getName(), new ParcelableTransaction(transaction));
        startActivity(intent);*/

        /*
        Intent intent = new Intent(this, TransactionList.class);
        intent.putExtra(AppAccount.class.getName(), new AppAccount(EAccountType.BANK, "amazing account"));
        startActivity(intent);
        */

        try {
            Repository.getInstance().getDatabase().addAccount(new AppAccount("Investment", EAccountType.STOCK));
        } catch (Exception ignored) {}
        Intent intent = new Intent(this, AccountList.class);
        startActivity(intent);

    }
}