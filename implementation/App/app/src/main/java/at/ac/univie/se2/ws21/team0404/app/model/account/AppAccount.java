package at.ac.univie.se2.ws21.team0404.app.model.account;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

/**
 * A class which holds information on one account.
 * <p>
 * This class is not simply called "Account" as there exists another class Account in the
 * android framework and I want to avoid having to deal with this.
 */
public class AppAccount implements Parcelable {

    private String name;
    private EAccountType type;

    public AppAccount(@NonNull EAccountType type, @NonNull String name) {
        this.name = name;
        this.type = type;
    }

    protected AppAccount(Parcel in) {
        name = in.readString();
        type = EAccountType.valueOf(in.readString().toUpperCase());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type.toString());
    }

    public static final Creator<AppAccount> CREATOR = new Creator<AppAccount>() {
        @Override
        public AppAccount createFromParcel(Parcel in) {
            return new AppAccount(in);
        }

        @Override
        public AppAccount[] newArray(int size) {
            return new AppAccount[size];
        }
    };

    public void setType(@NonNull EAccountType newType) {
        type = newType;
    }

    public void setName(@NonNull String newName) {
        name = newName;
    }

    public EAccountType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppAccount that = (AppAccount) o;
        return type == that.type && name.equals(that.name);
    }

}
