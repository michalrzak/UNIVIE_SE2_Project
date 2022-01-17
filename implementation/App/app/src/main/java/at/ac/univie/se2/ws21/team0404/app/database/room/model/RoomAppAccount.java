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
 * <p>
 * Defines the way {@link AppAccount} is saved in the SQL database. I uses the {@link
 * AppAccount#getId()} field as the primary key.
 */
@Entity(tableName = "accounts", indices = {@Index(value = "name", unique = true)})
public class RoomAppAccount extends AppAccount {

  @PrimaryKey
  @NonNull
  private UUID id;

  /**
   * Constructs {@link RoomAppAccount} with the provided parameters.
   *
   * @param name          the name of the account, cannot be null
   * @param type          the type of the account, cannot be null
   * @param id            the id of the account, cannot be null
   * @param spendingLimit the spending limit of the account
   * @param balance       the balance of the account
   */
  public RoomAppAccount(@NonNull String name, @NonNull EAccountType type, @NonNull UUID id,
      double spendingLimit, double balance) {
    super(name, type, id, spendingLimit, balance);
  }

  /**
   * Constructs {@link RoomAppAccount} with the fields from the passed account
   *
   * @param appAccount the account which will be used for initialisation, cannot be null
   */
  public RoomAppAccount(@NonNull AppAccount appAccount) {
    super(appAccount);
  }

}
