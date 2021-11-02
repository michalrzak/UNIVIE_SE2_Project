package org.clemy.androidapps.expense.ui.transactionlist;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import org.clemy.androidapps.expense.model.Transaction;

public class TransactionListAdapter extends ListAdapter<Transaction, TransactionListItemViewHolder> {

    public static final DiffUtil.ItemCallback<Transaction> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transaction>() {
        @Override
        public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.equals(newItem);
        }
    };
    private final Consumer<Transaction> onClick;

    protected TransactionListAdapter(Consumer<Transaction> onClick) {
        super(DIFF_CALLBACK);
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public TransactionListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TransactionListItemViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(TransactionListItemViewHolder holder, int position) {
        holder.bind(getItem(position), onClick);
    }
}
