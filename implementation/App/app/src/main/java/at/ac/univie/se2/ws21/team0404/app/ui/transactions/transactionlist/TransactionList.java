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
import at.ac.univie.se2.ws21.team0404.app.ui.account.newOrAddAccount.NewOrAddAccountActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.TransactionDetails;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.TransactionNew;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import java.util.List;

public class TransactionList extends AListActivity<Transaction, TransactionListViewHolder> {

  @Nullable
  private AppAccount account;

  /**
   * Object, used to get result from TransactionNew. This activity submits upon completion the
   * transaction which was created in its form.
   * <p>
   * This object handles what should happen once the result is returned. Namely it validates some
   * fields, and tries to save the new transaction to the DB
   */
  private final ActivityResultLauncher<Intent> resultTransactionNew = registerForActivityResult(
      new StartActivityForResult(),
      result -> {
        Log.d("TransactionList_result", "TransactionNew returned the result: " + result.toString());

        switch (result.getResultCode()) {
          case Activity.RESULT_OK:
            assert (account != null);

            Intent intentRes = result.getData();
            assert (intentRes != null);
            Transaction transaction = intentRes.getParcelableExtra(EIntents.TRANSACTION.toString());

            try {
              Repository.getInstance().createTransaction(account, transaction);
              Log.d("TransactionList_result", "Successfully added transaction");
            } catch (DataDoesNotExistException e) {
              Log.e("TransactionList_result",
                  "Tried to add transaction to an account which does not exist. Message: " + e
                      .getMessage());
              Toast.makeText(this, "Error on saving transaction try again please.",
                  Toast.LENGTH_LONG)
                  .show();
              finish();
            } catch (DataExistsException e) {
              Log.e("TransactionList_result",
                  "Tried to add transaction, but transaction with this ID already exists. This should not happen. Message"
                      + e.getMessage());
              Toast
                  .makeText(this, "Error on saving transaction, try again please.",
                      Toast.LENGTH_LONG)
                  .show();
              // this does not need to finnish as the account seems to be still valid
              break;
            }
        }
      });

  /**
   * Object, used to get result from TransactionDetails. This activity submits upon completion the
   * transaction which was created in its form and the id of the transaction on which the form was
   * based on.
   * <p>
   * This object handles what should happen once the result is returned. Namely it validates some
   * fields, and tries to update transaction in the DB
   */
  private final ActivityResultLauncher<Intent> resultTransactionDetails = registerForActivityResult(
      new StartActivityForResult(),
      result -> {
        Log.d("TransactionList_result",
            "TransactionDetails returned the result: " + result.toString());

        assert (account != null);

        Intent intentRes = result.getData();

        switch (result.getResultCode()) {
          case Activity.RESULT_OK:

            assert (intentRes != null);
            Transaction transaction = intentRes.getParcelableExtra(EIntents.TRANSACTION.toString());

            int oldId = intentRes.getIntExtra(EIntents.TRANSACTION_ID.toString(), -1);
            assert (oldId != -1);

            try {
              Repository.getInstance().updateTransaction(account, oldId, transaction);
              Log.d("TransactionList_result", "Successfully updated transaction");
            } catch (DataDoesNotExistException e) {
              Log.e("TransactionList_result",
                  "Tried to update a transaction from an account which does not exist. Message: "
                      + e
                      .getMessage());
              Toast.makeText(this, "Error on saving transaction try again please.",
                  Toast.LENGTH_LONG)
                  .show();
              finish();
            }
            break;

          // Transaction was deleted
          case Activity.RESULT_FIRST_USER:
            assert (intentRes != null);
            int toBeDeleted = intentRes.getIntExtra(EIntents.TRANSACTION_ID.toString(), -1);
            assert (toBeDeleted != -1);

            try {
              Repository.getInstance().deleteTransaction(account, toBeDeleted);
              Log.d("TransactionList_result", "Successfully deleted transaction");
            } catch (DataDoesNotExistException e) {
              Log.e("TransactionList_result",
                  "Tried to delete a transaction from an account which does not exist or the"
                      + "to be deleted transaction does not exists. Message: "
                      + e
                      .getMessage());
              Toast.makeText(this, "Error on saving transaction try again please.",
                  Toast.LENGTH_LONG)
                  .show();
              finish();
              break;
            }
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
      Intent intent = new Intent(this, TransactionNew.class);
      resultTransactionNew.launch(intent);
    };
  }

  @Override
  protected ListAdapter<Transaction, TransactionListViewHolder> getAdapter() {
    return new TransactionListAdapter(transaction -> {
      Intent intent = new Intent(this, TransactionDetails.class);
      intent.putExtra(EIntents.TRANSACTION.toString(), new ParcelableTransaction(transaction));
      resultTransactionDetails.launch(intent);
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
    if (item.getItemId() == R.id.category_menu_icon) {
      Intent intent = new Intent(this, NewOrAddAccountActivity.class);
      intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(account));
      startActivity(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
