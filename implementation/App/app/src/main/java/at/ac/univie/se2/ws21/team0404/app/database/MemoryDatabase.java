package at.ac.univie.se2.ws21.team0404.app.database;

// TODO: do we even remove log from here

import android.util.Log;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class MemoryDatabase implements IDatabase {

  private final Map<Integer, AppAccount> accounts = new HashMap<>();
  private final Map<String, Category> categories = new HashMap<>();
  private final Map<Integer, Set<Transaction>> transactions = new HashMap<>();

  /**
   * This function validates the account. It introduces a side affect as when an account is not yet
   * added in the transactions hash map it will add it.
   * <p>
   * TODO: Rework this. The entire account add/delete concept of the db should be rethought
   *
   * @param account AppAccount which is getting validated
   * @throws DataDoesNotExistException when account does not exist in database
   */
  private void validateAccount(AppAccount account) throws DataDoesNotExistException {
    if (!accounts.containsKey(account.getId())) {
      throw new DataDoesNotExistException("accounts");
    }

    if (!transactions.containsKey(account.getId())) {
      transactions.put(account.getId(), new HashSet<>());
    }
  }


  @NonNull
  @Override
  public Collection<AppAccount> getAccounts() {
    return accounts.values();
  }

  @NonNull
  @Override
  public Collection<Category> getCategories() {
    return categories.values();
  }

  @NonNull
  @Override
  public Collection<Transaction> getTransactions(@NonNull AppAccount account)
      throws DataDoesNotExistException {
    validateAccount(account);

    Set<Transaction> ret = transactions.get(account.getId());
    assert (ret != null);
    return ret;
  }

  @Override
  public void addAccount(@NonNull AppAccount newAccount) throws DataExistsException {
    if (newAccount.getName().isEmpty()) {
      throw new IllegalArgumentException("Account has to have a name");
    }
    if (accounts.containsValue(newAccount)) {
      throw new DataExistsException("accounts");
    }
    accounts.put(newAccount.getId(), newAccount);
  }

  @Override
  public void deleteAccount(AppAccount newAccount) throws DataDoesNotExistException {
    if (!accounts.containsKey(newAccount.getId())) {
      throw new DataDoesNotExistException("account");
    }
    accounts.remove(newAccount.getId());
  }

  @Override
  public void updateAccount(@NonNull AppAccount oldAccount, @NonNull AppAccount newAccount)
      throws DataDoesNotExistException {
    if (!accounts.containsValue(oldAccount)) {
      throw new DataDoesNotExistException("account");
    }
    accounts.replace(newAccount.getId(), newAccount);
  }

  @Override
  public void addCategory(@NonNull Category newCategory) throws DataExistsException {
    if (categories.containsKey(newCategory.getName())) {
      throw new DataExistsException("categories");
    }
    categories.put(newCategory.getName(), newCategory);
  }

  @Override
  public void addTransaction(@NonNull AppAccount owner, @NonNull Transaction newTransaction)
      throws DataExistsException, DataDoesNotExistException {

    validateAccount(owner);

    if (!transactions.get(owner.getId()).add(newTransaction)) {
      throw new DataExistsException("transactions");
    }
  }


  @Override
  public void updateCategory(@NonNull String categoryName, @NonNull Category newCategory)
      throws DataDoesNotExistException {
    if (!categories.containsKey(categoryName)) {
      throw new DataDoesNotExistException("categories");
    }

    categories.replace(categoryName, newCategory);
  }

  @Override
  public void updateTransaction(AppAccount owner, int oldId, Transaction updatedTransaction)
      throws DataDoesNotExistException {

    Log.d("MemDB_updateTx", "started update of transaction with id: " + oldId);
    validateAccount(owner);
    Log.d("MemDB_updateTx", "account valid");

    Set<Transaction> transactionList = transactions.get(owner.getId());
    assert (transactionList != null);

    Optional<Transaction> oldTransaction = transactionList.stream()
        .filter(transactionItem -> transactionItem.getId() == oldId).findFirst();

    if (!oldTransaction.isPresent()) {
      throw new DataDoesNotExistException("transaction");
    }

    transactionList.remove(oldTransaction.get());
    transactionList.add(updatedTransaction);
    Log.d("MemDB_updateTx", "successfully updated transaction in database");

  }

  @Override
  public void deleteTransaction(@NonNull AppAccount owner, int idToBeDeleted)
      throws DataDoesNotExistException {
    validateAccount(owner);

    Collection<Transaction> savedTransactions = transactions.get(owner.getId());
    assert (savedTransactions != null);

    Optional<Transaction> toBeDeleted = savedTransactions.stream()
        .filter(transaction -> transaction.getId() == idToBeDeleted).findFirst();

    if (!toBeDeleted.isPresent()) {
      throw new DataDoesNotExistException("transaction");
    }

    savedTransactions.remove(toBeDeleted.get());
  }
}
