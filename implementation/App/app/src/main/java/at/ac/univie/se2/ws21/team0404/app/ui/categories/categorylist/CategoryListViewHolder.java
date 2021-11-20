package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public class CategoryListViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameView;

    public CategoryListViewHolder(@NonNull View itemView){
        super(itemView);
        nameView = itemView.findViewById(R.id.list_item_title);
    }

    public void bind(@NonNull Category category, Consumer<Category> onClickListener) {
        nameView.setText(category.toString());
        nameView.setOnClickListener(view -> onClickListener.accept(category));
    }

    static protected CategoryListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new CategoryListViewHolder(view);
    }
}
