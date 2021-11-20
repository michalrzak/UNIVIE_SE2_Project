package at.ac.univie.se2.ws21.team0404.app.model.categories;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableCategory extends Category implements Parcelable {

    public ParcelableCategory(EIncomeOrExpense type, String name) {
        super(type, name);
    }

    public ParcelableCategory(Category category) {
        super(category.getType(), category.getName());
    }

    public ParcelableCategory(Parcel in) {
        super(EIncomeOrExpense.valueOf(in.readString()), in.readString());
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getType().name());
        dest.writeString(this.getName());
    }
}