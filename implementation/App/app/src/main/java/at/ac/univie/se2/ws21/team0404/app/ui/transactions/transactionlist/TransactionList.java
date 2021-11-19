package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.TemporaryDB;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.TransactionNew;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;

public class TransactionList extends AListActivity<Transaction, TransactionListViewHolder> {

    @Nullable private AppAccount account;

    @NonNull
    private AppAccount getAccount() {
        // TODO: make this safe. What if no account was passed? probably throw onw exception
        if (account == null) {
            account = (AppAccount) passedIntent.getSerializableExtra(AppAccount.class.getName());
        }
        return account;
    }

    @Override
    protected Class getFabRedirect() {
        return TransactionNew.class;
    }

    @Override
    protected ListAdapter<Transaction, TransactionListViewHolder> getAdapter() {
        return new TransactionListAdapter();
    }

    @Override
    protected List<Transaction> getList() {
        return TemporaryDB.getTList(getAccount());
    }
}
