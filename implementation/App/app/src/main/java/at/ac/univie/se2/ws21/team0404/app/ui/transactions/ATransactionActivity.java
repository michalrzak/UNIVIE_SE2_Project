package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.categories.EIncomeOrExpense;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

public abstract class ATransactionActivity extends AppCompatActivity {

  /**
   * Method, used to get a List of all available categories.
   * 
   * TODO: for now this method simply uses a static list. Needs to be change to be dynamically
   * pulled from the DB
   * 
   * @return List of available categories
   */
  private static List<Category> getAllCategories() {
    List<Category> categories = new ArrayList<>();

    // for now this will be mocked, later pull from database and use the proper type
    categories.add(new Category(EIncomeOrExpense.INCOME, "A"));
    categories.add(new Category(EIncomeOrExpense.INCOME,"B"));
    categories.add(new Category(EIncomeOrExpense.INCOME,"C"));
    categories.add(new Category(EIncomeOrExpense.INCOME,"D"));
    categories.add(new Category(EIncomeOrExpense.INCOME,"E"));

    return categories;
  }

  protected Transaction displayedTransaction;

  /**
   * Views, made available to the subclass
   */
  protected EditText amountEditText;
  protected Spinner typeSpinner;
  protected Spinner categorySpinner;

  /**
   * Adapters needs to be made available to be able to select the starting option for the spinners
   */
  protected ArrayAdapter<ETransactionType> typeAdapter;
  protected ArrayAdapter<Category> categoryAdapter;

  /**
   * This method gets called at the end of create. Use this to set values to the Views if needed
   * and perform other setup tasks
   */
  abstract void setup();

  //TODO: implement this once the db stuff is ready
  private void saveTransaction() {
    return;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction_details);

    Intent intent = getIntent();
    displayedTransaction = intent.getParcelableExtra(EIntents.TRANSACTION.toString());
    assert (displayedTransaction != null);

    amountEditText = findViewById(R.id.transaction_amount_edittext);

    typeSpinner = findViewById(R.id.transaction_type_spinner);
    typeAdapter = new ArrayAdapter<>(this,
        R.layout.support_simple_spinner_dropdown_item, ETransactionType.values());
    typeSpinner.setAdapter(typeAdapter);

    categorySpinner = findViewById(R.id.transaction_category_spinner);

    categoryAdapter = new ArrayAdapter<>(this,
            R.layout.support_simple_spinner_dropdown_item, getAllCategories());
    categorySpinner.setAdapter(categoryAdapter);

    Button button = findViewById(R.id.transaction_save_button);
    button.setOnClickListener(view -> saveTransaction());

    setup();
  }
}
