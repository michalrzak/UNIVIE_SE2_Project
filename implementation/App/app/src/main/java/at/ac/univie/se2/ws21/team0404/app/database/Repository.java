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
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Singleton, used to access data from the database. Implements the strategy pattern and allows to
 * set different {@link IDatabase} strategies.
 * <p>
 * Executes all access to the database on a separate thread.
 */
public class Repository {

  /**
   * Need to execute the database queries on a separate thread
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

  /**
   * Construct the {@link Repository} with the given database strategy. Is private as this class is
   * a singleton and shuld be accessed accordingly using the {@link Repository#create(IDatabase)}
   * method
   *
   * @param databaseStrategy the database strategy to be used by the repository
   */
  private Repository(IDatabase databaseStrategy) {
    this.databaseStrategy = databaseStrategy;
  }

  /**
   * Creates the singleton if it does not exist, or throws an exception
   *
   * @param databaseStrategy the database strategy to be used by the repository
   * @throws SingletonAlreadyInstantiatedException if the singleton is already instantiated
   */
  public static void create(IDatabase databaseStrategy) {
    if (instance != null) {
      throw new SingletonAlreadyInstantiatedException("Repository");
    }
    instance = new Repository(databaseStrategy);
  }

  /**
   * Gets the singleton instance or throws an exception if no instance exists
   *
   * @return the singleton of {@link Repository}
   * @throws SingletonNotInstantiatedException if the singleton was not instantiated
   */
  public static Repository getInstance() {
    if (instance == null) {
      throw new SingletonNotInstantiatedException("Repository");
    }
    return instance;
  }

  /**
   * Sets the strategy to be used for database requests
   *
   * @param databaseStrategy the database strategy to be used by the repository
   */
  public void setDatabaseStrategy(IDatabase databaseStrategy) {
    this.databaseStrategy = databaseStrategy;
  }

  private IDatabase getDatabase() {
    return this.databaseStrategy;
  }

  /**
   * Retrieve the list of saved accounts in the database. The list is wrapped in a {@link
   * IChangingData} observer, to allow to more easily modify this list from other parts of the code
   * and to more easily let all classes have up to date lists of accounts
   *
   * @return the list of accounts saved in the database wrapped in {@link IChangingData}
   */
  public IChangingData<List<AppAccount>> getAccountList() {
    reloadAccounts();
    return accountList;
  }

  /**
   * Retrieve the list of saved categories in the database. The list is wrapped in a {@link
   * IChangingData} observer, to allow to more easily modify this list from other parts of the code
   * and to more easily let all classes have up to date lists of accounts
   *
   * @return the list of categories saved in the database wrapped in {@link IChangingData}
   */
  public IChangingData<List<Category>> getCategoryList() {
    reloadCategories();
    return categoryList;
  }

  /**
   * Retrieve the list of saved transactions in the database. The list is wrapped in a {@link
   * IChangingData} observer, to allow to more easily modify this list from other parts of the code
   * and to more easily let all classes have up to date lists of accounts
   *
   * @param account the account whose transactions should be retrieved
   * @return the list of transactions saved in the database wrapped in {@link IChangingData}
   */
  public IChangingData<List<Transaction>> getTransactionList(@NonNull AppAccount account) {
    reloadTransactions(account);
    return transactionList;
  }

  /**
   * Executes a statement on the database on another thread. Returns a {@link IChangingData} wrapped
   * {@link ERepositoryReturnStatus} object. The value of the wrapped object determines whether the
   * execution status of the database query.
   *
   * @param tryStatements a callable statement which will be tried to be executed on another thread
   * @return {@link IChangingData} wrapped {@link ERepositoryReturnStatus} object determining the
   * execution status of the {@literal tryStatements} provided
   */
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


  /**
   * Tries to create the passed account on the database.
   *
   * @param appAccount the account to be tried to be inserted on the database
   * @return a in {@link IChangingData} wrapped {@link ERepositoryReturnStatus}. May be observed to
   * handle the execution status of the query.
   */
  public IChangingData<ERepositoryReturnStatus> createAppAccount(@NonNull AppAccount appAccount) {

    return databaseAccessor(() -> {
      databaseStrategy.addAccount(appAccount);
      reloadAccounts();
      return null;
    });
  }

  /**
   * Tries to update the passed account on the database.
   *
   * @param newAccount the account to be tried to be inserted on the database
   * @return a in {@link IChangingData} wrapped {@link ERepositoryReturnStatus}. May be observed to
   * handle the execution status of the query.
   */
  public IChangingData<ERepositoryReturnStatus> updateAppAccount(@NonNull AppAccount newAccount) {

    return databaseAccessor(() -> {
      databaseStrategy.updateAccount(newAccount);
      reloadAccounts();
      return null;
    });
  }

  /**
   * Tries to delete the passed account from the database.
   *
   * @param appAccount the account to be tried to be deleted from the database
   * @return a in {@link IChangingData} wrapped {@link ERepositoryReturnStatus}. May be observed to
   * handle the execution status of the query.
   */
  public IChangingData<ERepositoryReturnStatus> deleteAppAccount(@NonNull AppAccount appAccount) {

    return databaseAccessor(() -> {
      databaseStrategy.deleteAccount(appAccount);
      reloadAccounts();
      return null;
    });
  }

  /**
   * Tries to delete the passed transaction from the database.
   *
   * @param owner         the owner of the to be deleted transaction
   * @param idToBeDeleted the id of the account to be deleted
   * @return a in {@link IChangingData} wrapped {@link ERepositoryReturnStatus}. May be observed to
   * handle the execution status of the query.
   */
  public IChangingData<ERepositoryReturnStatus> deleteTransaction(@NonNull AppAccount owner,
      UUID idToBeDeleted) {

    return databaseAccessor(() -> {
      databaseStrategy.deleteTransaction(owner, idToBeDeleted);
      reloadTransactions(owner);
      return null;
    });
  }

  /**
   * Tries to create the passed category on the database.
   *
   * @param category the category to be tried to be inserted on the database
   * @return a in {@link IChangingData} wrapped {@link ERepositoryReturnStatus}. May be observed to
   * handle the execution status of the query.
   */
  public IChangingData<ERepositoryReturnStatus> createCategory(@NonNull Category category) {

    return databaseAccessor(() -> {
      databaseStrategy.addCategory(category);
      reloadCategories();
      return null;
    });
  }

  /**
   * Tries to update the passed category on the database.
   *
   * @param category the category to be tried to be updated on the database
   * @return a in {@link IChangingData} wrapped {@link ERepositoryReturnStatus}. May be observed to
   * handle the execution status of the query.
   */
  public IChangingData<ERepositoryReturnStatus> updateCategory(@NonNull Category category) {

    return databaseAccessor(() -> {
      databaseStrategy.updateCategory(category);
      reloadCategories();
      return null;
    });
  }

  /**
   * Tries to update the transaction with the passed id on the database.
   *
   * @param owner              the owner of the to be updated transaction
   * @param oldId              the id of the transaction to be updated
   * @param updatedTransaction the new transaction that shuld be inserted instead of the transaction
   *                           with {@literal oldId}
   * @return a in {@link IChangingData} wrapped {@link ERepositoryReturnStatus}. May be observed to
   * handle the execution status of the query.
   */
  public IChangingData<ERepositoryReturnStatus> updateTransaction(AppAccount owner, UUID oldId,
      Transaction updatedTransaction) {

    return databaseAccessor(() -> {
      databaseStrategy.updateTransaction(owner, oldId, updatedTransaction);
      reloadTransactions(owner);
      return null;
    });
  }

  /**
   * Tries to create a transaction on the database.
   *
   * @param owner the owner of the to be newly created transaction
   * @param transaction the transaction to be inserted to the database
   * @return a in {@link IChangingData} wrapped {@link ERepositoryReturnStatus}. May be observed to
   * handle the execution status of the query.
   */
  public IChangingData<ERepositoryReturnStatus> createTransaction(@NonNull AppAccount owner,
      @NonNull Transaction transaction) {

    return databaseAccessor(() -> {
      databaseStrategy.addTransaction(owner, transaction);
      reloadTransactions(owner);
      return null;
    });
  }

  private void calcAccountBalance(AppAccount owner) {
    long amount = transactionList.getData().stream().mapToLong(
        transaction -> (long) transaction.getAmount() * (transaction.getType().getSign())).sum();
    owner.setBalance(amount);
    updateAppAccount(owner);
  }

  private void reloadAccounts() {
    executor.execute(() -> accountList.setData(new ArrayList<>(databaseStrategy.getAccounts())));
  }

  private void reloadCategories() {
    executor.execute(() -> categoryList.setData(
        databaseStrategy.getCategories().stream().filter(category -> !category.isDisabled())
            .collect(Collectors.toList())));
  }

  private void reloadTransactions(@NonNull AppAccount account) {
    // right now this does not indicate that the reload can fail due to the account not being in the DB
    Callable<Void> task = () -> {
      transactionList.setData(new ArrayList<>(getDatabase().getTransactions(account)));
      transactionList.observe(data -> calcAccountBalance(account));
      return null;
    };

    executor.submit(task);
  }
}
