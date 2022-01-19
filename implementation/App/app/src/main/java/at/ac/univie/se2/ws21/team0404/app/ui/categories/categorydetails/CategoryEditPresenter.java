package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import android.util.Log;

import java.util.function.BiFunction;

import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

public class CategoryEditPresenter extends
    ABasePresenter<ICategoryActivityContract.IView> implements
    ICategoryActivityContract.IPresenter {

  private final Repository repository;

  private final Category editing;

  public CategoryEditPresenter(Category editing, Repository repository) {
    this.editing = editing;
    this.repository = repository;
  }

  private static BiFunction<Category, Repository, CategoryEditPresenter> factory = CategoryEditPresenter::new;

  public static CategoryEditPresenter create(Category category, Repository repository) {
    return factory.apply(category, repository);
  }

  /**
   * Allows replacing the factory for dependency injection during unit tests
   *
   * @param factory mocked factory
   */
  public static void setFactory(
          BiFunction<Category, Repository, CategoryEditPresenter> factory) {
    CategoryEditPresenter.factory = factory;
  }


  @Override
  public void clickedSave(@Nullable Category category) {
    if (category == null) {
      view.showCategoryInsertionFailed();
      return;
    }

    IChangingData<ERepositoryReturnStatus> result = repository.updateCategory(category);

    result.observe((newStatus) -> {
      switch (newStatus) {
        case SUCCESS:
          view.showCategoryInsertionSuccessful();
          break;
        case ERROR:
          view.showCategoryInsertionFailed();
          break;
        case UPDATING:
          // do nothing
      }
    });
  }

  @Override
  public void clickedDelete(@Nullable Category category) {
    if (category == null) {
      view.showCategoryDeletionFailed();
      return;
    }

    category.disable(); // TODO: this is a side effect. Maybe add copy-constructor to Category?

    IChangingData<ERepositoryReturnStatus> result = repository.updateCategory(category);

    result.observe((newStatus) -> {
      switch (newStatus) {
        case SUCCESS:
          Log.d("CategoryEdit", "Deleting category successful");
          view.showCategoryDeletionSuccessful();
          break;
        case ERROR:
          Log.d("CategoryEdit", "Deleting category failed");
          view.showCategoryDeletionFailed();
          break;
        case UPDATING:
          // do nothing
      }
    });
  }
}
