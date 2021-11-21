package at.ac.univie.se2.ws21.team0404.app.database;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingDataImpl;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.SingletonAlreadyInstantiatedException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.SingletonNotInstantiatedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {

  private static Repository instance;
  private final ChangingData<List<AppAccount>> accountList =
      new ChangingDataImpl<>(new ArrayList<>());
  private final ChangingData<List<Transaction>> transactionList =
      new ChangingDataImpl<>(new ArrayList<>());
  private final ChangingData<List<Category>> categoryList =
      new ChangingDataImpl<>(new ArrayList<>());
  private IDatabase databaseStrategy;

  private Repository(IDatabase databaseStrategy) {
    this.databaseStrategy = databaseStrategy;
  }

  public static void create(IDatabase databaseStrategy) {
    if (instance != null) {
      throw new SingletonAlreadyInstantiatedException("Repository");
    }
    instance = new Repository(databaseStrategy);
  }

  public static Repository getInstance() {
    if (instance == null) {
      throw new SingletonNotInstantiatedException("Repository");
    }
    return instance;
  }

  public void setDatabaseStraregy(IDatabase databaseStrategy) {
    this.databaseStrategy = databaseStrategy;
  }

  private IDatabase getDatabase() {
    return this.databaseStrategy;
  }

  public ChangingData<List<AppAccount>> getAccountList() {
    reloadAccounts();
    return accountList;
  }

  public ChangingData<List<Category>> getCategoryList() {
    reloadCategories();
    return categoryList;
  }

  public ChangingData<List<Transaction>> getTransactionList(@NonNull AppAccount account)
      throws DataDoesNotExistException {
    reloadTransactions(account);
    return transactionList;
  }

  public void createAppAccount(@NonNull AppAccount appAccount) throws DataExistsException {
    databaseStrategy.addAccount(appAccount);
    reloadAccounts();
  }

  public void deleteAppAccount(@NonNull AppAccount appAccount) throws DataDoesNotExistException {
    databaseStrategy.deleteAccount(appAccount);
    reloadAccounts();
  }

  public void createCategory(@NonNull Category category) throws DataExistsException {
    databaseStrategy.addCategory(category);
    reloadCategories();
  }

  public void updateCategory(@NonNull String categoryName, @NonNull Category category)
      throws DataDoesNotExistException {
    databaseStrategy.updateCategory(categoryName, category);
    reloadCategories();
  }

  public void updateTransaction(AppAccount owner, int oldId,
      Transaction updatedTransaction) throws DataDoesNotExistException {
    databaseStrategy.updateTransaction(owner, oldId, updatedTransaction);
    reloadTransactions(owner);
  }

  public void createTransaction(@NonNull AppAccount owner, @NonNull Transaction transaction)
      throws DataExistsException, DataDoesNotExistException {
    getDatabase().addTransaction(owner, transaction);
    reloadTransactions(owner);
  }

  private void reloadAccounts() {
    accountList.setData(new ArrayList<>(databaseStrategy.getAccounts()));
  }

  private void reloadCategories() {
    categoryList.setData(new ArrayList<>(
        databaseStrategy.getCategories().stream()
            .filter(category -> !category.isDisabled())
            .collect(Collectors.toList())
    ));
  }

  private void reloadTransactions(@NonNull AppAccount account) throws DataDoesNotExistException {
    transactionList.setData(new ArrayList<>(getDatabase().getTransactions(account)));
  }
}
