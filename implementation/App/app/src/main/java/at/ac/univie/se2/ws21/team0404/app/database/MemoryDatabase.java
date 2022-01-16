package at.ac.univie.se2.ws21.team0404.app.database;

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
import java.util.UUID;

public class MemoryDatabase implements IDatabase {

  private final Map<UUID, AppAccount> accounts = new HashMap<>();
  private final Map<UUID, Category> categories = new HashMap<>();
  private final Map<UUID, Set<Transaction>> transactions = new HashMap<>();

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
  public void deleteAccount(AppAccount account) throws DataDoesNotExistException {
    if (!accounts.containsKey(account.getId())) {
      throw new DataDoesNotExistException("account");
    }
    accounts.remove(account.getId());
  }

  @Override
  public void updateAccount(@NonNull AppAccount newAccount)
      throws DataDoesNotExistException {
    if (!accounts.containsKey(newAccount.getId())) {
      throw new DataDoesNotExistException("account");
    }
    accounts.replace(newAccount.getId(), newAccount);
  }

  @Override
  public void addCategory(@NonNull Category newCategory) throws DataExistsException {
    Category existingCategory = categories.get(newCategory.getId());
    if (existingCategory != null && !existingCategory.isDisabled()) {
      throw new DataExistsException("categories");
    }
    categories.put(newCategory.getId(), newCategory);
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
  public void updateCategory(@NonNull Category newCategory)
          throws DataDoesNotExistException, DataExistsException {
    if (!categories.containsKey(newCategory.getId())) {
      throw new DataDoesNotExistException("categories");
    }

    categories.remove(newCategory.getId());
    categories.put(newCategory.getId(), newCategory);
  }

  @Override
  public void updateTransaction(AppAccount owner, UUID oldId, Transaction updatedTransaction)
      throws DataDoesNotExistException {

    validateAccount(owner);

    Set<Transaction> transactionList = transactions.get(owner.getId());
    assert (transactionList != null);

    Optional<Transaction> oldTransaction = transactionList.stream()
        .filter(transactionItem -> transactionItem.getId().equals(oldId)).findFirst();

    if (!oldTransaction.isPresent()) {
      throw new DataDoesNotExistException("transaction");
    }

    transactionList.remove(oldTransaction.get());
    transactionList.add(updatedTransaction);

  }

  @Override
  public void deleteTransaction(@NonNull AppAccount owner, UUID idToBeDeleted)
      throws DataDoesNotExistException {
    validateAccount(owner);

    Collection<Transaction> savedTransactions = transactions.get(owner.getId());
    assert (savedTransactions != null);

    Optional<Transaction> toBeDeleted = savedTransactions.stream()
        .filter(transaction -> transaction.getId().equals(idToBeDeleted)).findFirst();

    if (!toBeDeleted.isPresent()) {
      throw new DataDoesNotExistException("transaction");
    }

    savedTransactions.remove(toBeDeleted.get());
  }
}
