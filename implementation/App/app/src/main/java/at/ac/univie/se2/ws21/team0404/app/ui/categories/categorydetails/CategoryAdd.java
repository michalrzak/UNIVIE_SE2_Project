package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import android.view.View;
import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class CategoryAdd extends ACategoryActivity implements ICategoryActivityContract.IView {

  private final CategoryAddPresenter presenter = new CategoryAddPresenter(Repository.getInstance());
  private LifecycleHandler<CategoryAdd> lifecycleHandler;

  @Override
  protected void setup() {
    radioIncome.toggle();
    deleteButton.setVisibility(View.GONE);
    submitButton.setText(R.string.create_category);

    lifecycleHandler = new LifecycleHandler<>(presenter, this);
  }

  @Override
  public void onSubmit(View view) {
    presenter.clickedSave(getCategory());
  }

  @Override
  public void onDelete(View view) {
  }

  @Override
  public void showCategoryInsertionSuccessful() {
    finish();
  }

  @Override
  public void showCategoryInsertionFailed() {
    Toast.makeText(getApplicationContext(), "Attempted to add already existing category!",
        Toast.LENGTH_SHORT).show();
  }

  /**
   * Not needed.
   */
  @Override
  public void showCategoryDeletionSuccessful() {}

  @Override
  public void showCategoryDeletionFailed() {}
}
