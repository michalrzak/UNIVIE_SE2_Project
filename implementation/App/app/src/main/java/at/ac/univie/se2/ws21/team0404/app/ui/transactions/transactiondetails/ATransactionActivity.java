package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class ATransactionActivity extends AppCompatActivity {

  /**
   * A special UI category to represent the null category
   */
  private final static Category nullCategory = new Category(ETransactionType.INCOME,
      "<no category>") {

    @NonNull
    @Override
    public String toString() {
      return getName();
    }
  };
  /**
   * Views, made available to the subclass
   */
  protected EditText amountEditText;
  protected EditText nameEditText;
  protected Spinner typeSpinner;
  protected Spinner categorySpinner;
  /**
   * Adapters needs to be made available to be able to select the starting option for the spinners
   */
  protected ArrayAdapter<ETransactionType> typeAdapter;
  protected ArrayAdapter<Category> categoryAdapter;
  /**
   * Fields, made available to the subclasses
   */
  protected AppAccount owner;

  private Category selectedCategory = nullCategory;

  /**
   * Method, used to get a List of all available categories.
   *
   * @return List of available categories
   */
  private static List<Category> getAllCategories() {
    return Repository.getInstance().getCategoryList().getData();
  }

  private List<Category> getMatchingCategories(ETransactionType type) {
    return getAllCategories()
        .stream().filter(category -> category.getType() == type)
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * This method gets called at the end of create. Use this to set values to the Views if needed and
   * perform other setup tasks
   */
  protected abstract void setup();

  /**
   * This function gets called once the "save" button gets pressed. Provide implementations in the
   * subclasses
   */
  protected abstract void saveButtonPressed();

  protected void updateCategorySelection(Category category) {
    selectedCategory = category;
    if (selectedCategory == null) {
      selectedCategory = nullCategory;
    }
    categorySpinner.setSelection(categoryAdapter.getPosition(selectedCategory));
  }

  private void updateCategoryAdapter(ETransactionType type) {
    ETransactionType selectedTransactionType = (ETransactionType) typeSpinner.getSelectedItem();
    List<Category> categories = getMatchingCategories(selectedTransactionType);

    // add support to Spinner for "no category" selection
    categories.add(0, nullCategory);
    // add support to Spinner for deleted categories
    if (!categories.contains(selectedCategory)) {
      if (selectedCategory.getType() == selectedTransactionType) {
        categories.add(selectedCategory);
      }
    }

    categoryAdapter = new ArrayAdapter<>(this,
        R.layout.support_simple_spinner_dropdown_item,
        categories
    );
    categorySpinner.setAdapter(categoryAdapter);
    updateCategorySelection(selectedCategory);
  }

  /**
   * Takes the values from the form and creates a new Transaction object from them. If a Transaction
   * object cannot be created returns an empty Optional. Careful! This object has a new unique ID.
   *
   * @return a new Transaction object based on the values provided in the form
   */
  @NonNull
  protected Optional<Transaction> getTransactionFromForm() {
    Object category = categorySpinner.getSelectedItem();
    Object type = typeSpinner.getSelectedItem();

    assert (type != null);
    assert (type instanceof ETransactionType);
    assert (category != null);
    assert (category instanceof Category);
    if (category == nullCategory) {
      category = null;
    }

    int amount;
    String amountText = amountEditText.getText().toString();
    try {
      amount = Integer.parseInt(amountText);
    } catch (NumberFormatException e) {
      if (amountText.length() == 0) {
        // if amountText is empty, just assume a 0 as the amount
        amount = 0;
      } else {
        Log.e("TransactionAct_saveBtn",
            "The parsed amount was not an Integer. This should not have been possible. Message:" + e
                .getMessage());
        return Optional.empty();
      }
    }

    String name = nameEditText.getText().toString();

    try {
      return Optional.of(new Transaction((Category) category, (ETransactionType) type, amount, name));
    } catch (IllegalArgumentException e) {
      Log.w("TransactionActivity", "The transaction provided in the form was invalid!");
    }

    return Optional.empty();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_transaction_details);

    ActionBar actionBar = getSupportActionBar();
    assert (actionBar != null);
    actionBar.setDisplayHomeAsUpEnabled(true);

    amountEditText = findViewById(R.id.transaction_amount_edittext);
    nameEditText = findViewById(R.id.transaction_name_editText);

    typeSpinner = findViewById(R.id.transaction_type_spinner);
    typeAdapter = new ArrayAdapter<>(this,
        R.layout.support_simple_spinner_dropdown_item, ETransactionType.values());
    typeSpinner.setAdapter(typeAdapter);

    categorySpinner = findViewById(R.id.transaction_category_spinner);

    categoryAdapter = new ArrayAdapter<>(this,
        R.layout.support_simple_spinner_dropdown_item,
        getMatchingCategories((ETransactionType) typeSpinner.getSelectedItem())
    );
    categorySpinner.setAdapter(categoryAdapter);

    Button saveButton = findViewById(R.id.transaction_save_button);
    saveButton.setOnClickListener(view -> saveButtonPressed());

    typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        updateCategoryAdapter((ETransactionType) typeSpinner.getSelectedItem());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    Intent intent = getIntent();
    owner = intent.getParcelableExtra(EIntents.ACCOUNT.toString());
    assert (owner != null);

    setup();
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    Log.d("OptionsItemSelected", String.format("Selected item with id %d", item.getItemId()));

    // back button pressed
    // need to add switch case where this is the default value if more options are added
    finish();
    return true;
  }
}
