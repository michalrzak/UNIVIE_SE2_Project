package at.ac.univie.se2.ws21.team0404.app.model.android;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import java.util.UUID;

/**
 * Used to be able to send {@link Category} in Intents as Parcels.
 */
public class ParcelableCategory extends Category implements Parcelable {

  public static final Creator<ParcelableCategory> CREATOR = new Creator<ParcelableCategory>() {
    @Override
    public ParcelableCategory createFromParcel(Parcel in) {
      return new ParcelableCategory(in);
    }

    @Override
    public ParcelableCategory[] newArray(int size) {
      return new ParcelableCategory[size];
    }
  };

  public ParcelableCategory(@NonNull UUID id, @NonNull ETransactionType type,
      @NonNull String name) {
    super(id, type, name);
  }

  public ParcelableCategory(@NonNull Category category) {
    super(category);
  }

  public ParcelableCategory(@NonNull Parcel in) {
    super(((ParcelUuid) in.readParcelable(Parcelable.class.getClassLoader())).getUuid(),
        ETransactionType.values()[in.readInt()], in.readString());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeParcelable(new ParcelUuid(this.getId()), 1);
    parcel.writeInt(getType().ordinal());
    parcel.writeString(this.getName());
  }
}
