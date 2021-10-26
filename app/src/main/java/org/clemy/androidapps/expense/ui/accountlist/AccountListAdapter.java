package org.clemy.androidapps.expense.ui.accountlist;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import org.clemy.androidapps.expense.model.Account;

public class AccountListAdapter extends ListAdapter<Account, AccountListItemViewHolder> {

    protected AccountListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public AccountListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AccountListItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(AccountListItemViewHolder holder, int position) {
        holder.bind(getItem(position));
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
