package at.ac.univie.se2.ws21.team0404.app.database;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public class MemoryDatabase implements IDatabase{

    private final Set<AppAccount> accounts = new HashSet<>();
    private final Map<AppAccount, Set<Transaction>> transactions = new HashMap<>();

    @Override
    public Collection<AppAccount> getAccounts(){
        return accounts;
    }

    @Override
    public Collection<Transaction> getTransactions(@NonNull AppAccount account) throws DataDoesNotExistException {
        if (transactions.containsKey(account)) {
            return transactions.get(account);
        }

        throw new DataDoesNotExistException("transactions");
    }

    @Override
    public void addAccount(@NonNull AppAccount newAccount) throws DataExistsException {
        if (!accounts.add(newAccount)) {
            throw new DataExistsException("accounts");
        }
    }

    @Override
    public void addTransaction(@NonNull AppAccount owner, @NonNull Transaction newTransaction)
            throws DataExistsException, DataDoesNotExistException {
        if (!transactions.containsKey(owner)) {
            throw new DataDoesNotExistException("accounts");
        }
        if (!transactions.get(owner).add(newTransaction)){
            throw new DataExistsException("transactions");
        }
    }


}
