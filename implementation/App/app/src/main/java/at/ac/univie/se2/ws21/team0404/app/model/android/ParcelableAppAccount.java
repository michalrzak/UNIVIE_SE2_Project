package at.ac.univie.se2.ws21.team0404.app.model.android;

import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

/**
 * Used to be able to send the {@link AppAccount} objects in intents as Parcels.
 */
public class ParcelableAppAccount extends AppAccount implements Parcelable {

  public static final Creator<AppAccount> CREATOR = new Creator<AppAccount>() {
    @Override
    public AppAccount createFromParcel(Parcel in) {
      return new ParcelableAppAccount(in);
    }

    @Override
    public AppAccount[] newArray(int size) {
      return new AppAccount[size];
    }
  };

  public ParcelableAppAccount(@NonNull Parcel in) {
    super(in.readString(), EAccountType.valueOf(in.readString().toUpperCase()),
        ((ParcelUuid) in.readParcelable(Parcelable.class.getClassLoader())).getUuid(),
        in.readDouble(), in.readDouble());
  }

  public ParcelableAppAccount(@NonNull AppAccount appAccount) {
    super(appAccount);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.getName());
    parcel.writeString(this.getType().toString());
    parcel.writeParcelable(new ParcelUuid(this.getId()), 1);
    parcel.writeDouble(this.getSpendingLimit());
    parcel.writeDouble(this.getBalance());
  }
}
