package com.se2.tracker;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;


public class TransactionListAdapter extends ListAdapter<Transaction, TransactionViewHolder> {

    public TransactionListAdapter(@NonNull DiffUtil.ItemCallback<Transaction> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TransactionViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class TransactionDiff extends DiffUtil.ItemCallback<Transaction> {

        @Override
        public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
            return oldItem.getTransactionId() == newItem.getTransactionId();
        }
    }

}
