package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivityPresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist.TransactionListPresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataWithViewState;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CategoryListPresenter extends AListActivityPresenter<Category> {

  public CategoryListPresenter(Repository repository) {
    super(repository);
  }

  private static Function<Repository, CategoryListPresenter> factory = CategoryListPresenter::new;

  public static CategoryListPresenter create(Repository repository) {
    return factory.apply(repository);
  }

  /**
   * Allows replacing the factory for dependency injection during unit tests
   *
   * @param factory mocked factory
   */
  public static void setFactory(
          Function<Repository, CategoryListPresenter> factory) {
    CategoryListPresenter.factory = factory;
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
