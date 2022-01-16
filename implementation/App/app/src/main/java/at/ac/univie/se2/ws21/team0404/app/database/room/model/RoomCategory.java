package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import java.util.UUID;

/**
 * Extends the {@link Category} class with Room annotations
 */
@Entity(tableName = "categories")
public class RoomCategory extends Category {
    @NonNull
    @PrimaryKey
    private UUID id;

    public RoomCategory(@NonNull UUID id, @NonNull ETransactionType type, @NonNull String name, boolean disabled) {
        super(id, type, name, disabled);
    }

    public RoomCategory(@NonNull Category category) {
        super(category);
    }
}
