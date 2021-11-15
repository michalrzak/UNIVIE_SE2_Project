package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListViewHolder> {

    private final List<AppAccount> accounts;

    public AccountListAdapter(@NonNull List<AppAccount> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public AccountListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_account, viewGroup, false);

        return new AccountListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountListViewHolder holder, int position) {
        holder.bind(accounts.get(position));
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }


}
