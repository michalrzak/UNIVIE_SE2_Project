package at.ac.univie.se2.ws21.team0404.app.ui;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.ui.IListActivityContract.IView;

/**
 * An abstract presenter class that can be used to more simply implement presenters for the {@link
 * AListActivity}
 *
 * @param <T> The class that will be displayed in the {@link AListActivity}
 */
public abstract class AListActivityPresenter<T> extends ABasePresenter<IView<T>> implements
    IListActivityContract.IPresenter<T> {

  protected final Repository repository;

  public AListActivityPresenter(Repository repository) {
    this.repository = repository;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clickFab() {
    assert (view != null);
    view.showFabRedirect();
  }

  /**
   * {@inheritDoc}
   * @param item the item which was pressed
   */
  @Override
  public void clickListItem(T item) {
    view.showListItemRedirect(item);
  }
}
