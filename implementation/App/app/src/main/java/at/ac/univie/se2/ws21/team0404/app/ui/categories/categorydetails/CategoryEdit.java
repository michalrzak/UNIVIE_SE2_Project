package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import java.util.Optional;

public class CategoryEdit extends ACategoryActivity implements ICategoryActivityContract.IView {

  private Category passedCategory;

  private CategoryEditPresenter presenter;
  private LifecycleHandler<CategoryEdit> lifecycleHandler;

  @Override
  protected Optional<Category> getCategory() {
    Category category = super.getCategory().orElse(null);
    if (category == null) {
      return Optional.empty();
    }

    return Optional
        .of(new Category(passedCategory.getId(), category.getType(), category.getName()));
  }

  @Override
  protected void setup() {
    Intent passedIntent = getIntent();
    passedCategory = passedIntent
        .getParcelableExtra(EIntents.CATEGORY.toString());
    assert (passedCategory != null);

    presenter = CategoryEditPresenter.create(passedCategory, Repository.getInstance());

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

    lifecycleHandler = new LifecycleHandler<>(presenter, this);
  }

  @Override
  public void onSubmit(View view) {
    presenter.clickedSave(getCategory().orElse(null));
  }

  @Override
  public void onDelete(View view) {
    presenter.clickedDelete(passedCategory);
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

  @Override
  public void showCategoryDeletionSuccessful() {
    finish();
  }

  @Override
  public void showCategoryDeletionFailed() {
    Toast.makeText(getApplicationContext(), "Attempted to add already existing category!",
        Toast.LENGTH_SHORT).show();
  }
}
