package at.ac.univie.se2.ws21.team0404.app.model.account;

import android.os.Parcel;
import android.os.Parcelable;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public class ParcelableAppAccount extends AppAccount implements Parcelable {

    public ParcelableAppAccount(@NonNull Parcel in) {
        super(in.readString(), EAccountType.valueOf(in.readString().toUpperCase()), in.readInt());
    }

    public ParcelableAppAccount(@NonNull AppAccount appAccount){
        super(appAccount.getName(), appAccount.getType(), appAccount.getId());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getName());
        parcel.writeString(this.getType().toString());
        parcel.writeInt(this.getId());
    }

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
}
