package org.clemy.androidapps.expense.ui.newaccount;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.model.AccountWithTransactions;
import org.clemy.androidapps.expense.ui.BasePresenter;
import org.clemy.androidapps.expense.utils.ChangingDataOnMainThread;
import org.clemy.androidapps.expense.utils.ChangingDataWithViewState;

import java.util.Optional;

public class NewAccountPresenter extends BasePresenter<NewAccountContract.View> implements NewAccountContract.Presenter {
    @NonNull
    private final Repository repository;
    @NonNull
    private final Optional<Integer> accountId;

    public NewAccountPresenter(@NonNull Repository repository, Integer accountId) {
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
        final ChangingDataWithViewState<AccountWithTransactions> accountData =
                new ChangingDataWithViewState<>(
                        new ChangingDataOnMainThread<>(
                                repository.getAccountWithTransactions(accountId)
                        ),
                        viewState);
        accountData.observe(data -> data.getAccount().ifPresent(view::showAccountData));
    }

    @Override
    public void clickedSave(String accountName, AccountType accountType, Double overdueLimit) {
        final Account newOrChangedAccount =
                new Account(accountId.orElse(null), accountName, accountType, overdueLimit);
        repository.createOrUpdateAccount(newOrChangedAccount);
    }
}
