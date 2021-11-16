package at.ac.univie.se2.ws21.team0404.app.database;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class MemoryDatabase implements IDatabase{

    private Set<AppAccount> accounts = new HashSet<>();

    @Override
    public Collection<AppAccount> getAccounts(){
        return accounts;
    }

    @Override
    public void addAccount(@NonNull AppAccount newAccount) throws DataExistsException {
        if (!accounts.add(newAccount)) {
            throw new DataExistsException("accounts");
        }
    }


}
