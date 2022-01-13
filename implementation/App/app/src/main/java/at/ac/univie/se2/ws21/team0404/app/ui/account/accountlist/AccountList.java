package at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.ListAdapter;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivityPresenter;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails.AccountAdd;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist.CategoryList;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist.TransactionList;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

// implements
//    IAccountListContract.IView
public class AccountList extends AListActivity<AppAccount, AccountListViewHolder, AccountListPresenter> implements
    IAccountListContract.IView {

  @Override
  protected ListAdapter<AppAccount, AccountListViewHolder> getAdapter() {
    return new AccountListAdapter(account -> presenter.clickListItem(account));
  }


  @Override
  protected AccountListPresenter getPresenter() {
    return AccountListPresenter.create(Repository.getInstance());
  }

  @Override
  protected int getListTitle() {
    return R.string.account_list;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ActionBar actionBar = getSupportActionBar();
    assert (actionBar != null);
    actionBar.setDisplayHomeAsUpEnabled(false);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.category_menu_icon, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.category_menu_icon) {
      presenter.categories();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void showListItemRedirect(@NonNull AppAccount item) {
    Intent intent = new Intent(this, TransactionList.class);
    intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(item));
    startActivity(intent);
  }

  @Override
  public void showFabRedirect() {
    Intent intent = new Intent(this, AccountAdd.class);
    startActivity(intent);
  }

  @Override
  public void showCategoriesList() {
    Intent intent = new Intent(this, CategoryList.class);
    startActivity(intent);
  }
}