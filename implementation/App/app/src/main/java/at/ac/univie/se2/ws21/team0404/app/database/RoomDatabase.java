package at.ac.univie.se2.ws21.team0404.app.database;

import java.util.Collection;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.NotYetImplementedException;

public class RoomDatabase implements IDatabase {
    @NonNull
    @Override
    public Collection<AppAccount> getAccounts() {
        throw new NotYetImplementedException("RoomDatabase::getAccounts");
    }

    @NonNull
    @Override
    public Collection<Transaction> getTransactions(@NonNull AppAccount account) throws DataDoesNotExistException {
        throw new NotYetImplementedException("RoomDatabase::getTransactions");
    }

    @NonNull
    @Override
    public Collection<Category> getCategories() {
        throw new NotYetImplementedException("RoomDatabase::getCategories");
    }

    @Override
    public void addAccount(@NonNull AppAccount newAccount) throws DataExistsException {
        throw new NotYetImplementedException("RoomDatabase::addAccount");

    }

    @Override
    public void deleteAccount(AppAccount account) throws DataDoesNotExistException {
        throw new NotYetImplementedException("RoomDatabase::deleteAccount");

    }

    @Override
    public void updateAccount(@NonNull AppAccount newAccount) throws DataDoesNotExistException {
        throw new NotYetImplementedException("RoomDatabase::updateAccount");

    }

    @Override
    public void addTransaction(@NonNull AppAccount owner, @NonNull Transaction newTransaction) throws DataExistsException, DataDoesNotExistException {
        throw new NotYetImplementedException("RoomDatabase::addTransaction");

    }

    @Override
    public void addCategory(Category newCategory) throws DataExistsException {
        throw new NotYetImplementedException("RoomDatabase::addCategory");

    }

    @Override
    public void updateCategory(String categoryName, Category newCategory) throws DataDoesNotExistException {
        throw new NotYetImplementedException("RoomDatabase::updateCategory");

    }

    @Override
    public void updateTransaction(AppAccount owner, int oldId, Transaction updatedTransaction) throws DataDoesNotExistException {
        throw new NotYetImplementedException("RoomDatabase::updateTransaction");

    }

    @Override
    public void deleteTransaction(@NonNull AppAccount owner, int idToBeDeleted) throws DataDoesNotExistException {
        throw new NotYetImplementedException("RoomDatabase::deleteTransaction");
    }
}
