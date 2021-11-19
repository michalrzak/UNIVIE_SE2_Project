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

public class TransactionDetails extends AppCompatActivity {

    private static class MockCategory extends Category {
        private final String name;

        public MockCategory(String name){
            this.name = name;
        }

        @NonNull
        @Override
        public String toString() {
            return name;
        }
    }

    private Transaction displayedTransaction;

    private EditText amountEditText;
    private Spinner typeSpinner;
    private Spinner categorySpinner;

    private List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();

        // for now this will be mocked, later pull from database and use the proper type
        categories.add(new MockCategory("A"));
        categories.add(new MockCategory("B"));
        categories.add(new MockCategory("C"));
        categories.add(new MockCategory("D"));
        categories.add(new MockCategory("E"));

        return categories;
    }

    //TODO: implement this once the db stuff is ready
    private void saveTransaction() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);

        Intent intent = getIntent();
        displayedTransaction = (Transaction) intent.getSerializableExtra(Transaction.class.getName());

        amountEditText = findViewById(R.id.transaction_amount_edittext);
        amountEditText.setText(Integer.toString(displayedTransaction.getAmount()));

        typeSpinner = findViewById(R.id.transaction_type_spinner);
        ArrayAdapter<ETransactionType> typeAdapter = new ArrayAdapter<ETransactionType>(this,
                R.layout.support_simple_spinner_dropdown_item, ETransactionType.values());
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setSelection(typeAdapter.getPosition(displayedTransaction.getType()));

        categorySpinner = findViewById(R.id.transaction_category_spinner);

        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this,
                R.layout.support_simple_spinner_dropdown_item, getAllCategories());
        categorySpinner.setAdapter(categoryAdapter);

        Button button = findViewById(R.id.transaction_save_button);
        button.setOnClickListener(view -> saveTransaction());
    }
}