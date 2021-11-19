package at.ac.univie.se2.ws21.team0404.app.model.account;

import androidx.annotation.NonNull;

import java.io.Serializable;

/**
 * A class which holds information on one account.
 * <p>
 * This class is not simply called "Account" as there exists another class Account in the
 * android framework and I want to avoid having to deal with this.
 */
public class AppAccount implements Serializable {

    private EAccountType type;
    private String name;

    public AppAccount(@NonNull EAccountType type, @NonNull String name) {
        this.type = type;
        this.name = name;
    }

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
