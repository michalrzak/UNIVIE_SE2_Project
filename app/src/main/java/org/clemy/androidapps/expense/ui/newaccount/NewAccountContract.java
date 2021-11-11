package org.clemy.androidapps.expense.ui.newaccount;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.ui.BaseContract;

public interface NewAccountContract {
    interface View extends BaseContract.View {
        void showAccountTypes(AccountType[] accountTypes);
        void setEditMode();
        void showAccountData(Account account);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void clickedSave(String accountName, AccountType accountType, Double overdueLimit);
    }
}