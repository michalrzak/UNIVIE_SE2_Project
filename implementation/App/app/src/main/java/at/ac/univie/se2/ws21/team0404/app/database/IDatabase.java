package at.ac.univie.se2.ws21.team0404.app.database;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import java.util.Collection;
import java.util.Map;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

/**
 * Interface, used for our database implementation. Allows us to have a test database
 * that does not rely on Room
 */
public interface IDatabase {
    @NonNull Collection<AppAccount> getAccounts();
    @NonNull Collection<Transaction> getTransactions(@NonNull AppAccount account) throws DataDoesNotExistException;

    void addAccount(@NonNull AppAccount newAccount) throws DataExistsException;
    void addTransaction(@NonNull AppAccount owner, @NonNull Transaction newTransaction)
            throws DataExistsException, DataDoesNotExistException;
    // the transaction with the given ID can already exist or the owner of the transaction may
    // not exist; may get changed once actual DB implementation finalises and it becomes clear
    // how transaction ids will be handled.

    // should not be needed
    // public void updateAccount(AppAccount oldAccount, AppAccount newAccount) throws DataDoesNotExistException;
    Map<String, Category> getCategories();
    void addCategory(Category newCategory) throws DataExistsException;
    void updateCategory(String categoryName, Category newCategory) throws DataDoesNotExistException;
}
