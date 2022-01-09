package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.IListActivityContract;

public interface ICategoryListContract {

  interface IView extends IListActivityContract.IView<Category> {

  }

  interface IPresenter extends IListActivityContract.IView<Category> {

  }

}
