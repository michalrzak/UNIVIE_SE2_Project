package at.ac.univie.se2.ws21.team0404.app.utils.iterator;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;

public class AccountIterator implements IIterator<AppAccount>{

    private final List<AppAccount> accounts;
    private int pos = 0;

    public AccountIterator(List<AppAccount> accounts){
        this.accounts = accounts;
    }

    @Override
    public boolean hasNext() {
        return pos < accounts.size();
    }

    @Override
    public AppAccount next() {
        return accounts.get(pos++);
    }

}
