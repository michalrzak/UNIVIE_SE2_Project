package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

/**
 * Extends the {@link AppAccount} class with Room annotations
 */
@Entity(tableName = "accounts")
public class RoomAppAccount extends AppAccount {
    @PrimaryKey
    private int id;

    public RoomAppAccount(@NonNull String name, @NonNull EAccountType type, int id) {
        super(name, type, id);
    }

    public RoomAppAccount(@NonNull AppAccount appAccount) {
        super(appAccount.getName(), appAccount.getType(), appAccount);
    }
}
