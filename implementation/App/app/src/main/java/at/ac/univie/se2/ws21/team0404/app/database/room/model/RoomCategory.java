package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;

@Entity(tableName = "categories")
public class RoomCategory extends Category {
    @NonNull
    @PrimaryKey
    private String name;

    public RoomCategory(@NonNull ETransactionType type, @NonNull String name, boolean disabled) {
        super(type, name);
        if (disabled) {
            this.disable();
        }
    }

    public RoomCategory(@NonNull Category category) {
        super(category);
    }
}
