package org.clemy.androidapps.expense.ui.transactionlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.model.Transaction;

public class TransactionListItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView descriptionView;

    public TransactionListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        descriptionView = itemView.findViewById(R.id.description);
    }

    static TransactionListItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_account, parent, false);
        return new TransactionListItemViewHolder(view);
    }

    public void bind(Transaction transaction, Consumer<Transaction> onClick) {
        descriptionView.setText(transaction.getName());
        itemView.setOnClickListener(view -> onClick.accept(transaction));
    }
}
