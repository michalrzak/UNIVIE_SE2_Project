package com.se2.tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionViewHolder extends RecyclerView.ViewHolder{

    private TextView textId;
    private TextView textAmount;
    private TextView textCategory;

    public TransactionViewHolder(@NonNull View itemView) {
        super(itemView);
        textId = itemView.findViewById(R.id.idText);
        textAmount = itemView.findViewById(R.id.amountText);
        textCategory = itemView.findViewById(R.id.categoryText);
    }

    public void bind(Transaction transaction){
        textId.setText(String.valueOf(transaction.getTransactionId()));
        textAmount.setText(String.valueOf(transaction.getAmount()));
        textCategory.setText(transaction.getCategory());
    }

    static TransactionViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewHolder(view);
    }
}
