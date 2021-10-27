package org.clemy.androidapps.expense.ui.accountlist;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import org.clemy.androidapps.expense.model.Account;

public class AccountListAdapter extends ListAdapter<Account, AccountListItemViewHolder> {

    private final Consumer<Account> onClick;

    protected AccountListAdapter(Consumer<Account> onClick) {
        super(DIFF_CALLBACK);
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public AccountListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AccountListItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AccountListItemViewHolder holder, int position) {
        holder.bind(getItem(position), onClick);
    }

    public static final DiffUtil.ItemCallback<Account> DIFF_CALLBACK = new DiffUtil.ItemCallback<Account>() {
        @Override
        public boolean areItemsTheSame(@NonNull Account oldItem, @NonNull Account newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Account oldItem, @NonNull Account newItem) {
            return oldItem.equals(newItem);
        }
    };
}
