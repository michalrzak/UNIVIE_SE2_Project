package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import java.util.Optional;

public abstract class ACategoryActivity extends AppCompatActivity {

  protected EditText editTextCategoryName;
  protected RadioButton radioIncome;
  protected RadioButton radioExpense;
  protected TextView categoryTypeHintText;
  protected Button deleteButton;
  protected Button submitButton;

  /**
   * Override this method to add further functionality to onCreate
   * <p>
   * This method gets called at the end of onCreate()
   */
  protected abstract void setup();

  /**
   * Override this to define the action of the submit button This method needs to be public due to
   * the way it is bound to onClick
   *
   * @param view not used
   */
  public abstract void onSubmit(View view);

  /**
   * Override this to define the action of the delete button This method needs to be public due to
   * the way it is bound to onClick
   *
   * @param view not used
   */
  public abstract void onDelete(View view);

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    // back button pressed
    // need to add switch case where this is the default value if more options are added
    finish();
    return true;
  }

  /**
   * Method which gathers the filled in parameters and returns the resulting category. If the
   * category cannot be created returns empty Optional
   *
   * @return Category category based on the params. in the activity
   */
  protected Optional<Category> getCategory() {
    ETransactionType type =
        radioIncome.isChecked() ? ETransactionType.INCOME : ETransactionType.EXPENSE;
    String name = editTextCategoryName.getText().toString();
    try {
      return Optional.of(new Category(type, name));
    } catch (IllegalArgumentException e) {
      Log.w("CategoryActivity", "tried to create a category with invalid parameters");
    }

    return Optional.empty();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_category);

    ActionBar actionBar = getSupportActionBar();
    assert (actionBar != null);
    actionBar.setDisplayHomeAsUpEnabled(true);

    editTextCategoryName = findViewById(R.id.editTextCategoryName);
    radioIncome = findViewById(R.id.radioIncome);
    radioExpense = findViewById(R.id.radioExpense);
    categoryTypeHintText = findViewById(R.id.categoryTypeHintText);
    deleteButton = findViewById(R.id.deleteButton);
    submitButton = findViewById(R.id.submitButton);

    setup();
  }

}
