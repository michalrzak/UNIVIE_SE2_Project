package at.ac.univie.se2.ws21.team0404.app.model.android;

import static org.junit.Assert.assertEquals;

import android.os.Parcel;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class ParcelableTransactionTest {

  private Transaction testTransaction;

  private Transaction getTransaction() {
    Category mockCategory = new Category(ETransactionType.EXPENSE, "test name");
    int testAmount = 10;
    String testName = "test";
    Date testDate = new Date(1508388214);
    ETransactionType transactionType = ETransactionType.EXPENSE;
    return new Transaction(mockCategory, transactionType, testAmount, testName, testDate);
  }

  @Before
  public void setUp() {
    testTransaction = getTransaction();
  }

  @Test
  public void createParcelableTransaction_fromTransaction_equals() {
    ParcelableTransaction testParcelableTransaction = new ParcelableTransaction(testTransaction);
    assertEquals(testParcelableTransaction, testTransaction);
  }

  @Test
  public void createParcelableTransaction_fromParcel_equals() {
    ParcelableTransaction testParcelableTransaction = new ParcelableTransaction(testTransaction);

    Parcel parcel = Parcel.obtain();
    testParcelableTransaction.writeToParcel(parcel, 0);
    // make parcel readable
    parcel.setDataPosition(0);

    ParcelableTransaction fromParcelParcelableTransaction = new ParcelableTransaction(parcel);
    assertEquals(testParcelableTransaction, fromParcelParcelableTransaction);
  }

}
