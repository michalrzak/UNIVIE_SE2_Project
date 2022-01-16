package at.ac.univie.se2.ws21.team0404.app.database.room;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomAppAccount;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomCategory;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransaction;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransactionWithCategory;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class AppRoomDatabaseTest {

  private AppRoomDatabase db;
  private AccountDao accountDao;
  private CategoryDao categoryDao;
  private TransactionDao transactionDao;

  private static class Fixtures {

    public static AppAccount testAccount1 = new AppAccount("test1", EAccountType.BANK, 10.0);
    public static AppAccount testAccount2 = new AppAccount("test1", EAccountType.BANK, 10.0);

    public static Category testCategory1 = new Category(ETransactionType.INCOME, "cat1");
    public static Transaction testTransaction1 = new Transaction(testCategory1,
        ETransactionType.INCOME, 100, "tr1");
  }

  @Before
  public void createDb() {
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, AppRoomDatabase.class).build();
    accountDao = db.accountDao();
    categoryDao = db.categoryDao();
    transactionDao = db.transactionDao();
  }

  @After
  public void closeDb() {
    db.close();
  }

  @Test
  public void emptyDb_addAccount_accountInList() {
    AppAccount account = Fixtures.testAccount1;
    RoomAppAccount roomAccount = new RoomAppAccount(account);
    accountDao.addAccount(roomAccount);

    List<RoomAppAccount> accountList = accountDao.getAccounts();
    assertThat(accountList, iterableWithSize(1));
    assertThat(accountList, containsInAnyOrder(account));
  }

  @Test
  public void oneAccountInDb_deleteAccount_accountListEmpty() {
    AppAccount account = Fixtures.testAccount1;
    RoomAppAccount roomAccount = new RoomAppAccount(account);
    accountDao.addAccount(roomAccount);

    accountDao.deleteAccount(roomAccount);

    List<RoomAppAccount> accountList = accountDao.getAccounts();
    assertThat(accountList, empty());
  }

  @Test
  public void oneAccountInDb_updateAccount_accountIsUpdated() {
    AppAccount account = Fixtures.testAccount1;
    RoomAppAccount roomAccount = new RoomAppAccount(account);
    accountDao.addAccount(roomAccount);

    AppAccount updatedAccount = new AppAccount("testChanged", EAccountType.CARD, account.getId(),
        account.getSpendingLimit(), account.getBalance());
    accountDao.updateAccount(new RoomAppAccount(updatedAccount));

    List<RoomAppAccount> accountList = accountDao.getAccounts();
    assertThat(accountList, iterableWithSize(1));
    assertThat(accountList, containsInAnyOrder(updatedAccount));
    assertThat(accountList, not(containsInAnyOrder(account)));
  }

  @Test
  public void emptyDb_addTransaction_transactionInDb() {
    Transaction transaction = new Transaction(null, ETransactionType.INCOME, 100, "tr1");
    AppAccount account = Fixtures.testAccount1;
    RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
    transactionDao.addTransaction(roomTransaction);

    List<RoomTransactionWithCategory> transactionList = transactionDao
        .getTransactions(account.getId());
    assertThat(transactionList, iterableWithSize(1));
    RoomTransactionWithCategory result = transactionList.get(0);
    Transaction resultTransaction = result.getTransaction();
    assertEquals(transaction, resultTransaction);
  }

  @Test
  public void emptyDb_addTransaction_transactionInDbCategoryMissing() {
    Transaction transaction = Fixtures.testTransaction1;
    AppAccount account = Fixtures.testAccount1;
    RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
    transactionDao.addTransaction(roomTransaction);

    List<RoomTransactionWithCategory> transactionList = transactionDao
        .getTransactions(account.getId());
    assertThat(transactionList, iterableWithSize(1));
    RoomTransactionWithCategory result = transactionList.get(0);
    Transaction resultTransaction = result.getTransaction();
    assertEquals(transaction, resultTransaction);
    assertFalse(resultTransaction.getCategory().isPresent());
  }

  @Test
  public void categoryInDb_addTransaction_transactionInDbCategoryMissing() {
    Category category = Fixtures.testCategory1;
    categoryDao.addCategory(new RoomCategory(category));

    Transaction transaction = Fixtures.testTransaction1;
    AppAccount account = Fixtures.testAccount1;
    RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
    transactionDao.addTransaction(roomTransaction);

    List<RoomTransactionWithCategory> transactionList = transactionDao
        .getTransactions(account.getId());
    assertThat(transactionList, iterableWithSize(1));
    RoomTransactionWithCategory result = transactionList.get(0);
    Transaction resultTransaction = result.getTransaction();
    assertEquals(transaction, resultTransaction);
    assertEquals(category, resultTransaction.getCategory().orElse(null));
    assertEquals(category.getName(),
        resultTransaction.getCategory().map(Category::getName).orElse(null));
  }

  @Test
  public void transactionInDb_getOtherAccountTransactions_transactionListEmpty() {
    Transaction transaction = new Transaction(null, ETransactionType.INCOME, 100, "tr1");
    AppAccount account = Fixtures.testAccount1;
    RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
    transactionDao.addTransaction(roomTransaction);

    AppAccount otherAccount = Fixtures.testAccount2;
    List<RoomTransactionWithCategory> transactionList = transactionDao
        .getTransactions(otherAccount.getId());
    assertThat(transactionList, empty());
  }

  @Test
  public void categoryAndTransactionInDb_updateCategoryType_transactionCategoryUpdated() {
    Category category = Fixtures.testCategory1;
    categoryDao.addCategory(new RoomCategory(category));
    Transaction transaction = Fixtures.testTransaction1;
    AppAccount account = Fixtures.testAccount1;
    RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
    transactionDao.addTransaction(roomTransaction);

    Category updatedCategory = new Category(category.getId(), ETransactionType.EXPENSE, "cat1");
    categoryDao.updateCategory(new RoomCategory(updatedCategory));

    List<RoomTransactionWithCategory> transactionList = transactionDao
        .getTransactions(account.getId());
    assertThat(transactionList, iterableWithSize(1));
    RoomTransactionWithCategory result = transactionList.get(0);
    Transaction resultTransaction = result.getTransaction();
    assertEquals(transaction, resultTransaction);
    assertEquals(updatedCategory, resultTransaction.getCategory().orElse(null));
    assertEquals(updatedCategory.getName(),
        resultTransaction.getCategory().map(Category::getName).orElse(null));
    assertEquals(updatedCategory.getType(),
        resultTransaction.getCategory().map(Category::getType).orElse(null));
  }

  @Test
  public void categoryAndTransactionInDb_updateCategoryName_transactionCategoryUpdated() {
    Category category = Fixtures.testCategory1;
    categoryDao.addCategory(new RoomCategory(category));
    Transaction transaction = Fixtures.testTransaction1;
    AppAccount account = Fixtures.testAccount1;
    RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
    transactionDao.addTransaction(roomTransaction);

    Category updatedCategory = new Category(category.getId(), ETransactionType.INCOME, "catUpdated");
    categoryDao.updateCategory(new RoomCategory(updatedCategory));

    List<RoomTransactionWithCategory> transactionList = transactionDao
        .getTransactions(account.getId());
    assertThat(transactionList, iterableWithSize(1));
    RoomTransactionWithCategory result = transactionList.get(0);
    Transaction resultTransaction = result.getTransaction();
    assertEquals(transaction, resultTransaction);
    assertEquals(updatedCategory, resultTransaction.getCategory().orElse(null));
    assertEquals(updatedCategory.getName(),
        resultTransaction.getCategory().map(Category::getName).orElse(null));
    assertEquals(updatedCategory.getType(),
        resultTransaction.getCategory().map(Category::getType).orElse(null));
  }
}
