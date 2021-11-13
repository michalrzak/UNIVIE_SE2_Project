package org.clemy.androidapps.expense.ui.neweditaccount;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.model.AccountWithTransactions;
import org.clemy.androidapps.expense.ui.BasePresenter;
import org.clemy.androidapps.expense.utils.ChangingData;
import org.clemy.androidapps.expense.utils.ChangingDataWithViewState;

import java.util.Optional;

public class NewEditAccountPresenter extends BasePresenter<NewEditAccountContract.View> implements NewEditAccountContract.Presenter {
    @NonNull
    private final Repository repository;
    @NonNull
    private final Optional<Integer> accountId;

    public NewEditAccountPresenter(@NonNull Repository repository, Integer accountId) {
        this.repository = repository;
        this.accountId = Optional.ofNullable(accountId);
    }

    @Override
    public void viewCreated() {
        view.showAccountTypes(AccountType.values());
        accountId.ifPresent(this::setupEditMode);
    }

    private void setupEditMode(Integer accountId) {
        view.setEditMode();
        final ChangingData<AccountWithTransactions> accountData =
                new ChangingDataWithViewState<>(
                        repository.getAccountWithTransactions(accountId), viewState);
        accountData.observe(data -> data.getAccount().ifPresent(view::showAccountData));
    }

    @Override
    public void clickedSave(String accountName, AccountType accountType, Double overdueLimit) {
        final Account newOrChangedAccount =
                new Account(accountId.orElse(null), accountName, accountType, overdueLimit);
        repository.createOrUpdateAccount(newOrChangedAccount);
    }
}
