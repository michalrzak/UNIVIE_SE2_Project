package at.ac.univie.se2.ws21.team0404.app.database;

import java.util.Collection;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

/**
 * Interface, used for our databse implementation. Allows us to have a test database
 * that does not rely on Room
 */
public interface IDatabase {
    Collection<AppAccount> getAccounts();
    void addAccount(AppAccount newAccount) throws DataExistsException;
    // should not be needed
    // public void updateAccount(AppAccount oldAccount, AppAccount newAccount) throws DataDoesNotExistException;
}
