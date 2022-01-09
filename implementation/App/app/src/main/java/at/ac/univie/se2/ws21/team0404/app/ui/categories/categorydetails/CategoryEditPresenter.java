package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.ABasePresenter;
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
  public void clickedSave(Category category) {
    try {
      repository.updateCategory(editing.getName(), category);
      view.showCategoryInsertionSuccessful();
    } catch (DataDoesNotExistException | DataExistsException e) {
      //TODO: right now these two branches are handled equally. Maybe split them?
      view.showCategoryInsertionFailed();
    }
  }

  @Override
  public void clickedDelete(Category category) {
    category.disable(); // TODO: this is a side effect. Maybe add copy-constructor to Category?
    try {
      repository.updateCategory(editing.getName(), category);
      view.showCategoryDeletionSuccessful();
    } catch (DataDoesNotExistException| DataExistsException e) {
      view.showCategoryDeletionFailed();
    }
  }
}
