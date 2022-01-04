package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class CategoryEdit extends ACategoryActivity {

  private Category passedCategory;

  @Override
  protected void setup() {
    Intent passedIntent = getIntent();
    passedCategory = passedIntent
        .getParcelableExtra(EIntents.CATEGORY.toString());
    assert (passedCategory != null);

    editTextCategoryName.setText(passedCategory.getName());
    deleteButton.setVisibility(View.VISIBLE);
    submitButton.setText(R.string.save_category);

    switch (passedCategory.getType()) {
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

  private void saveCategory(Category category) {
    assert (passedCategory != null);
    try {
      Repository.getInstance().updateCategory(passedCategory.getName(), category);
      finish();
    } catch (DataExistsException e) {
      Toast.makeText(getApplicationContext(), "Attempted to add already existing category!",
          Toast.LENGTH_SHORT).show();
    } catch (DataDoesNotExistException e) {
      Toast.makeText(getApplicationContext(), "Attempted to edit a nonexistent category!",
          Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onSubmit(View view) {
    Category newCategory = getCategory();

    saveCategory(newCategory);
  }

  @Override
  public void onDelete(View view) {
    Category newCategory = getCategory();
    newCategory.disable();

    saveCategory(newCategory);
  }
}
