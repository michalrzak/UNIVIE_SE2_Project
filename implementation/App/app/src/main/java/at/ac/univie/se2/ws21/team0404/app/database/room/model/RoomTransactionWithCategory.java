package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

/**
 * Join of {@link Transaction} and {@link Category}. Naming and design according
 * official Room documentation.
 */
public class RoomTransactionWithCategory {
    @Embedded
    RoomTransaction transaction;
    @Relation(
            parentColumn = "categoryId",
            entityColumn = "id"
    )
    @Nullable
    private RoomCategory category;

    public RoomTransactionWithCategory(RoomTransaction transaction, @Nullable RoomCategory category) {
        this.transaction = transaction;
        this.category = category;
    }

    @NonNull
    public Transaction getTransaction() {
        return new Transaction(
                transaction.getId(),
                category,
                transaction.getType(),
                transaction.getAmount(),
                transaction.getName());
    }
}
