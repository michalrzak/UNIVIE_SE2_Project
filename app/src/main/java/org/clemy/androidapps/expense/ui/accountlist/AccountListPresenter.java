package org.clemy.androidapps.expense.ui.accountlist;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.database.MemoryDb;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.database.RoomDb;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.ui.BasePresenter;
import org.clemy.androidapps.expense.utils.ChangingDataWithViewState;

import java.util.List;

/**
 * The Presenter for the AccountList activity.
 */
public class AccountListPresenter
        extends BasePresenter<AccountListContract.View>
        implements AccountListContract.Presenter {

    private final Repository repository;

    /**
     * @param repository the data repository for reading and updating the data.
     */
    public AccountListPresenter(Repository repository) {
        this.repository = repository;
    }

    /**
     * Setups the model -> view connection.
     */
    @Override
    public void viewCreated() {
        final ChangingDataWithViewState<List<Account>> accountsData =
                new ChangingDataWithViewState<>(repository.getAccounts(), viewState);
        accountsData.observe(data -> view.showAccountList(data));
    }

    /**
     * Handles the user wish to create a new account.
     */
    @Override
    public void newAccount() {
        view.showNewAccount();
    }

    @Override
    public void clickAccount(@NonNull Account account) {
        view.showEditAccount(account);
    }

    /**
     * Handles the user wish to switch to the test (memory) database.
     */
    @Override
    public void switchToMemoryDb() {
        repository.setDatabaseStrategy(new MemoryDb());
    }

    /**
     * Handles the user wish to switch to the real persisted database.
     */
    @Override
    public void switchToSavedDb() {
        repository.setDatabaseStrategy(new RoomDb());
    }
}
