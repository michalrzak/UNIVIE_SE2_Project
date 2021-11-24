package at.ac.univie.se2.ws21.team0404.app.ui.account.accountList;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.account.addOrEditAccount.AddOrEditAccountActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist.CategoryList;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist.TransactionList;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import java.util.List;

public class AccountList extends AListActivity<AppAccount, AccountListViewHolder> {

  @Override
  protected Runnable getFabRedirect() {
    return () -> {
      Intent intent = new Intent(this, AddOrEditAccountActivity.class);
      startActivity(intent);
    };
  }

  @Override
  protected ListAdapter<AppAccount, AccountListViewHolder> getAdapter() {
    return new AccountListAdapter(account -> {
      Intent intent = new Intent(this, TransactionList.class);
      intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(account));
      startActivity(intent);
    });
  }

  @Override
  protected IChangingData<List<AppAccount>> getList() {
    return Repository.getInstance().getAccountList();
  }

  @Override
  protected int getListTitle() {
    return R.string.account_list;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.category_menu_icon, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.category_menu_icon) {
      Intent intent = new Intent(this, CategoryList.class);
      startActivity(intent);
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}