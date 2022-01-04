package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import android.view.View;
import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class CategoryAdd extends ACategoryActivity{


  @Override
  protected void setup() {
    radioIncome.toggle();
    deleteButton.setVisibility(View.GONE);
    submitButton.setText(R.string.create_category);
  }

  @Override
  public void onSubmit(View view) {
    Category newCategory = getCategory();

    try {
      Repository.getInstance().createCategory(newCategory);
      finish();
    } catch (DataExistsException e) {
      Toast.makeText(getApplicationContext(), "Attempted to add already existing category!",
          Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onDelete(View view) {
  }
}
