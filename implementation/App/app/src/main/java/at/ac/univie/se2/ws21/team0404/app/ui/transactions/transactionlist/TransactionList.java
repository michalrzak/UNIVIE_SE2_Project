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
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivityPresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails.AccountEdit;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist.AccountListPresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist.IAccountListContract;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails.TransactionAdd;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails.TransactionEdit;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import java.util.List;

public class TransactionList extends AListActivity<Transaction, TransactionListViewHolder, TransactionListPresenter> implements
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
    return new TransactionListPresenter(getAccount(), Repository.getInstance());
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

  @Override
  public void showEditAccount() {
    Intent intent = new Intent(this, AccountEdit.class);
    intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(getAccount()));
    startActivity(intent);
  }
}
