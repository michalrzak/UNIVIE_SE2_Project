package at.ac.univie.se2.ws21.team0404.app.database;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;

public class TemporaryDB {

    private static List<AppAccount> list;

    public static List<AppAccount> getList() {
        if (list == null)
            createList();
        return list;
    }

    public static void addAppAccount(AppAccount appAccount) {
        if (list == null)
            createList();
        list.add(appAccount);
    }

    public static AppAccount changeData(AppAccount oldAccount, String name, EAccountType eAccountType){
        AppAccount newAppAccount = list.stream()
                                        .filter(appAccount -> appAccount.equals(oldAccount))
                                        .findFirst().orElse(null);
        newAppAccount.setName(name);
        newAppAccount.setType(eAccountType);
        return newAppAccount;
    }

    public static void removeAppAccount(AppAccount appAccount) {
        list.remove(appAccount);
    }

    private static void createList() {
        list = new ArrayList<>();
        list.add(new AppAccount(EAccountType.BANK, "Investment"));
        list.add(new AppAccount(EAccountType.CARD, "Savings"));
    }
}
