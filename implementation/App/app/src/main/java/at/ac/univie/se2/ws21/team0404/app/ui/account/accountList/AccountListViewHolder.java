package at.ac.univie.se2.ws21.team0404.app.ui.account.accountList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public class AccountListViewHolder extends RecyclerView.ViewHolder {
    private final TextView accountNameView;

    public AccountListViewHolder(@NonNull View view) {
        super(view);
        accountNameView = view.findViewById(R.id.list_item_title);
    }

    public void bind(@NonNull AppAccount account, Consumer<AppAccount> onClickListener) {
        accountNameView.setText(account.getName());
        accountNameView.setOnClickListener(view -> onClickListener.accept(account));
    }

    static AccountListViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem, parent, false);
        return new AccountListViewHolder(view);
    }
}
