package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import androidx.recyclerview.widget.ListAdapter;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

import java.util.Collection;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.AListActivity;
import at.ac.univie.se2.ws21.team0404.app.ui.newOrAddAccount.NewOrAddAccountActivity;
import java.util.List;

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