package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

/**
 * Join of Transaction and Category. Naming and design according
 * official Room documentation.
 */
public class RoomTransactionWithCategory {
    @Embedded
    RoomTransaction transaction;
    @Relation(
            parentColumn = "categoryName",
            entityColumn = "name"
    )
    @Nullable
    private RoomCategory category;

    public RoomTransactionWithCategory(RoomTransaction transaction, @Nullable RoomCategory category) {
        this.transaction = transaction;
        this.category = category;
    }

    public Transaction getTransaction() {
        return new Transaction(
                transaction.getId(),
                category,
                transaction.getType(),
                transaction.getAmount(),
                transaction.getName());
    }
}
