package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

public class TransactionDetails extends ATransactionActivity {
    @Override
    void setup() {
        amountEditText.setText(Integer.toString(displayedTransaction.getAmount()));
        typeSpinner.setSelection(typeAdapter.getPosition(displayedTransaction.getType()));
        // TODO: category spinner
    }
}