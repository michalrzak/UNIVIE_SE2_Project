package org.clemy.androidapps.expense.ui.accountlist;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.ui.BaseContract;

public interface AccountListContract {
    interface View extends BaseContract.View {
        void showAccountList(@NonNull AccountList accounts);

        void showNewAccount();

        void showEditAccount(@NonNull Account account);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void newAccount();

        void clickAccount(@NonNull Account account);

        void switchToMemoryDb();

        void switchToSavedDb();
    }
}
