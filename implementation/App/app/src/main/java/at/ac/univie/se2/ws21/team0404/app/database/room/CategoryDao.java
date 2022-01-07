package at.ac.univie.se2.ws21.team0404.app.database.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomCategory;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name ASC")
    List<RoomCategory> getCategories();

    @Insert
    void addCategory(RoomCategory category);

    @Update
    void updateCategory(RoomCategory category);
}
