package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import android.view.ViewGroup;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public class CategoryListAdapter extends ListAdapter<Category, CategoryListViewHolder> {

  public static final DiffUtil.ItemCallback<Category> categoryDiffUtil = new DiffUtil.ItemCallback<Category>() {
    @Override
    public boolean areItemsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
      return oldItem == newItem;
    }

    @Override
    public boolean areContentsTheSame(@NonNull Category oldItem, @NonNull Category newItem) {
      return oldItem.equals(newItem);
    }
  };
  private final Consumer<Category> onClickListener;

  public CategoryListAdapter(Consumer<Category> onClickListener) {
    super(categoryDiffUtil);
    this.onClickListener = onClickListener;
  }

  @NonNull
  @Override
  public CategoryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return CategoryListViewHolder.create(parent);
  }

  @Override
  public void onBindViewHolder(@NonNull CategoryListViewHolder holder, int position) {
    holder.bind(getItem(position), onClickListener);
  }

}
