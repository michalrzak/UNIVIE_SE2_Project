package at.ac.univie.se2.ws21.team0404.app.database;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import java.util.Collection;
import java.util.UUID;

/**
 * Interface, used for our database implementation. Allows us to have a test database that does not
 * rely on Room
 */
public interface IDatabase {

  @NonNull
  Collection<AppAccount> getAccounts();

  @NonNull
  Collection<Transaction> getTransactions(@NonNull AppAccount account)
      throws DataDoesNotExistException;

  @NonNull
  Collection<Category> getCategories();

  /**
   * Adds the provided account to the database
   *
   * @param newAccount the account to be added to the database
   * @throws DataExistsException if an account with the key of {@param newAccount} already exists on
   *                             the database
   */
  void addAccount(@NonNull AppAccount newAccount) throws DataExistsException;

  /**
   * Deletes the provided account from the database
   *
   * @param account the account to be deleted from the database
   * @throws DataDoesNotExistException if an account with the key of {@param account} does not exist
   *                                   on the database
   */
  void deleteAccount(AppAccount account) throws DataDoesNotExistException;

  /**
   * Updates the given account on the database
   *
   * @param newAccount the account, whose id already exists on the database, which will be updated
   * @throws DataDoesNotExistException if an account with the id of {@literal newAccount} does not
   *                                   exist on the database
   */
  void updateAccount(@NonNull AppAccount newAccount) throws DataDoesNotExistException;

  /**
   * Adds the provided transaction to the database and assigns {@literal owner} as its owner/
   *
   * @param owner          the account to be assigned as owner of {@literal newTransaction}, cannot
   *                       be null
   * @param newTransaction the transaction to be added to the database, cannot be null
   * @throws DataExistsException       if a transaction with the id of {@literal newTransaction} and
   *                                   the owner {@literal owner} already exists on the database
   * @throws DataDoesNotExistException if {@literal owner} does not exist on the databse
   */
  void addTransaction(@NonNull AppAccount owner, @NonNull Transaction newTransaction)
      throws DataExistsException, DataDoesNotExistException;

  /**
   * Adds the provided category to the database
   *
   * @param newCategory the category to be inserted to the database, cannot be null
   * @throws DataExistsException if the provided category already exists on the database
   */
  void addCategory(@NonNull Category newCategory) throws DataExistsException;

  /**
   * Updates the given category on the database
   *
   * @param newCategory the category to be updated on the database, cannot be null
   * @throws DataDoesNotExistException if a category with the id of the passed category does not
   *                                   exist on the database
   * @throws DataExistsException       if a category with the name of the new category already
   *                                   exists on the database
   */
  void updateCategory(@NonNull Category newCategory)
      throws DataDoesNotExistException, DataExistsException;

  /**
   * Updates the transaction with the id in {@literal oldId} and owner {@literal owner} to the value
   * passed in {@literal newTransaction}
   *
   * @param owner              the owner of the transaction to be updated, cannot be null
   * @param oldId              the id of the transaction to be updated, cannot be null
   * @param updatedTransaction the transaction, which will be inserted inplace of the old
   *                           transaction
   * @throws DataDoesNotExistException if an account {@literal owner} or a transaction with
   *                                   {@literal owner} {@literal oldId} pair does not exist on the
   *                                   database
   */
  void updateTransaction(@NonNull AppAccount owner, @NonNull UUID oldId,
      @NonNull Transaction updatedTransaction) throws DataDoesNotExistException;

  /**
   * Deletes the transaction with the given id {@literal idToBeDeleted} and owner {@literal owner}
   * from the database
   *
   * @param owner         the owner of the transaction to be deleted, cannot be null
   * @param idToBeDeleted the id of the transaction to be deleted, cannot be null
   * @throws DataDoesNotExistException if the owner {@literal owner} or the {@literal idToBeDeleted}
   *                                   does not exist on the database
   */
  void deleteTransaction(@NonNull AppAccount owner, @NonNull UUID idToBeDeleted)
      throws DataDoesNotExistException;
}
