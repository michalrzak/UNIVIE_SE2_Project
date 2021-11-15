package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;

public class AccountListViewHolder extends RecyclerView.ViewHolder {
    private final TextView accountNameView;

    public AccountListViewHolder(@NonNull View view) {
        super(view);
        accountNameView = view.findViewById(R.id.account_name);
    }

    public void bind(@NonNull AppAccount account) {
        accountNameView.setText(account.getName());
    }

    static AccountListViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_account, parent, false);
        return new AccountListViewHolder(view);
    }
}
