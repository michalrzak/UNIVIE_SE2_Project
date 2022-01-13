package at.ac.univie.se2.ws21.team0404.app.database;

import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.IChangingData;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.SingletonAlreadyInstantiatedException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.SingletonNotInstantiatedException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Repository {

  /**
   * Need to execute the database queries on a seperate thread
   */
  private static final int NUMBER_OF_THREADS = 4;
  private final ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


  private static Repository instance;
  private final IChangingData<List<AppAccount>> accountList =
      new ChangingData<>(new ArrayList<>());
  private final IChangingData<List<Transaction>> transactionList =
      new ChangingData<>(new ArrayList<>());
  private final IChangingData<List<Category>> categoryList =
      new ChangingData<>(new ArrayList<>());
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

  public void setDatabaseStrategy(IDatabase databaseStrategy) {
    this.databaseStrategy = databaseStrategy;
  }

  private IDatabase getDatabase() {
    return this.databaseStrategy;
  }

  public IChangingData<List<AppAccount>> getAccountList() {
    reloadAccounts();
    return accountList;
  }

  public IChangingData<List<Category>> getCategoryList() {
    reloadCategories();
    return categoryList;
  }

  public IChangingData<List<Transaction>> getTransactionList(@NonNull AppAccount account) {
    reloadTransactions(account);
    return transactionList;
  }

  private IChangingData<ERepositoryReturnStatus> databaseAccessor(Callable<Void> tryStatements) {

    IChangingData<ERepositoryReturnStatus> output = new ChangingData<>(
        ERepositoryReturnStatus.UPDATING);

    Callable<Void> task = () -> {
      try {
        tryStatements.call();
        output.setData(ERepositoryReturnStatus.SUCCESS);
      } catch (Exception e) {
        output.setData(ERepositoryReturnStatus.ERROR);
      }
      return null;
    };

    executor.submit(task);
    return output;
  }


  public IChangingData<ERepositoryReturnStatus> createAppAccount(@NonNull AppAccount appAccount) {

    return databaseAccessor(() -> {
      databaseStrategy.addAccount(appAccount);
      reloadAccounts();
      return null;
    });
  }

  public IChangingData<ERepositoryReturnStatus> updateAppAccount(@NonNull AppAccount newAccount) {

    return databaseAccessor(() -> {
      databaseStrategy.updateAccount(newAccount);
      reloadAccounts();
      return null;
    });
  }

  public IChangingData<ERepositoryReturnStatus> deleteAppAccount(@NonNull AppAccount appAccount) {

    return databaseAccessor(() -> {
      databaseStrategy.deleteAccount(appAccount);
      reloadAccounts();
      return null;
    });
  }

  public IChangingData<ERepositoryReturnStatus> deleteTransaction(@NonNull AppAccount owner,
      int idToBeDeleted) {

    return databaseAccessor(() -> {
      databaseStrategy.deleteTransaction(owner, idToBeDeleted);
      reloadTransactions(owner);
      return null;
    });
  }

  public IChangingData<ERepositoryReturnStatus> createCategory(@NonNull Category category) {

    return databaseAccessor(() -> {
      databaseStrategy.addCategory(category);
      reloadCategories();
      return null;
    });
  }

  public IChangingData<ERepositoryReturnStatus> updateCategory(@NonNull String categoryName,
      @NonNull Category category) {

    return databaseAccessor(() -> {
      databaseStrategy.updateCategory(categoryName, category);
      reloadCategories();
      return null;
    });
  }


  public IChangingData<ERepositoryReturnStatus> updateTransaction(AppAccount owner, int oldId,
      Transaction updatedTransaction) {

    return databaseAccessor(() -> {
      databaseStrategy.updateTransaction(owner, oldId, updatedTransaction);
      reloadTransactions(owner);
      return null;
    });
  }

  public IChangingData<ERepositoryReturnStatus> createTransaction(@NonNull AppAccount owner,
      @NonNull Transaction transaction) {

    return databaseAccessor(() -> {
      databaseStrategy.addTransaction(owner, transaction);
      reloadTransactions(owner);
      return null;
    });
  }

  private void reloadAccounts() {
    executor.execute(() -> accountList.setData(new ArrayList<>(databaseStrategy.getAccounts())));
  }

  private void reloadCategories() {
    executor.execute(() -> categoryList.setData(new ArrayList<>(
        databaseStrategy.getCategories().stream().filter(category -> !category.isDisabled())
            .collect(Collectors.toList()))));
  }

  private void reloadTransactions(@NonNull AppAccount account) {
    // right now this does not indicate that the reload can fail due to the account not being in the DB
    Callable<Void> task = () -> {
      transactionList.setData(new ArrayList<>(getDatabase().getTransactions(account)));
      return null;
    };

    executor.submit(task);
  }
}
