package org.clemy.androidapps.expense.ui.transactionlist;

import androidx.annotation.NonNull;

import org.clemy.androidapps.expense.model.Transaction;
import org.clemy.androidapps.expense.ui.BaseContract;

import java.util.List;

public interface TransactionListContract {
    interface View extends BaseContract.View {
        void showAccountLoading();

        void showAccountInformation(@NonNull String accountName, boolean overdue);

        void showTransactionList(@NonNull List<Transaction> transactions);

        void showEditAccount(@NonNull Integer accountId);

        void showNewTransaction(@NonNull Integer accountId);
    }

    interface Presenter extends BaseContract.Presenter<View> {
        void clickedEditAccount();

        void clickedNewTransaction();
    }
}
