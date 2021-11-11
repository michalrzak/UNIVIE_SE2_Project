package org.clemy.androidapps.expense.ui.newtransaction;

import org.clemy.androidapps.expense.ui.BaseContract;

public interface NewTransactionContract {
    interface View extends BaseContract.View {
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void clickedSave(String transactionName);
    }
}