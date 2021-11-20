package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import android.content.Intent;

import androidx.recyclerview.widget.ListAdapter;

import java.util.List;
import java.util.stream.Collectors;

import at.ac.univie.se2.ws21.team0404.app.database.IDatabase;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.categories.ParcelableCategory;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.AddOrEditCategoryActivity;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

public class CategoryList extends AListActivity<Category, CategoryListViewHolder> {
    @Override
    protected Class getFabRedirect() {
        return AddOrEditCategoryActivity.class;
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
    protected List<Category> getList() {
        IDatabase database = Repository.getInstance().getDatabase();

        return database.getCategories().stream()
                .filter(category -> !category.isDisabled())
                .collect(Collectors.toList());
    }
}
