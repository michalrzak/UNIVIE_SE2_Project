package org.clemy.androidapps.expense.ui.transactionlist;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.TransactionList;
import org.clemy.androidapps.expense.ui.BaseContract;

public interface TransactionListContract {
    interface View extends BaseContract.View {
        void showTransactionList(@NonNull TransactionList transactions);

        void showNewTransaction(@NonNull Integer accountId);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void newTransaction();
    }
}
