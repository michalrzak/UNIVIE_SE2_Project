package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.Date;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;
import java.util.UUID;

/**
 * Holds the data of {@link Transaction} with Room annotations
 */
@Entity(tableName = "transactions")
public class RoomTransaction {
  @PrimaryKey
  @NonNull
  private final UUID id;
  @Nullable
  private final UUID categoryId;
  private final UUID accountId;
  private final String name;

  private final ETransactionType type;
  private final int amount; // in euro cent

  public RoomTransaction(@NonNull UUID id, @Nullable UUID categoryId, @NonNull ETransactionType type,
      int amount, @NonNull String name, UUID accountId, @NonNull Date date) {
    this.id = id;
    this.categoryId = categoryId;
    this.type = type;
    this.amount = amount;
    this.name = name;
    this.date = date;
    this.accountId = accountId;
  }

  public RoomTransaction(@NonNull Transaction transaction, @NonNull AppAccount appAccount) {
    this.id = transaction.getId();
    this.categoryId = transaction.getCategory().map(Category::getId).orElse(null);
    this.type = transaction.getType();
    this.amount = transaction.getAmount();
    this.name = transaction.getName();
    this.date = transaction.getDate();
    this.accountId = appAccount.getId();
  }

  @Nullable
  public UUID getCategoryId() {
    return categoryId;
  }

  public UUID getAccountId() {
    return accountId;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ETransactionType getType() {
    return type;
  }

  public int getAmount() {
    return amount;
  }

  public Date getDate() {
    return date;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RoomTransaction)) {
      return false;
    }
    RoomTransaction that = (RoomTransaction) o;

    boolean categoriesEqual = getCategoryId() == null ? that.getCategoryId() == null
        : getCategoryId().equals(that.getCategoryId());

    return getId() == that.getId()
        && getAccountId() == that.getAccountId()
        && getAmount() == that.getAmount()
        && categoriesEqual
        && getName().equals(that.getName())
        && getType() == that.getType();
  }

}
