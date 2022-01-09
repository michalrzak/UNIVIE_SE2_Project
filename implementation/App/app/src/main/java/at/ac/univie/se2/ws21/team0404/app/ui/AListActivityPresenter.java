package at.ac.univie.se2.ws21.team0404.app.ui;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.ui.IListActivityContract.IView;

public abstract class AListActivityPresenter<T> extends ABasePresenter<IView<T>> implements
    IListActivityContract.IPresenter<T> {

  protected final Repository repository;

  public AListActivityPresenter(Repository repository) {
    this.repository = repository;
  }

  @Override
  public void clickFab() {
    assert (view != null);
    view.showFabRedirect();
  }

  @Override
  public void clickListItem(T item) {
    view.showListItemRedirect(item);
  }
}
