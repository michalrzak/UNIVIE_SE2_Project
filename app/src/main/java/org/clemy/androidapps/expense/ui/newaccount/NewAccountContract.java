package org.clemy.androidapps.expense.ui.newaccount;

import org.clemy.androidapps.expense.ui.BaseContract;

public interface NewAccountContract {
    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void createNewAccount(String accountName);
    }
}