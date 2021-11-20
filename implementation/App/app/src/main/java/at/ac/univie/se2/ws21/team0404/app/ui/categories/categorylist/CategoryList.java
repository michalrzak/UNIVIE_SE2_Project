package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import androidx.recyclerview.widget.ListAdapter;

import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.se2.ws21.team0404.app.database.IDatabase;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.AddOrEditCategoryActivity;

public class CategoryList extends AListActivity<Category, CategoryListViewHolder> {
    @Override
    protected Class getFabRedirect() {
        return AddOrEditCategoryActivity.class;
    }

    @Override
    protected ListAdapter<Category, CategoryListViewHolder> getAdapter() {
        return new CategoryListAdapter();
    }

    @Override
    protected List<Category> getList() {
        IDatabase database = Repository.getInstance().getDatabase();

        return database.getCategories().values().stream()
                .filter(category -> !category.isDisabled())
                .collect(Collectors.toList());
    }
}
