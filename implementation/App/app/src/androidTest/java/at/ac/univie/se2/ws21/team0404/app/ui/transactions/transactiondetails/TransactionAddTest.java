package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.widget.DatePicker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.intent.Intents;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import java.util.UUID;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class TransactionAddTest {

  private final static UUID insertedAccountId = UUID.randomUUID();
  private final static String insertedAccountName = "insertedAccount";
  @Mock
  private AppAccount insertedAccount;
  @Mock
  private TransactionAddPresenter mockPresenter;
  private ITransactionActivityContract.IView view;

  @Before
  public void setUp() {
    TransactionAddPresenter.setFactory((AppAccount owner, Repository repository) -> mockPresenter);

    when(insertedAccount.getId()).thenReturn(insertedAccountId);
    when(insertedAccount.getName()).thenReturn(insertedAccountName);
    when(insertedAccount.getType()).thenReturn(EAccountType.BANK);

    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), TransactionAdd.class)
        .putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(insertedAccount));
    ActivityScenario.launch(intent);

    ArgumentCaptor<ITransactionActivityContract.IView> captorView = ArgumentCaptor
        .forClass(ITransactionActivityContract.IView.class);
    verify(mockPresenter).setView(captorView.capture());
    view = captorView.getValue();
  }

  @Before
  public void startIntents() {
    Intents.init();
  }

  @After
  public void stopIntents() {
    Intents.release();
  }

  @Test
  public void TransactionAdd_submitEmptyNothing_clickedSaveCalled() {
    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }

  private void insertFieldsToView(String name, String amount, ETransactionType type, Date date) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    onView(withId(R.id.transaction_name_editText)).perform(typeText(name));
    onView(withId(R.id.transaction_amount_edittext)).perform(typeText(amount));
    closeSoftKeyboard();
    onView(withId(R.id.transaction_date_picker)).perform(click());
    onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(
            PickerActions.setDate(
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            ));
    onView(withId(android.R.id.button1)).perform(click());
    onView(withId(R.id.transaction_type_spinner)).perform(click());
    onData(allOf(is(instanceOf(ETransactionType.class)), is(type))).perform(click());

  }

  @Test
  public void TransactionAdd_submitValidTransaction_clickedSaveCalled() {
    final String name = "Test name";
    final String amount = "100";
    final Date testDate = new Date(1508388214);
    final ETransactionType type = ETransactionType.INCOME;

    // This unfortunately has to be the real and not a mocked type, as I need to compare it
    Transaction real = new Transaction(UUID.randomUUID(), null, type, Integer.parseInt(amount), name, testDate);

    insertFieldsToView(name, amount, type, testDate);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(real);
  }

  @Test
  public void TransactionAdd_submitTooLargeAmount_clickSaveCalled() {
    final String name = "Test name";
    final String amount = "100000000000000000";
    final Date testDate = new Date(1508388214);
    final ETransactionType type = ETransactionType.INCOME;

    insertFieldsToView(name, amount, type, testDate);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }

  @Test
  public void TransactionAdd_submitNegativeAmount_minusNotWritten() {
    final String name = "Test name";
    final String amount = "-100";
    final Date testDate = new Date(1508388214);
    final ETransactionType type = ETransactionType.INCOME;

    insertFieldsToView(name, amount, type, testDate);

    onView(withId(R.id.transaction_amount_edittext)).check(matches(withText("100")));
  }

  @Test
  public void TransactionAdd_submitTextAmount_checkAmountNotWritten() {
    final String name = "Test name";
    final String amount = "abd";
    final Date testDate = new Date(1508388214);
    final ETransactionType type = ETransactionType.INCOME;

    insertFieldsToView(name, amount, type, testDate);

    onView(withId(R.id.transaction_amount_edittext)).check(matches(withText("")));
  }

  @Test
  public void TransactionAdd_submitZeroAmount_clickSaveCalled() {
    final String name = "Test name";
    final String amount = "0";
    final Date testDate = new Date(1508388214);
    final ETransactionType type = ETransactionType.INCOME;

    // This unfortunately has to be the real and not a mocked type, as I need to compare it
    Transaction real = new Transaction(UUID.randomUUID(), null, type, Integer.parseInt(amount), name, testDate);

    insertFieldsToView(name, amount, type, testDate);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(real);
  }

  @Test
  public void TransactionAdd_submitEmptyName_clickSaveCalled() {
    final String name = "";
    final String amount = "100";
    final Date testDate = new Date(1508388214);
    final ETransactionType type = ETransactionType.INCOME;

    insertFieldsToView(name, amount, type, testDate);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }

  @Test
  public void TransactionAdd_submitEmptyAmount_clickSaveCalled() {
    final String name = "Test name";
    final String amount = "";
    final Date testDate = new Date(1508388214);
    final ETransactionType type = ETransactionType.EXPENSE;

    Transaction real = new Transaction(UUID.randomUUID(), null, type, 0, name, testDate);

    insertFieldsToView(name, amount, type, testDate);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(real);
  }
}
