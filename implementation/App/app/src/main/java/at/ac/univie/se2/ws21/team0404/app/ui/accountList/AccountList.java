package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.newOrAddAccount.NewOrAddAccountActivity;

// Part of the code here is temporary. It will be later moved into the Presenter Class

public class AccountList extends AListActivity<AppAccount, AccountListViewHolder> {

    @Override
    protected Class getFabRedirect() {
        return NewOrAddAccountActivity.class;
    }

    @Override
    protected ListAdapter<AppAccount, AccountListViewHolder> getAdapter() {
        return new AccountListAdapter();
    }

    @Override
    protected List<AppAccount> getList() {
        return TemporaryDB.getList();
    }
}