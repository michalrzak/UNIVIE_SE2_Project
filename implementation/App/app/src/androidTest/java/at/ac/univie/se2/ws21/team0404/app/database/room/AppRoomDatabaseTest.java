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

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomAppAccount;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomCategory;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransaction;
import at.ac.univie.se2.ws21.team0404.app.database.room.model.RoomTransactionWithCategory;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;

public class AppRoomDatabaseTest {
    private AppRoomDatabase db;
    private AccountDao accountDao;
    private CategoryDao categoryDao;
    private TransactionDao transactionDao;

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
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomAppAccount roomAccount = new RoomAppAccount(account);
        accountDao.addAccount(roomAccount);

        List<RoomAppAccount> accountList = accountDao.getAccounts();
        assertThat(accountList, iterableWithSize(1));
        assertThat(accountList, containsInAnyOrder(account));
    }

    @Test
    public void oneAccountInDb_deleteAccount_accountListEmpty() {
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomAppAccount roomAccount = new RoomAppAccount(account);
        accountDao.addAccount(roomAccount);

        accountDao.deleteAccount(roomAccount);

        List<RoomAppAccount> accountList = accountDao.getAccounts();
        assertThat(accountList, empty());
    }

    @Test
    public void oneAccountInDb_updateAccount_accountIsUpdated() {
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomAppAccount roomAccount = new RoomAppAccount(account);
        accountDao.addAccount(roomAccount);

        AppAccount updatedAccount = new AppAccount("testChanged", EAccountType.CARD, account);
        accountDao.updateAccount(new RoomAppAccount(updatedAccount));

        List<RoomAppAccount> accountList = accountDao.getAccounts();
        assertThat(accountList, iterableWithSize(1));
        assertThat(accountList, containsInAnyOrder(updatedAccount));
        assertThat(accountList, not(containsInAnyOrder(account)));
    }

    @Test
    public void emptyDb_addTransaction_transactionInDb() {
        Transaction transaction = new Transaction(null, ETransactionType.INCOME, 100, "tr1");
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
        transactionDao.addTransaction(roomTransaction);

        List<RoomTransactionWithCategory> transactionList = transactionDao.getTransactions(account.getId());
        assertThat(transactionList, iterableWithSize(1));
        RoomTransactionWithCategory result = transactionList.get(0);
        Transaction resultTransaction = result.getTransaction();
        assertEquals(transaction, resultTransaction);
    }

    @Test
    public void emptyDb_addTransaction_transactionInDbCategoryMissing() {
        Category category = new Category(ETransactionType.INCOME, "cat1");
        Transaction transaction = new Transaction(category, ETransactionType.INCOME, 100, "tr1");
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
        transactionDao.addTransaction(roomTransaction);

        List<RoomTransactionWithCategory> transactionList = transactionDao.getTransactions(account.getId());
        assertThat(transactionList, iterableWithSize(1));
        RoomTransactionWithCategory result = transactionList.get(0);
        Transaction resultTransaction = result.getTransaction();
        assertEquals(transaction, resultTransaction);
        assertFalse(resultTransaction.getCategory().isPresent());
    }

    @Test
    public void categoryInDb_addTransaction_transactionInDbCategoryMissing() {
        Category category = new Category(ETransactionType.INCOME, "cat1");
        categoryDao.addCategory(new RoomCategory(category));

        Transaction transaction = new Transaction(category, ETransactionType.INCOME, 100, "tr1");
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
        transactionDao.addTransaction(roomTransaction);

        List<RoomTransactionWithCategory> transactionList = transactionDao.getTransactions(account.getId());
        assertThat(transactionList, iterableWithSize(1));
        RoomTransactionWithCategory result = transactionList.get(0);
        Transaction resultTransaction = result.getTransaction();
        assertEquals(transaction, resultTransaction);
        assertEquals(category, resultTransaction.getCategory().orElse(null));
        assertEquals(category.getName(), resultTransaction.getCategory().map(Category::getName).orElse(null));
    }

    @Test
    public void transactionInDb_getOtherAccountTransactions_transactionListEmpty() {
        Transaction transaction = new Transaction(null, ETransactionType.INCOME, 100, "tr1");
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
        transactionDao.addTransaction(roomTransaction);

        AppAccount otherAccount = new AppAccount("test2", EAccountType.BANK);
        List<RoomTransactionWithCategory> transactionList = transactionDao.getTransactions(otherAccount.getId());
        assertThat(transactionList, empty());
    }

    @Test
    public void categoryAndTransactionInDb_updateCategoryType_transactionCategoryUpdated() {
        Category category = new Category(ETransactionType.INCOME, "cat1");
        categoryDao.addCategory(new RoomCategory(category));
        Transaction transaction = new Transaction(category, ETransactionType.INCOME, 100, "tr1");
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
        transactionDao.addTransaction(roomTransaction);

        Category updatedCategory = new Category(ETransactionType.EXPENSE, "cat1");
        categoryDao.updateCategory(new RoomCategory(updatedCategory));

        List<RoomTransactionWithCategory> transactionList = transactionDao.getTransactions(account.getId());
        assertThat(transactionList, iterableWithSize(1));
        RoomTransactionWithCategory result = transactionList.get(0);
        Transaction resultTransaction = result.getTransaction();
        assertEquals(transaction, resultTransaction);
        assertEquals(updatedCategory, resultTransaction.getCategory().orElse(null));
        assertEquals(updatedCategory.getName(), resultTransaction.getCategory().map(Category::getName).orElse(null));
        assertEquals(updatedCategory.getType(), resultTransaction.getCategory().map(Category::getType).orElse(null));
    }

    //
    @Ignore("This test fails as long as Category uses name as key, needs a dedicated id as primary key")
    @Test
    public void categoryAndTransactionInDb_updateCategoryName_transactionCategoryUpdated() {
        Category category = new Category(ETransactionType.INCOME, "cat1");
        categoryDao.addCategory(new RoomCategory(category));
        Transaction transaction = new Transaction(category, ETransactionType.INCOME, 100, "tr1");
        AppAccount account = new AppAccount("test1", EAccountType.BANK);
        RoomTransaction roomTransaction = new RoomTransaction(transaction, account);
        transactionDao.addTransaction(roomTransaction);

        Category updatedCategory = new Category(ETransactionType.INCOME, "catUpdated");
        categoryDao.updateCategory(new RoomCategory(updatedCategory));

        List<RoomTransactionWithCategory> transactionList = transactionDao.getTransactions(account.getId());
        assertThat(transactionList, iterableWithSize(1));
        RoomTransactionWithCategory result = transactionList.get(0);
        Transaction resultTransaction = result.getTransaction();
        assertEquals(transaction, resultTransaction);
        assertEquals(updatedCategory, resultTransaction.getCategory().orElse(null));
        assertEquals(updatedCategory.getName(), resultTransaction.getCategory().map(Category::getName).orElse(null));
        assertEquals(updatedCategory.getType(), resultTransaction.getCategory().map(Category::getType).orElse(null));
    }
}
