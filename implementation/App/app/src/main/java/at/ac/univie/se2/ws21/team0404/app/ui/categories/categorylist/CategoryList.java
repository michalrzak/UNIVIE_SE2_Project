package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import android.content.Intent;
import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.categories.ParcelableCategory;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.AddOrEditCategoryActivity;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import java.util.List;

public class CategoryList extends AListActivity<Category, CategoryListViewHolder> {

  @Override
  protected Runnable getFabRedirect() {
    return () -> {
      Intent intent = new Intent(this, AddOrEditCategoryActivity.class);
      startActivity(intent);
    };
  }

  @Override
  protected ListAdapter<Category, CategoryListViewHolder> getAdapter() {
    return new CategoryListAdapter(category -> {
      Intent intent = new Intent(this, AddOrEditCategoryActivity.class);
      intent.putExtra(EIntents.CATEGORY.toString(), new ParcelableCategory(category));
      startActivity(intent);
    });
  }

  @Override
  protected ChangingData<List<Category>> getList() {
    return Repository.getInstance().getCategoryList();
  }

  @Override
  protected int getListTitle() {
    return R.string.category_list;
  }
}
