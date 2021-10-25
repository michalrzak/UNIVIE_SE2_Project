package org.clemy.androidapps.expense;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class AccountListAdapter extends ListAdapter<String, AccountViewHolder> {

    protected AccountListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AccountViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public static final DiffUtil.ItemCallback<String> DIFF_CALLBACK = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };
}
