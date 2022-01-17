package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import java.util.UUID;

/**
 * Extends the {@link Category} class with Room annotations
 * <p>
 * Defines the way {@link Category} is saved in the SQL database. I uses the {@link
 * Category#getId()} field as the primary key.
 */
@Entity(tableName = "categories")
public class RoomCategory extends Category {

  @NonNull
  @PrimaryKey
  private UUID id;

  /**
   * Constructs the {@link RoomCategory} with the given parameters
   *
   * @param id       the id of the category, cannot be null
   * @param type     the type of the category, cannot be null
   * @param name     the name of the category, cannot be null
   * @param disabled whether the category is disabled or not
   */
  public RoomCategory(@NonNull UUID id, @NonNull ETransactionType type, @NonNull String name,
      boolean disabled) {
    super(id, type, name, disabled);
  }

  /**
   * Constructs the {@link RoomCategory} using the given category
   *
   * @param category the category that which values will be used to construct {@link RoomCategory}
   */
  public RoomCategory(@NonNull Category category) {
    super(category);
  }
}
