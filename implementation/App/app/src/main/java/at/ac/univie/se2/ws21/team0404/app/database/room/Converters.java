package at.ac.univie.se2.ws21.team0404.app.database.room;

import androidx.room.TypeConverter;

import java.util.Date;

// Simple converters for Room, inspired by
// https://developer.android.com/training/data-storage/room/referencing-data
public class Converters {
    @TypeConverter
    public static Date dateFromTimestamp(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date != null ? date.getTime() : null;
    }
}
