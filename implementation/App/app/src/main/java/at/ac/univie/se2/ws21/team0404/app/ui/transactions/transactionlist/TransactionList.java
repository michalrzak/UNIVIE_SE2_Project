package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.ParcelableTransaction;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.TransactionDetails;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.TransactionNew;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class TransactionList extends AListActivity<Transaction, TransactionListViewHolder> {

    @Nullable
    private AppAccount account;

    @NonNull
    private AppAccount getAccount() {
        // TODO: make this safe. What if no account was passed? probably throw onw exception
        if (account == null) {
            account = passedIntent.getParcelableExtra(EIntents.ACCOUNT.toString());
        }

        return account;
    }

    @Override
    protected Class getFabRedirect() {
        return TransactionNew.class;
    }

    @Override
    protected ListAdapter<Transaction, TransactionListViewHolder> getAdapter() {
        return new TransactionListAdapter(transaction -> {
            Intent intent = new Intent(this, TransactionDetails.class);
            intent.putExtra(EIntents.TRANSACTION.toString(), new ParcelableTransaction(transaction));
            startActivity(intent);
        });
    }

    @Override
    protected ChangingData<List<Transaction>> getList() {
        try {
            return Repository.getInstance().getTransactionList(getAccount());
        } catch (DataDoesNotExistException e) {
            Log.e("TransactionList_getList", "Tried to query for transactions of an account from the database, but an error was returned. Message: " + e.getMessage());
            Toast.makeText(this, "There seems to be a problem with the database. This account does not exist", Toast.LENGTH_LONG).show();
            finish();
        }
        
        // if an error occurred return empty list
        // this should never cause problems, as the catch block should redirect in that case
        // FIXME: take care of this branch!
        return null;
    }
}
