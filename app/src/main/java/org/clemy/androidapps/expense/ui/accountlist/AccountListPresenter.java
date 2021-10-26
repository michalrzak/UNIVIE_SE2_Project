package org.clemy.androidapps.expense.ui.accountlist;

import org.clemy.androidapps.expense.database.MemoryDb;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.database.RoomDb;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.ui.BasePresenter;
import org.clemy.androidapps.expense.utils.ChangingDataWithViewState;

public class AccountListPresenter extends BasePresenter<AccountListContract.View> implements AccountListContract.Presenter {
    private Repository repository;

    @Override
    public void viewCreated() {
        repository = Repository.getInstance();
        final ChangingDataWithViewState<AccountList> accountsData =
                new ChangingDataWithViewState<>(repository.getAccounts(), viewState);
        accountsData.observe(data -> view.showAccountList(data));
    }

    @Override
    public void newAccount() {
        view.showNewAccount();
    }

    @Override
    public void switchToMemoryDb() {
        repository.setDatabaseStrategy(new MemoryDb());
    }

    @Override
    public void switchToSavedDb() {
        repository.setDatabaseStrategy(new RoomDb());
    }
}
