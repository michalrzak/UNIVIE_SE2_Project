package org.clemy.androidapps.expense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountViewHolder extends RecyclerView.ViewHolder {
    private final TextView descriptionView;

    public AccountViewHolder(@NonNull View itemView) {
        super(itemView);
        descriptionView = itemView.findViewById(R.id.description);
    }

    public void bind(String text) {
        descriptionView.setText(text);
    }

    static AccountViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new AccountViewHolder(view);
    }
}
