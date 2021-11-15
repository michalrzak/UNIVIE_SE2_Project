package at.ac.univie.se2.ws21.team0404.app.model.account;

import androidx.annotation.NonNull;

/**
 * A class which holds information on one account.
 *
 * This class is not simply called "Account" as there exists another class Account in the
 * android framework and I want to avoid having to deal with this.
 */
public class AppAccount {

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

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
