package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
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
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

public class TransactionList extends
    AListActivity<Transaction, TransactionListViewHolder, TransactionListPresenter> implements
    ITransactionListContract.IView {

  @Nullable
  private ChangingData<AppAccount> account;

  @NonNull
  private AppAccount getAccount() {
    // TODO: make this safe. What if no account was passed? probably throw onw exception
    if (account == null || account.getData() == null) {
      account = new ChangingData<>(passedIntent.getParcelableExtra(EIntents.ACCOUNT.toString()));
    }

    assert (account.getData() != null);
    return account.getData();
  }


  @Override
  protected ListAdapter<Transaction, TransactionListViewHolder> getAdapter() {
    return new TransactionListAdapter(transaction -> {
      presenter.clickListItem(transaction);
    });
  }

  @Override
  protected TransactionListPresenter getPresenter() {
    return TransactionListPresenter.create(getAccount(), Repository.getInstance());
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
      presenter.editAccount();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }


  @Override
  public void showListItemRedirect(@NonNull Transaction item) {
    Intent intent = new Intent(this, TransactionEdit.class);
    intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(getAccount()));
    intent.putExtra(EIntents.TRANSACTION.toString(), new ParcelableTransaction(item));
    startActivity(intent);
  }

  @Override
  public void showFabRedirect() {
    Intent intent = new Intent(this, TransactionAdd.class);
    intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(getAccount()));
    startActivity(intent);
  }

  /**
   * This is needed to handle the situation where the user edits an account and deletes it. After
   * that the application needs to redirect out of this activity, as the account saved here is no
   * longer valid.
   *
   * We launch {@link AccountEdit} with this and look if it returns a deleted boolean.
   */
  private final ActivityResultLauncher<Intent> accountDeleted = registerForActivityResult(
      new StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
          Intent resultIntent = result.getData();

          if (resultIntent != null) {
            boolean deleted = resultIntent.getBooleanExtra("deleted", false);
            if (deleted) {
              presenter.accountDeleted();
            }
          }
        }
      });

  @Override
  public void showEditAccount() {
    Intent intent = new Intent(this, AccountEdit.class);
    intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(getAccount()));
    accountDeleted.launch(intent);
  }

  @Override
  public void finishActivity() {
    finish();
  }
}
