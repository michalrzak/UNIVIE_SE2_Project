package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ListAdapter;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableTransaction;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails.AccountEdit;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails.TransactionAdd;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails.TransactionEdit;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import java.util.List;

public class TransactionList extends AListActivity<Transaction, TransactionListViewHolder> {

  @Nullable
  private AppAccount account;

  /**
   * Object, used to get the result from AccountEdit. Updates the currently stored
   * account to the new one.
   */
  private final ActivityResultLauncher<Intent> resultAccountEdit = registerForActivityResult(
      new StartActivityForResult(),
      result -> {
        switch (result.getResultCode()) {
          case Activity.RESULT_OK:
            Intent intentRes = result.getData();
            assert (intentRes != null);
            account = intentRes.getParcelableExtra(EIntents.ACCOUNT.toString());
        }
      });

  @NonNull
  private AppAccount getAccount() {
    // TODO: make this safe. What if no account was passed? probably throw onw exception
    if (account == null) {
      account = passedIntent.getParcelableExtra(EIntents.ACCOUNT.toString());
    }

    return account;
  }


  @Override
  protected Runnable getFabRedirect() {
    return () -> {
      Intent intent = new Intent(this, TransactionAdd.class);
      assert (account != null);
      intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(account));
      startActivity(intent);
    };
  }

  @Override
  protected ListAdapter<Transaction, TransactionListViewHolder> getAdapter() {
    return new TransactionListAdapter(transaction -> {
      Intent intent = new Intent(this, TransactionEdit.class);
      assert (account != null);
      intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(account));
      intent.putExtra(EIntents.TRANSACTION.toString(), new ParcelableTransaction(transaction));
      startActivity(intent);
    });
  }

  @Override
  protected IChangingData<List<Transaction>> getList() {
    try {
      return Repository.getInstance().getTransactionList(getAccount());
    } catch (DataDoesNotExistException e) {
      Log.e("TransactionList_getList",
          "Tried to query for transactions of an account from the database, but an error was returned. Message: "
              + e.getMessage());
      Toast.makeText(this,
          "There seems to be a problem with the database. This account does not exist",
          Toast.LENGTH_LONG).show();
      finish();
    }

    // if an error occurred return empty list
    // this should never cause problems, as the catch block should redirect in that case
    // FIXME: take care of this branch!
    return null;
  }

  @Override
  protected int getListTitle() {
    return R.string.transaction_list;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.edit_menu_icon, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.edit_menu_icon) {
      assert (account != null);
      Intent intent = new Intent(this, AccountEdit.class);
      intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(account));
      resultAccountEdit.launch(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
