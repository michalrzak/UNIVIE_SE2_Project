package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ETransactionType;

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
    return Repository.getInstance().getCategoryList().getData();
  }

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
   *  and perform other setup tasks
   */
  abstract void setup();

  /**
   * This function gets called once the "save" button gets pressed.
   * Provide implementations in the subclasses
   */
  abstract protected void saveButtonPressed();

  /**
   * Takes the values from the form and creates a new Transaction object from them.
   *
   * Careful! This object has a new unique ID.
   *
   * @return a new Transaction object based on the values provided in the form
   */
  @NonNull
  protected Transaction getTransactionFromForm() {
    Object category = categorySpinner.getSelectedItem();
    Object type = typeSpinner.getSelectedItem();

    assert (type != null);
    assert (type instanceof ETransactionType);
    // as category is optional it can be returned as null
    // TODO: maybe change this away from null to some special enum value
    assert (category == null || category instanceof Category);

    int amount;
    String amountText = amountEditText.getText().toString();
    try {
      amount = Integer.parseInt(amountText);
    } catch (NumberFormatException e) {
      if (amountText.length() == 0) {
        // if amountText is empty, just assume a 0 as the amount
        amount = 0;
      }
      else {
        Log.e("TransactionAct_saveBtn",
            "The parsed amount was not an Integer. This should not have been possible. Message:" + e
                .getMessage());
        finish();
        throw new AssertionError();
      }
    }

    return new Transaction((Category) category, (ETransactionType) type, amount);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction_details);

    ActionBar actionBar = getSupportActionBar();
    assert (actionBar != null);
    actionBar.setDisplayHomeAsUpEnabled(true);
    
    amountEditText = findViewById(R.id.transaction_amount_edittext);

    typeSpinner = findViewById(R.id.transaction_type_spinner);
    typeAdapter = new ArrayAdapter<>(this,
        R.layout.support_simple_spinner_dropdown_item, ETransactionType.values());
    typeSpinner.setAdapter(typeAdapter);

    categorySpinner = findViewById(R.id.transaction_category_spinner);

    categoryAdapter = new ArrayAdapter<>(this,
            R.layout.support_simple_spinner_dropdown_item, getAllCategories());
    categorySpinner.setAdapter(categoryAdapter);

    Button saveButton = findViewById(R.id.transaction_save_button);
    saveButton.setOnClickListener(view -> saveButtonPressed());

    setup();
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {

    Log.d("OptionsItemSelected", String.format("Selected item with id %d", item.getItemId()));

    switch (item.getItemId()) {
      default:
        finish();
        return true;
    }
  }
}
