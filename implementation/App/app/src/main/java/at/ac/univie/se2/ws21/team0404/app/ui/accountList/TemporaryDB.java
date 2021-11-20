package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;

public class TemporaryDB {

    private static List<AppAccount> list;
    private static List<Transaction> tlist;


    public static List<AppAccount> getList() {
        if (list == null)
            createList();
        return list;
    }

    public static List<Transaction> getTList(AppAccount owner) {
        if (tlist == null) {
            createTList();
        }
        return tlist;
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

    private static void createTList() {
        tlist = new ArrayList<>();
        tlist.add(new Transaction(0, new Category(), ETransactionType.EXPENSE, 100));
        tlist.add(new Transaction(1, new Category(), ETransactionType.INCOME, 200));
        tlist.add(new Transaction(2, new Category(), ETransactionType.EXPENSE, 50));
    }
}
