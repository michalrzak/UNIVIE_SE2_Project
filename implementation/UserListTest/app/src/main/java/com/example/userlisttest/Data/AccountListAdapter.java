package com.example.userlisttest.Data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userlisttest.R;

import java.util.List;
import java.util.Optional;

/* I don't really understand the structure of this class, will have to read up on ReclerView a bit more
 */
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder> {

    private final LayoutInflater inflater;
    private Optional<List<Account>> accounts = Optional.empty();

    public AccountListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    @NonNull
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        if (accounts.isPresent()) {
            Account current = accounts.get().get(position);
            holder.accountView.setText(current.getAccountName());
        }
        else {
            holder.accountView.setText("No Account Name found!");
        }
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = Optional.of(accounts);
        // if I understand correctly, as we are changing the entire dataset here we need this notify
        // even though intelij is complaining
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (accounts.isPresent()) {
            return accounts.get().size();
        }
        return 0;
    }


    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView accountView;

        // can be accessed in the wrapper class!
        private AccountViewHolder(View view) {
            super(view);
            accountView = view.findViewById(R.id.textView);
        }
    }
}

