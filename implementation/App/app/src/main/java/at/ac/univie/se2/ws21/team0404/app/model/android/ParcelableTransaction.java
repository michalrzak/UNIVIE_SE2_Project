package at.ac.univie.se2.ws21.team0404.app.model.android;

import android.os.Parcel;
import android.os.Parcelable;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

/**
 * A subclass of transaction, which implements the parcelable interface and allows to send these
 * objects through intents.
 */
public class ParcelableTransaction extends Transaction implements Parcelable {

  public static final Creator<ParcelableTransaction> CREATOR = new Creator<ParcelableTransaction>() {
    @Override
    public ParcelableTransaction createFromParcel(Parcel in) {
      return new ParcelableTransaction(in);
    }

    @Override
    public ParcelableTransaction[] newArray(int size) {
      return new ParcelableTransaction[size];
    }
  };

  public ParcelableTransaction(@Nullable Category category,
                               @NonNull ETransactionType type, int amount, String name) {
    super(category, type, amount, name);
  }

  public ParcelableTransaction(@NonNull Transaction transaction) {
    super(transaction);
  }


  protected ParcelableTransaction(@NonNull Parcel in) {
    super(in.readInt(),
        (Category) in.readValue(Category.class.getClassLoader()),
        // TODO: is this correct? I am unsure of the classloader
        ETransactionType.values()[in.readInt()],
        in.readInt(),
        in.readString());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel parcel, int i) {
    parcel.writeInt(getId());

    // Categories can be null.
    Category category = getRawCategory();
    ParcelableCategory parcelableCategory =
        category != null ? new ParcelableCategory(category) : null;
    parcel.writeValue(parcelableCategory);

    // Unfortunately this looks like the best way to pass enums.
    parcel.writeInt(getType().ordinal());
    parcel.writeInt(getAmount());

    parcel.writeString(getName());
  }
}
