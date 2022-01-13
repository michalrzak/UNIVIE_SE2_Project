package at.ac.univie.se2.ws21.team0404.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import androidx.room.Room;
import at.ac.univie.se2.ws21.team0404.app.database.room.AccountDao;
import at.ac.univie.se2.ws21.team0404.app.database.room.AppRoomDatabase;
import at.ac.univie.se2.ws21.team0404.app.database.room.CategoryDao;
import at.ac.univie.se2.ws21.team0404.app.database.room.TransactionDao;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomAppAccount;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomCategory;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransaction;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransactionWithCategory;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import java.util.Collection;
import java.util.stream.Collectors;


/**
 * This class uses the adapter pattern to adapt the DAO objects to the IDatabase format
 */
public class RoomDatabase implements IDatabase {

  private static final String LOG_TAG = "RoomDatabase";

  private final AppRoomDatabase roomDatabase;
  private final AccountDao accountDao;
  private final CategoryDao categoryDao;
  private final TransactionDao transactionDao;

  public RoomDatabase(Context context) {
    roomDatabase = Room.databaseBuilder(context, AppRoomDatabase.class, "room-db").build();
    accountDao = roomDatabase.accountDao();
    categoryDao = roomDatabase.categoryDao();
    transactionDao = roomDatabase.transactionDao();
  }

  @NonNull
  @Override
  public Collection<AppAccount> getAccounts() {
    return accountDao.getAccounts().stream().map(roomAccount -> (AppAccount) roomAccount).collect(
        Collectors.toList());
  }

  @NonNull
  @Override
  public Collection<Transaction> getTransactions(@NonNull AppAccount account)
      throws DataDoesNotExistException {
    try {
      return transactionDao.getTransactions(account.getId()).stream()
          .map(RoomTransactionWithCategory::getTransaction).collect(Collectors.toList());
    } catch (SQLiteException e) {
      Log.w(LOG_TAG, "Failed to get transactions. With owner " + account.toString());
      throw new DataDoesNotExistException("Transaction");
    }
  }

  @NonNull
  @Override
  public Collection<Category> getCategories() {
    return categoryDao.getCategories().stream().map(roomCategory -> (Category) roomCategory)
        .collect(Collectors.toList());
  }

  @Override
  public void addAccount(@NonNull AppAccount newAccount) throws DataExistsException {
    try {
      accountDao.addAccount(new RoomAppAccount(newAccount));
    } catch (SQLiteException e) {
      Log.w(LOG_TAG, "Add account threw an SQL exception; Tried to add " + newAccount.toString());
      throw new DataExistsException("Account");
    }
  }

  @Override
  public void deleteAccount(AppAccount account) throws DataDoesNotExistException {
    try {
      accountDao.deleteAccount(new RoomAppAccount(account));
    } catch (SQLiteException e) {
      Log.w(LOG_TAG,
          "Delete account threw an SQL exception; Tried to delete " + account.toString());
      throw new DataDoesNotExistException("Account");
    }
  }

  @Override
  public void updateAccount(@NonNull AppAccount newAccount) throws DataDoesNotExistException {
    try {
      accountDao.updateAccount(new RoomAppAccount(newAccount));
    } catch (SQLiteException e) {
      Log.w(LOG_TAG,
          "Update account threw an SQL exception; Tried to update " + newAccount.toString());
      throw new DataDoesNotExistException("Account");
    }
  }

  @Override
  public void addTransaction(@NonNull AppAccount owner, @NonNull Transaction newTransaction)
      throws DataExistsException, DataDoesNotExistException {
    try {
      transactionDao.addTransaction(new RoomTransaction(newTransaction, owner));
    } catch (SQLiteException e) {
      Log.w(LOG_TAG,
          "Add transaction threw an SQL exception; Tried to add " + newTransaction.toString()
              + ". With owner " + owner.toString());
      // I don't actually know which of the two exception needs to be thrown here
      // TODO: Rework the exception structure to better reflect the current state of the application
      throw new DataExistsException("Transaction");
    }
  }

  @Override
  public void addCategory(Category newCategory) throws DataExistsException {
    try {
      categoryDao.addCategory(new RoomCategory(newCategory));
    } catch (SQLiteException e) {
      Log.w(LOG_TAG,
          "Add category threw an SQL exception; Tried to add " + newCategory.toString());
      throw new DataExistsException("Category");
    }
  }

  @Override
  public void updateCategory(String categoryName, Category newCategory)
      throws DataDoesNotExistException {
    try {
      // TODO: This is most likely wrong
      categoryDao.updateCategory(new RoomCategory(newCategory));
    } catch (SQLiteException e) {
      Log.w(LOG_TAG,
          "Update category threw an SQL exception; Tried to update " + newCategory.toString());
      throw new DataDoesNotExistException("Category");
    }
  }

  @Override
  public void updateTransaction(AppAccount owner, int oldId, Transaction updatedTransaction)
      throws DataDoesNotExistException {
    try {
      RoomTransaction roomTransaction = new RoomTransaction(oldId,
          updatedTransaction.getCategory().map(Category::getName).orElse(null),
          updatedTransaction.getType(), updatedTransaction.getAmount(),
          updatedTransaction.getName(), owner.getId());
      transactionDao.updateTransaction(roomTransaction);
    } catch (SQLiteException e) {
      Log.w(LOG_TAG,
          "Update transaction threw an SQL exception; Tried to update " + updatedTransaction
              .toString() + ". With owner " + owner.toString() + ". With oldID " + oldId);
      throw new DataDoesNotExistException("Transaction");
    }
  }

  @Override
  public void deleteTransaction(@NonNull AppAccount owner, int idToBeDeleted)
      throws DataDoesNotExistException {
    try {
      transactionDao.deleteTransaction(
          new RoomTransaction(idToBeDeleted, "", ETransactionType.EXPENSE, 1, "", owner.getId()));
    } catch (SQLiteException e) {
      Log.w(LOG_TAG, "Delete transaction threw an SQL exception; Tried to delete " + idToBeDeleted
          + ". With owner " + owner.toString());
      throw new DataDoesNotExistException("Transaction");
    }
  }
}
