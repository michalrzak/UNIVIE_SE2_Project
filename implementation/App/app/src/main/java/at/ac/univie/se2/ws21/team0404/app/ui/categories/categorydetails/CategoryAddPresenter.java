package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import android.util.Log;
import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

public class CategoryAddPresenter extends ABasePresenter<ICategoryActivityContract.IView> implements
    ICategoryActivityContract.IPresenter {

  private final Repository repository;

  public CategoryAddPresenter(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void clickedSave(@Nullable Category category) {
    if (category == null) {
      view.showCategoryInsertionFailed();
      return;
    }

    IChangingData<ERepositoryReturnStatus> result = repository.createCategory(category);

    result.observe((newStatus) -> {
      switch (newStatus) {
        case SUCCESS:
          Log.d("CategoryAdd", "Inserting new category successful");
          view.showCategoryInsertionSuccessful();
          break;
        case ERROR:
          Log.d("CategoryAdd", "Inserting new category failed");
          view.showCategoryInsertionFailed();
          break;
        case UPDATING:
          // do nothing
      }
    });
  }

  /**
   * Not implemented as it is not needed!
   *
   * @param category category to be deleted
   */
  @Override
  public void clickedDelete(@Nullable Category category) {
  }
}
