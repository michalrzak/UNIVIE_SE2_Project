package at.ac.univie.se2.ws21.team0404.app.ui.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.AccountList;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.TransactionDetails;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // temporary for testing
        // Intent intent = new Intent(this, AccountList.class);

        Transaction transaction = new Transaction(0, new Category(), ETransactionType.EXPENSE, 100);
        Intent intent = new Intent(this, TransactionDetails.class);
        intent.putExtra(Transaction.class.getName(), transaction);
        startActivity(intent);
    }
}