package org.clemy.androidapps.expense.database.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.Transaction;

/**
 * Extends the {@link Account} class with Room annotations
 */
@Entity(tableName = "transactions")
public class RoomTransaction extends Transaction {
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    public RoomTransaction(@NonNull Integer id, @NonNull Integer accountId, @NonNull String name, @NonNull Double amount) {
        super(id, accountId, name, amount);
    }

    public RoomTransaction(@NonNull final Transaction transaction) {
        super(transaction);
    }
}
