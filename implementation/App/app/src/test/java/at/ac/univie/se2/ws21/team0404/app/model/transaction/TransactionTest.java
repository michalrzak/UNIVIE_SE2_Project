package at.ac.univie.se2.ws21.team0404.app.model.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;

@RunWith(MockitoJUnitRunner.class)
public class TransactionTest {

  private final int testAmount = 10;
  private final ETransactionType transactionType = ETransactionType.EXPENSE;
  private final String testName = "test";
  private final Date testDate = new Date(1508388214);
  // define some fields which will be tested
  @Mock
  private Category mockCategory;
  private Transaction testTransaction;

  private Transaction getTransaction() {
    return new Transaction(mockCategory, transactionType, testAmount, testName, testDate);
  }

  @Before
  public void setUp() {
    testTransaction = getTransaction();
  }

  @Test
  public void createTwoTransactions_idsNotEqual() {
    Transaction otherTransaction = getTransaction();
    assertNotEquals(otherTransaction.getId(), testTransaction.getId());
  }

  @Test
  public void compareTransaction_equalIfIdsEqual() {
    Transaction mockTransaction = mock(Transaction.class);
    when(mockTransaction.getId()).thenReturn(testTransaction.getId());

    // is assertTrue to make it clear the equals gets called on testTransaction
    assertTrue(testTransaction.equals(mockTransaction));
  }

  @Test
  public void compareTransactions_notEqualIfIdsAndNamesNotEqual() {
    Transaction otherTransaction = getTransaction();
    otherTransaction.setName("other name");
    assertNotEquals(otherTransaction.getId(), testTransaction.getId());
    assertNotEquals(otherTransaction, testTransaction);
  }

  @Test
  public void createTransaction_negativeAmount_throwsException() {
    assertThrows(IllegalArgumentException.class,
        () -> new Transaction(mockCategory, transactionType, -100,
            testName, testDate));
  }

  @Test
  public void createTransaction_zeroAmount_noException() {
    Transaction zeroTransaction = new Transaction(mockCategory, transactionType, 0, testName, testDate);
    assertNotNull(zeroTransaction);
  }

  @Test
  public void updateTransaction_negativeAmount_throwsException() {
    assertThrows(IllegalArgumentException.class, () -> testTransaction.setAmount(-100));
  }

  @Test
  public void updateTransaction_zeroAmount_noException() {
    testTransaction.setAmount(0);
    assertNotNull(testTransaction);
    assertEquals(testTransaction.getAmount(), 0);
  }

  @Test
  public void createTransaction_NullCategory_returnsEmptyOptional() {
    Transaction nullCategory = new Transaction(null, transactionType, testAmount, testName, testDate);

    assertNotNull(nullCategory);
    assertFalse(nullCategory.getCategory().isPresent());
  }
}
