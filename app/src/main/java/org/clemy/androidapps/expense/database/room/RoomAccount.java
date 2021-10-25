package org.clemy.androidapps.expense.database.room;

import androidx.room.Embedded;
import androidx.room.Entity;

import org.clemy.androidapps.expense.model.Account;

@Entity(tableName = "accounts")
public class RoomAccount {
    @Embedded
    Account account;
}
