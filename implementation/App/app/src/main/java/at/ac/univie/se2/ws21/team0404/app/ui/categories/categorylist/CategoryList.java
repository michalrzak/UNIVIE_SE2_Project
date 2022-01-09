package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableCategory;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivityPresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails.CategoryAdd;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails.CategoryEdit;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import java.util.List;

public class CategoryList extends AListActivity<Category, CategoryListViewHolder, CategoryListPresenter> {

  @Override
  protected ListAdapter<Category, CategoryListViewHolder> getAdapter() {
    return new CategoryListAdapter(category -> {
      presenter.clickListItem(category);
    });
  }

  @Override
  protected CategoryListPresenter getPresenter() {
    return new CategoryListPresenter(Repository.getInstance());
  }

  @Override
  protected int getListTitle() {
    return R.string.category_list;
  }

  @Override
  public void showListItemRedirect(@NonNull Category item) {
    Intent intent = new Intent(this, CategoryEdit.class);
    intent.putExtra(EIntents.CATEGORY.toString(), new ParcelableCategory(item));
    startActivity(intent);
  }

  @Override
  public void showFabRedirect() {
    Intent intent = new Intent(this, CategoryAdd.class);
    startActivity(intent);
  }
}
