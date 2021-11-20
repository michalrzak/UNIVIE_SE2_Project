package at.ac.univie.se2.ws21.team0404.app.database;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class MemoryDatabase implements IDatabase{

    private final Set<AppAccount> accounts = new HashSet<>();
    private final Map<String, Category> categories = new HashMap<>();
    private final Map<AppAccount, Set<Transaction>> transactions = new HashMap<>();

    @NonNull
    @Override
    public Collection<AppAccount> getAccounts(){
        return accounts;
    }

    @NonNull
    @Override
    public Map<String, Category> getCategories() {
        return categories;
    }

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
    public void addCategory(Category newCategory) throws DataExistsException {
        if(categories.containsKey(newCategory.getName())) {
            throw new DataExistsException("categories");
        }
        categories.put(newCategory.getName(), newCategory);
    }

    // TODO: finnish this method
    public void addTransaction(@NonNull AppAccount owner, @NonNull Transaction newTransaction)
            throws DataExistsException, DataDoesNotExistException {
        if (!transactions.containsKey(owner)) {
            throw new DataDoesNotExistException("accounts");
        }
        if (!transactions.get(owner).add(newTransaction)){
            throw new DataExistsException("transactions");
        }
    }


    @Override
    public void updateCategory(String categoryName, Category newCategory) throws DataDoesNotExistException {
        if (!categories.containsKey(categoryName)) {
            throw new DataDoesNotExistException("categories");
        }

        categories.replace(categoryName, newCategory);
    }
}
