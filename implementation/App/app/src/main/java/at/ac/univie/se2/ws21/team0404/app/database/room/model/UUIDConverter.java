package at.ac.univie.se2.ws21.team0404.app.database.room.model;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;
import java.util.UUID;

/**
 * Converter class to be used by {@link at.ac.univie.se2.ws21.team0404.app.database.room.AppRoomDatabase}
 * to convert the {@link UUID} fields to a representation, savable to the room database.
 */
public class UUIDConverter {

  @TypeConverter
  @Nullable
  public static String fromUUID(@Nullable UUID uuid) {
    return uuid == null ? null : uuid.toString();
  }

  @TypeConverter
  @Nullable
  public static UUID uuidFromString(@Nullable String uuid) {
    return uuid == null ? null : UUID.fromString(uuid);
  }

}
