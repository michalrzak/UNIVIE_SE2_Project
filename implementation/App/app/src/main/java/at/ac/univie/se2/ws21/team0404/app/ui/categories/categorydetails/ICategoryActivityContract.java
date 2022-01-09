package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;

public interface ICategoryActivityContract {

  interface IView extends IBaseContract.IView {
    void showCategoryInsertionSuccessful();
    void showCategoryInsertionFailed();

    void showCategoryDeletionSuccessful();
    void showCategoryDeletionFailed();
  }

  interface IPresenter extends IBaseContract.IPresenter<IView> {
    void clickedSave(Category category);
    void clickedDelete(Category category);
  }

}
