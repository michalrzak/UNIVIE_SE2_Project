package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

/**
 * Holds the data of {@link Transaction} with Room annotations
 */
@Entity(tableName = "transactions")
public class RoomTransaction {
    @PrimaryKey
    private final int id;
    @Nullable
    private final String categoryName;
    private final int accountId;
    private final String name;
    private final ETransactionType type;
    private final int amount; // in euro cent

    public RoomTransaction(int id, @Nullable String categoryName, @NonNull ETransactionType type,
                              int amount, @NonNull String name, int accountId) {
        this.id = id;
        this.categoryName = categoryName;
        this.type = type;
        this.amount = amount;
        this.name = name;
        this.accountId = accountId;
    }

    public RoomTransaction(@NonNull Transaction transaction, @NonNull AppAccount appAccount) {
        this.id = transaction.getId();
        this.categoryName = transaction.getCategory().map(Category::getName).orElse(null);
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.name = transaction.getName();
        this.accountId = appAccount.getId();
    }

    @Nullable
    public String getCategoryName() {
        return categoryName;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getId() {
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
}
