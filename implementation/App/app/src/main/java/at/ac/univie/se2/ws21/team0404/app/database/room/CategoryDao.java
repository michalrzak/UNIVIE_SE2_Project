package at.ac.univie.se2.ws21.team0404.app.database.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomCategory;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import java.util.List;

/**
 * The Dao (data access object) for categories. Is not an interface as fetching data from this table
 * is a little more complicated, as we do not delete unused categories, rater just set a disabled
 * field on them
 */
@Dao
public abstract class CategoryDao {

  @Query("SELECT * FROM categories ORDER BY name ASC")
  public abstract List<RoomCategory> getCategories();

  @Insert
  protected abstract void addCategory(RoomCategory category);

  @Update
  public abstract void updateCategory(RoomCategory category);

  @Query("SELECT COUNT(id) FROM categories WHERE name == :name AND disabled == 0")
  protected abstract long getEnabledCategory(String name);

  @Transaction
  public void addCategoryIfNotExist(RoomCategory category) throws DataExistsException {
    if (getEnabledCategory(category.getName()) > 0) {
      throw new DataExistsException("categories");
    }
    addCategory(category);
  }
}
