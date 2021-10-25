package org.clemy.androidapps.expense.database.room;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;

@Entity(tableName = "accounts")
public class RoomAccount extends Account {
    public RoomAccount(@NonNull final Account account) {
        super(account);
    }
}
