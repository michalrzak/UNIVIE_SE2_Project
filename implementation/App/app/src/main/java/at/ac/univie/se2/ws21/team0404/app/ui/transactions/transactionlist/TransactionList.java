package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.database.IDatabase;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.TemporaryDB;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.TransactionNew;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import java.util.ArrayList;
import java.util.Collection;

import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import java.util.List;
import java.util.stream.Collectors;

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
        Repository repository = Repository.getInstance();
        IDatabase database = repository.getDatabase();
        
        try {
            return database.getTransactions(getAccount()).stream().collect(Collectors.toList());
        } catch (DataDoesNotExistException e) {
            // TODO: redirect back to accounts screen
        }
        
        // if an error occured return empty list
        // this should never cause problems, as the catch block should redirect in that case
        // TODO: can an assert be used here?
        // assert(false); 
        
        return new ArrayList<>();
    }
}
