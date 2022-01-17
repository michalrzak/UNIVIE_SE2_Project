package at.ac.univie.se2.ws21.team0404.app.ui;

import androidx.annotation.NonNull;
import java.util.List;

/**
 * The base contract to be used by classes implementing {@link AListActivity}. Should be extended by
 * the specific Contracts.
 */
public interface IListActivityContract {

  interface IView<T> extends IBaseContract.IView {

    /**
     * Tell the View to display the passed list
     *
     * @param list the list to be displayed, cannot be null
     */
    void showList(@NonNull List<T> list);

    /**
     * Tell the view to show the redirect to the item passed.
     *
     * @param item the item from witch the redirect should be figured out, cannot be null
     */
    void showListItemRedirect(@NonNull T item);

    /**
     * Tell the view to show the Floating Action Button redirect
     */
    void showFabRedirect();
  }

  interface IPresenter<T> extends IBaseContract.IPresenter<IView<T>> {

    /**
     * Tell the presenter that the Floating Action Button was pressed
     */
    void clickFab();

    /**
     * Tell the presenter that the passed list item was pressed
     *
     * @param item the item which was pressed
     */
    void clickListItem(T item);
  }

}
