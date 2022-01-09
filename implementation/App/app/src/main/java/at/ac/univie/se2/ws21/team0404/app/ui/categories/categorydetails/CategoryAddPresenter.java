package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class CategoryAddPresenter extends ABasePresenter<ICategoryActivityContract.IView> implements
    ICategoryActivityContract.IPresenter {

  private final Repository repository;

  public CategoryAddPresenter(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void clickedSave(Category category) {
    try {
      repository.createCategory(category);
      view.showCategoryInsertionSuccessful();
    } catch (DataExistsException e) {
      view.showCategoryInsertionFailed();
    }
  }

  /**
   * Not implemented as it is not needed!
   * @param category category to be deleted
   */
  @Override
  public void clickedDelete(Category category) { }
}
