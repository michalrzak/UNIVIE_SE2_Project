package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import java.util.UUID;

/**
 * Extends the {@link AppAccount} class with Room annotations
 */
@Entity(tableName = "accounts", indices = {@Index(value = "name", unique = true)})
public class RoomAppAccount extends AppAccount {

  @PrimaryKey
  @NonNull
  private UUID id;

  public RoomAppAccount(@NonNull String name, @NonNull EAccountType type, UUID id,
      double spendingLimit, double balance) {
    super(name, type, id, spendingLimit, balance);
  }

  public RoomAppAccount(@NonNull AppAccount appAccount) {
    super(appAccount);
  }

}
