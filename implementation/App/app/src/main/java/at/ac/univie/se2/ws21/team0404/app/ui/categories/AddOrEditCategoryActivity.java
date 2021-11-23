package at.ac.univie.se2.ws21.team0404.app.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableCategory;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class AddOrEditCategoryActivity extends AppCompatActivity {

  private ParcelableCategory passedCategory;

  private EditText editTextCategoryName;
  private RadioButton radioIncome;
  private RadioButton radioExpense;
  private TextView categoryTypeHintText;
  private Button deleteButton;
  private Button submitButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_or_edit_category);

    ActionBar actionBar = getSupportActionBar();
    assert (actionBar != null);
    actionBar.setDisplayHomeAsUpEnabled(true);

    editTextCategoryName = findViewById(R.id.editTextCategoryName);
    radioIncome = findViewById(R.id.radioIncome);
    radioExpense = findViewById(R.id.radioExpense);
    categoryTypeHintText = findViewById(R.id.categoryTypeHintText);
    deleteButton = findViewById(R.id.deleteButton);
    submitButton = findViewById(R.id.submitButton);

    Intent passedIntent = getIntent();
    passedCategory = passedIntent
        .getParcelableExtra(EIntents.CATEGORY.toString());

    setup(passedCategory);
  }

  private void setup(Category category) {
    // No category - we are creating a new category and no setup is necessary
    if (category == null) {
      radioIncome.toggle();
      deleteButton.setVisibility(View.GONE);
      submitButton.setText(R.string.create_category);
      return;
    }

    editTextCategoryName.setText(category.getName());
    deleteButton.setVisibility(View.VISIBLE);
    submitButton.setText(R.string.save_category);

    switch (category.getType()) {
      case INCOME:
        radioIncome.toggle();
        break;
      case EXPENSE:
        radioExpense.toggle();
        break;
    }

    radioIncome.setEnabled(false);
    radioExpense.setEnabled(false);
    categoryTypeHintText
        .setText(getResources().getString(R.string.category_type_cannot_be_changed));
  }

  private void saveCategoryToDb(boolean shouldDeleteCategory) {
    ETransactionType type =
        radioIncome.isChecked() ? ETransactionType.INCOME : ETransactionType.EXPENSE;
    String name = shouldDeleteCategory
        ? passedCategory.getName()
        : editTextCategoryName.getText().toString();
    Category newCategory = new Category(type, name);
    if (shouldDeleteCategory) {
      newCategory.disable();
    }

    try {
      if (passedCategory == null) {
        Repository.getInstance().createCategory(newCategory);
      } else {
        Repository.getInstance().updateCategory(name, newCategory);
      }
      finish();
    } catch (DataExistsException e) {
      Toast.makeText(getApplicationContext(), "Attempted to add already existing category!",
          Toast.LENGTH_SHORT).show();
    } catch (DataDoesNotExistException e) {
      Toast.makeText(getApplicationContext(), "Attempted to edit a nonexistent category!",
          Toast.LENGTH_SHORT).show();
    }
  }

  public void onSubmit(View view) {
    saveCategoryToDb(false);
  }

  public void onDelete(View view) {
    saveCategoryToDb(true);
  }
}