package org.clemy.androidapps.expense.ui.newaccount;

import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.ui.BaseContract;

public interface NewAccountContract {
    interface View extends BaseContract.View {
        void showAccountTypes(AccountType[] accountTypes);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void createNewAccount(String accountName, AccountType accountType, Double overdueLimit);
    }
}