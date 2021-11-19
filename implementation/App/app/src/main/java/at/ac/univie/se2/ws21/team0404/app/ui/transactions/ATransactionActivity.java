package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import java.util.ArrayList;
import java.util.List;

public abstract class ATransactionActivity extends AppCompatActivity {

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

  private static List<Category> getAllCategories() {
    List<Category> categories = new ArrayList<>();

    // for now this will be mocked, later pull from database and use the proper type
    categories.add(new MockCategory("A"));
    categories.add(new MockCategory("B"));
    categories.add(new MockCategory("C"));
    categories.add(new MockCategory("D"));
    categories.add(new MockCategory("E"));

    return categories;
  }

  protected Transaction displayedTransaction;

  protected EditText amountEditText;
  protected Spinner typeSpinner;
  protected Spinner categorySpinner;

  protected ArrayAdapter<ETransactionType> typeAdapter;

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
    displayedTransaction = (Transaction) intent.getSerializableExtra(Transaction.class.getName());

    amountEditText = findViewById(R.id.transaction_amount_edittext);

    typeSpinner = findViewById(R.id.transaction_type_spinner);
    typeAdapter = new ArrayAdapter<>(this,
        R.layout.support_simple_spinner_dropdown_item, ETransactionType.values());
    typeSpinner.setAdapter(typeAdapter);

    categorySpinner = findViewById(R.id.transaction_category_spinner);

    ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this,
        R.layout.support_simple_spinner_dropdown_item, getAllCategories());
    categorySpinner.setAdapter(categoryAdapter);

    Button button = findViewById(R.id.transaction_save_button);
    button.setOnClickListener(view -> saveTransaction());
  }
}
