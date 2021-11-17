package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

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

    public static void addAppAccount(AppAccount appAccount){
        if (list == null)
            createList();
        list.add(appAccount);
    }

    private static void createList(){
        list = new ArrayList<>();
        list.add(new AppAccount(EAccountType.BANK, "Investment"));
        list.add(new AppAccount(EAccountType.CARD, "Savings"));
    }
}
