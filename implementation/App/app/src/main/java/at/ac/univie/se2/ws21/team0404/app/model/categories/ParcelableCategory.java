package at.ac.univie.se2.ws21.team0404.app.model.categories;

import android.os.Parcel;
import android.os.Parcelable;

import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

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

  public ParcelableCategory(@NonNull ETransactionType type, @NonNull String name) {
    super(type, name);
  }

  public ParcelableCategory(@NonNull Category category) {
    super(category.getType(), category.getName());
  }

  public ParcelableCategory(@NonNull Parcel in) {
    super(ETransactionType.values()[in.readInt()], in.readString());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeInt(getType().ordinal());
    parcel.writeString(this.getName());
  }
}
