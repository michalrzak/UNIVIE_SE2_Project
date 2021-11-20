package at.ac.univie.se2.ws21.team0404.app.database;


import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

public class MemoryDatabase implements IDatabase{

    private final Set<AppAccount> accounts = new HashSet<>();
    private final Map<String, Category> categories = new HashMap<>();

    @Override
    public Collection<AppAccount> getAccounts(){
        return accounts;
    }

    @Override
    public Map<String, Category> getCategories() {
        return categories;
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

    @Override
    public void updateCategory(String categoryName, Category newCategory) throws DataDoesNotExistException {
        if (!categories.containsKey(categoryName)) {
            throw new DataDoesNotExistException("categories");
        }

        categories.replace(categoryName, newCategory);
    }
}
