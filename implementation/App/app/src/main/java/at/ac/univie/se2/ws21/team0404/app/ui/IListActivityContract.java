package at.ac.univie.se2.ws21.team0404.app.ui;

import androidx.annotation.NonNull;
import java.util.List;

public interface IListActivityContract {

  interface IView<T> extends IBaseContract.IView {
    void showList(@NonNull List<T> list);

    void showListItemRedirect(@NonNull T item);

    void showFabRedirect();
  }

  interface IPresenter<T> extends IBaseContract.IPresenter<IView<T>> {
    void clickFab();

    void clickListItem(T item);
  }

}
