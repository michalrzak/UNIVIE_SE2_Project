package at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivityPresenter;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataWithViewState;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import java.util.List;
import java.util.function.Function;

public class AccountListPresenter extends AListActivityPresenter<AppAccount> implements
    IAccountListContract.IPresenter {

  private static Function<Repository, AccountListPresenter> factory = AccountListPresenter::new;
  public static AccountListPresenter create(Repository repository) {
    return factory.apply(repository);
  }

  /**
   * Allows replacing the factory for dependency injection during unit tests
   *
   * @param factory mocked factory
   */
  public static void setFactory(Function<Repository, AccountListPresenter> factory) {
    AccountListPresenter.factory = factory;
  }

  private AccountListPresenter(Repository repository) {
    super(repository);
  }

  /**
   * This needs to be implemented to provide the correct data for the list
   */
  @Override
  public void viewCreated() {
    final IChangingData<List<AppAccount>> accountsData =
        new ChangingDataWithViewState<>(repository.getAccountList(), viewState);
    accountsData.observe(data -> view.showList(data));
  }

  @Override
  public void categories() {
    //TODO: Surely this can be done better
    assert (view instanceof IAccountListContract.IView);
    IAccountListContract.IView myView = (IAccountListContract.IView) view;
    myView.showCategoriesList();
  }

  @Override
  public void report() {
    assert (view instanceof IAccountListContract.IView);
    IAccountListContract.IView myView = (IAccountListContract.IView) view;
    myView.showReportForm();
  }
}
