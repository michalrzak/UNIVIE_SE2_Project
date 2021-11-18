package at.ac.univie.se2.ws21.team0404.app.model.transaction;

import androidx.annotation.NonNull;

// TODO: where will we track whether a transaction is income/expense? We can either assign this
//  enum to transaction directly, or we can distinguish categories.
public enum ETransactionType {
    INCOME("Income"), EXPENSE("Expense");

    private final String type;

    ETransactionType(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return type;
    }
}
