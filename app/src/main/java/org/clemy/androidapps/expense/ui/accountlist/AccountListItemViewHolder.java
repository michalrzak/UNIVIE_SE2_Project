package org.clemy.androidapps.expense.ui.accountlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.model.Account;

public class AccountListItemViewHolder extends RecyclerView.ViewHolder {
    private final TextView descriptionView;

    public AccountListItemViewHolder(@NonNull View itemView) {
        super(itemView);
        descriptionView = itemView.findViewById(R.id.description);
    }

    public void bind(Account account, Consumer<Account> onClick) {
        descriptionView.setText(account.getName());
        itemView.setOnClickListener(view -> onClick.accept(account));
    }

    static AccountListItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_account, parent, false);
        return new AccountListItemViewHolder(view);
    }
}
