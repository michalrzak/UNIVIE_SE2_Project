package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import android.util.Log;
import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class CategoryEditPresenter extends
    ABasePresenter<ICategoryActivityContract.IView> implements
    ICategoryActivityContract.IPresenter {

  private final Repository repository;

  private final Category editing;

  public CategoryEditPresenter(Category editing, Repository repository) {
    this.editing = editing;
    this.repository = repository;
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
