package org.clemy.androidapps.expense.database.room;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;

@Entity(tableName = "accounts")
public class RoomAccount extends Account {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    public RoomAccount(@NonNull Integer id, @NonNull String name, @NonNull AccountType type) {
        super(id, name, type);
    }
    public RoomAccount(@NonNull final Account account) {
        super(account);
    }
}
