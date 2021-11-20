package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

public class TransactionDetails extends ATransactionActivity {

    /**
     * Holds the transaction displayed by this activity. Can be null, should no Transaction be sent
     *  with the intent (Probably add implementation).
     */
    private Transaction displayedTransaction;

    @Override
    void setup() {
        Intent intent = getIntent();
        displayedTransaction = intent.getParcelableExtra(EIntents.TRANSACTION.toString());
        assert (displayedTransaction != null);

        amountEditText.setText(Integer.toString(displayedTransaction.getAmount()));
        typeSpinner.setSelection(typeAdapter.getPosition(displayedTransaction.getType()));
        // TODO: category spinner
    }
}