package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivityPresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataWithViewState;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import java.util.List;

public class CategoryListPresenter extends AListActivityPresenter<Category> {

  public CategoryListPresenter(Repository repository) {
    super(repository);
  }

  /**
   * This needs to be implemented to provide the correct data for the list
   */
  @Override
  public void viewCreated() {
    final IChangingData<List<Category>> categoryData =
        new ChangingDataWithViewState<>(repository.getCategoryList(), viewState);
    categoryData.observe(data -> view.showList(data));
  }
}
