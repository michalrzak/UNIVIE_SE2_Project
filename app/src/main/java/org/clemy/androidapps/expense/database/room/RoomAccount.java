package org.clemy.androidapps.expense.database.room;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;

/**
 * Extends the {@link Account} class with Room annotations
 */
@Entity(tableName = "accounts")
public class RoomAccount extends Account {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    public RoomAccount(@NonNull Integer id, @NonNull String name, @NonNull AccountType type, Double overdueLimit) {
        super(id, name, type, overdueLimit);
    }
    public RoomAccount(@NonNull final Account account) {
        super(account);
    }
}
