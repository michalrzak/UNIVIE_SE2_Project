package at.ac.univie.se2.ws21.team0404.app.utils.iterator;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;

public class AccountCollection implements ICollection<AppAccount> {

    private List<AppAccount> accounts = new ArrayList<>();

    public AccountCollection() {

    }

    public AccountCollection(List<AppAccount> accounts) {
        this.accounts = accounts;
    }

    public void addAccounts(List<AppAccount> accounts) {
        this.accounts.addAll(accounts);
    }

    @Override
    public IIterator<AppAccount> createIterator() {
        return new AccountIterator(accounts);
    }
}
