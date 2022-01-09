package at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.IBaseContract;
import at.ac.univie.se2.ws21.team0404.app.ui.IListActivityContract;


public interface IAccountListContract {
  interface IView extends IListActivityContract.IView<AppAccount> {
    void showCategoriesList();
  }

  interface IPresenter extends IListActivityContract.IPresenter<AppAccount> {
    void categories();
  }

}
