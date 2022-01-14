package at.ac.univie.se2.ws21.team0404.app.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.ac.univie.se2.ws21.team0404.app.database.room.AccountDao;
import at.ac.univie.se2.ws21.team0404.app.database.room.AppRoomDatabase;
import at.ac.univie.se2.ws21.team0404.app.database.room.CategoryDao;
import at.ac.univie.se2.ws21.team0404.app.database.room.TransactionDao;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomAppAccount;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomCategory;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransaction;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransactionWithCategory;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit tests for the {@link RoomDatabase} class. This needs to live in the android test package as
 * the class uses Logging, which is difficult to mock in the regular unit tests
 */
@RunWith(MockitoJUnitRunner.class)
public class RoomDatabaseTest {

  @Mock
  private AppRoomDatabase mockRoomDatabase;

  @Mock
  private AccountDao mockAccountDao;
  @Mock
  private CategoryDao mockCategoryDao;
  @Mock
  private TransactionDao mockTransactionDao;

  private RoomDatabase roomDatabase;

  @Before
  public void setUp() {
    when(mockRoomDatabase.accountDao()).thenReturn(mockAccountDao);
    when(mockRoomDatabase.categoryDao()).thenReturn(mockCategoryDao);
    when(mockRoomDatabase.transactionDao()).thenReturn(mockTransactionDao);

    roomDatabase = new RoomDatabase(() -> mockRoomDatabase);
  }

  /**
   * Tests the method {@link RoomDatabase#getAccounts()}
   */
  @Test
  public void RoomDatabase_getAccounts_returnsValidList() {
    List<RoomAppAccount> myAccountList = new ArrayList<>();
    for (int i = 0; i < 100; ++i) {
      myAccountList.add(Mockito.mock(RoomAppAccount.class));
    }

    when(mockAccountDao.getAccounts()).thenReturn(myAccountList);
    Collection<AppAccount> result = roomDatabase.getAccounts();
    assertEquals(myAccountList, result);
  }

  /**
   * Tests the method {@link RoomDatabase#getCategories()}
   */
  @Test
  public void RoomDatabase_getCategories_returnsValidList() {
    List<RoomCategory> myCategoryList = new ArrayList<>();
    for (int i = 0; i < 100; ++i) {
      myCategoryList.add(Mockito.mock(RoomCategory.class));
    }

    when(mockCategoryDao.getCategories()).thenReturn(myCategoryList);
    Collection<Category> result = roomDatabase.getCategories();
    assertEquals(myCategoryList, result);
  }

  /**
   * Tests the method {@link RoomDatabase#getTransactions(AppAccount)}
   */
  @Test
  public void RoomDatabase_getTransaction_returnsValidList() {
    List<RoomTransactionWithCategory> myTransactionList = new ArrayList<>();
    List<Transaction> myResultList = new ArrayList<>();
    for (int i = 0; i < 2; ++i) {
      RoomTransactionWithCategory mockedRoomTransaction = Mockito
          .mock(RoomTransactionWithCategory.class);
      myTransactionList.add(mockedRoomTransaction);
      Transaction mockedTransaction = Mockito.mock(Transaction.class);
      when(mockedRoomTransaction.getTransaction()).thenReturn(mockedTransaction);
      myResultList.add(mockedTransaction);
    }

    AppAccount mockAccount = Mockito.mock(AppAccount.class);
    int mockId = 1000;
    when(mockAccount.getId()).thenReturn(mockId);

    when(mockTransactionDao.getTransactions(mockId)).thenReturn(myTransactionList);
    Collection<Transaction> result;
    try {
      result = roomDatabase.getTransactions(mockAccount);
    } catch (DataDoesNotExistException e) {
      fail();
      return;
    }
    verify(mockTransactionDao, times(1)).getTransactions(mockId);
    assertEquals(myResultList, result);
  }

  /**
   * Tests the method {@link RoomDatabase#addAccount(AppAccount)}
   */
  @Test
  public void RoomDatabase_addAccount_accountDaoReceivesValidAccount() {
    AppAccount mockAccount = Mockito.mock(AppAccount.class);
    when(mockAccount.getId()).thenReturn(1);
    when(mockAccount.getName()).thenReturn("mock name");
    when(mockAccount.getType()).thenReturn(EAccountType.BANK);

    try {
      roomDatabase.deleteAccount(mockAccount);
    } catch (DataDoesNotExistException e) {
      fail();
      return;
    }

    verify(mockAccountDao, times(1)).deleteAccount(new RoomAppAccount(mockAccount));
  }

  /**
   * Tests the method {@link RoomDatabase#updateAccount(AppAccount)}
   */
  @Test
  public void RoomDatabase_deleteAccount_accountDaoReceivesCorrectAccount() {
    AppAccount mockAccount = Mockito.mock(AppAccount.class);
    when(mockAccount.getId()).thenReturn(1);
    when(mockAccount.getName()).thenReturn("mock name");
    when(mockAccount.getType()).thenReturn(EAccountType.BANK);

    try {
      roomDatabase.updateAccount(mockAccount);
    } catch (DataDoesNotExistException e) {
      fail();
      return;
    }

    verify(mockAccountDao, times(1)).updateAccount(new RoomAppAccount(mockAccount));
  }

  /**
   * Tests the method {@link RoomDatabase#addCategory(Category)}
   */
  @Test
  public void RoomDatabase_addCategory_categoryDaoReceivesValidCategory() {
    Category mockCategory = Mockito.mock(Category.class);
    when(mockCategory.getName()).thenReturn("mock name");
    when(mockCategory.getType()).thenReturn(ETransactionType.INCOME);
    when(mockCategory.isDisabled()).thenReturn(false);

    try {
      roomDatabase.addCategory(mockCategory);
    } catch (DataExistsException e) {
      fail();
      return;
    }

    verify(mockCategoryDao, times(1)).addCategory(new RoomCategory(mockCategory));
  }

  /**
   * Tests the method {@link RoomDatabase#updateCategory(String, Category)}}
   * <p>
   * Ignore as long as ID rework is not finished
   */
  @Ignore
  @Test
  public void RoomDatabase_updateCategory_categoryDaoReceivesValidCategory() {
    Category mockCategory = Mockito.mock(Category.class);
    when(mockCategory.getName()).thenReturn("mock name");
    when(mockCategory.getType()).thenReturn(ETransactionType.INCOME);
    when(mockCategory.isDisabled()).thenReturn(false);

    try {
      roomDatabase.updateCategory("mock name", mockCategory);
    } catch (DataDoesNotExistException e) {
      fail();
      return;
    }

    verify(mockCategoryDao, times(1)).updateCategory(new RoomCategory(mockCategory));
  }

  /**
   * Tests the method {@link RoomDatabase#addTransaction(AppAccount, Transaction)}
   */
  @Test
  public void RoomDatabase_addTransaction_transactionDaoReceivesValidTransaction() {
    AppAccount mockAccount = Mockito.mock(AppAccount.class);
    when(mockAccount.getId()).thenReturn(1);

    Transaction mockTransaction = Mockito.mock(Transaction.class);
    int mockId = 2;
    when(mockTransaction.getId()).thenReturn(mockId);
    when(mockTransaction.getCategory()).thenReturn(Optional.empty());
    when(mockTransaction.getType()).thenReturn(ETransactionType.INCOME);
    when(mockTransaction.getAmount()).thenReturn(100);
    when(mockTransaction.getName()).thenReturn("mock name");

    try {
      roomDatabase.addTransaction(mockAccount, mockTransaction);
    } catch (DataExistsException | DataDoesNotExistException e) {
      fail();
      return;
    }

    verify(mockTransactionDao, times(1))
        .addTransaction(new RoomTransaction(mockTransaction, mockAccount));
  }

  /**
   * Tests the method {@link RoomDatabase#updateTransaction(AppAccount, int, Transaction)}
   */
  @Test
  public void RoomDatabase_updateTransaction_transactionDaoReceivesValidTransaction() {
    AppAccount mockAccount = Mockito.mock(AppAccount.class);
    when(mockAccount.getId()).thenReturn(1);

    Transaction mockTransaction = Mockito.mock(Transaction.class);
    int mockId = 2;
    when(mockTransaction.getId()).thenReturn(mockId);
    when(mockTransaction.getCategory()).thenReturn(Optional.empty());
    when(mockTransaction.getType()).thenReturn(ETransactionType.INCOME);
    when(mockTransaction.getAmount()).thenReturn(100);
    when(mockTransaction.getName()).thenReturn("mock name");

    try {
      // if oldID = id of transaction we can just compare against the transaction itself
      roomDatabase.updateTransaction(mockAccount, mockId, mockTransaction);
    } catch (DataDoesNotExistException e) {
      fail();
      return;
    }

    verify(mockTransactionDao, times(1))
        .updateTransaction(new RoomTransaction(mockTransaction, mockAccount));
  }

  @Test
  public void RoomDatabase_deleteTransaction_transactionDaoReceivesValidTransaction() {
    AppAccount mockAccount = Mockito.mock(AppAccount.class);
    when(mockAccount.getId()).thenReturn(1);

    try {
      // if oldID = id of transaction we can just compare against the transaction itself
      roomDatabase.deleteTransaction(mockAccount, 2);
    } catch (DataDoesNotExistException e) {
      fail();
      return;
    }

    // with the way the RoomDatabase API is structured right now it is not possible to test it in a
    //  better way, without becoming too implementation specific
    verify(mockTransactionDao, times(1)).deleteTransaction(any(RoomTransaction.class));
  }
}

