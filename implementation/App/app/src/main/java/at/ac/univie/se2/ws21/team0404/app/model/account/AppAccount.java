package at.ac.univie.se2.ws21.team0404.app.model.account;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

/**
 * A class which holds information on one account.
 * <p>
 * This class is not simply called "Account" as there exists another class Account in the
 * android framework and I want to avoid having to deal with this.
 */
public class AppAccount {

    private String name;
    private EAccountType type;
    private final int id;

    // should maybe use another solution to create unique ids (UUID?)
    private static int idCounter = 0;

    public AppAccount(@NonNull String name, @NonNull EAccountType type) {
        this.name = name;
        this.type = type;
        id = idCounter++;
    }

    public AppAccount(@NonNull String name, @NonNull EAccountType type, int id) {
        this.name = name;
        this.type = type;
        this.id = id;
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

    public int getId(){
        return id;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppAccount)) return false;
        AppAccount that = (AppAccount) o;
        return name.equals(that.name) && type == that.type;
    }
}
