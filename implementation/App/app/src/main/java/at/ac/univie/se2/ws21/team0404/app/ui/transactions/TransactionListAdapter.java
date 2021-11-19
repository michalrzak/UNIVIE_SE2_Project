package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.AccountListAdapter;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.AccountListViewHolder;

public class TransactionListAdapter extends ListAdapter<Transaction, AccountListViewHolder> {

    public AccountListAdapter {
        super(appAccountDiffUtil);
    }


    @NonNull
    @Override
    public AccountListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AccountListViewHolder holder, int position) {

    }
}
