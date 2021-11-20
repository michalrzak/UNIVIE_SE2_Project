package at.ac.univie.se2.ws21.team0404.app.model.transaction;

import android.os.Parcel;
import android.os.Parcelable;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

/**
 * A subclass of transaction, which implements the parcelable interface and allows to send
 *  these objects through intents.
 *
 * TODO: I would quite like for this entire package to be android-dependency free so we should
 *  find a new home for this class
 */
public class ParcelableTransaction extends Transaction implements Parcelable {

  public ParcelableTransaction(int id,
      @Nullable Category category,
      @NonNull ETransactionType type, int amount) {
    super(id, category, type, amount);
  }

  public ParcelableTransaction(@NonNull Transaction transaction) {
    this(transaction.getId(), transaction.getRawCategory(), transaction.getType(), transaction.getAmount());
  }

  protected ParcelableTransaction(@NonNull Parcel in) {
    this(in.readInt(),
        (Category) in.readValue(Category.class.getClassLoader()), // TODO: is this correct? I am unsure of the classloader
        ETransactionType.values()[in.readInt()],
        in.readInt());
  }


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

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(@NonNull Parcel parcel, int i) {
    parcel.writeInt(getId());
    // TODO: change this to parcelable. Problem: category can be null!!!
    parcel.writeValue(getRawCategory());
    // Unfortunately this looks like the best way to pass enums.
    parcel.writeInt(getType().ordinal());
    parcel.writeInt(getAmount());
  }
}
