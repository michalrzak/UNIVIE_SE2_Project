package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableTransaction;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails.ITransactionActivityContract.IView;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import java.util.Optional;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionEditTest {

  private final static UUID insertedAccountId = UUID.randomUUID();
  private final static String insertedAccountName = "insertedAccount";
  private final static UUID insertedTransactionId = UUID.randomUUID();
  private final static String insertedTransactionName = "insertedTransaction";
  private final static ETransactionType insertedTransactionType = ETransactionType.INCOME;
  private final static int insertedTransactionAmount = 100;
  @Mock
  private AppAccount insertedAccount;
  @Mock
  private Transaction insertedTransaction;
  @Mock
  private TransactionEditPresenter mockPresenter;
  private ITransactionActivityContract.IView view;

  @Before
  public void setUp() {
    TransactionEditPresenter.setFactory(
        (AppAccount owner, Transaction editing, Repository repository) -> mockPresenter);

    when(insertedAccount.getId()).thenReturn(insertedAccountId);
    when(insertedAccount.getName()).thenReturn(insertedAccountName);
    when(insertedAccount.getType()).thenReturn(EAccountType.BANK);

    when(insertedTransaction.getId()).thenReturn(insertedTransactionId);
    when(insertedTransaction.getName()).thenReturn(insertedTransactionName);
    when(insertedTransaction.getType()).thenReturn(insertedTransactionType);
    when(insertedTransaction.getAmount()).thenReturn(insertedTransactionAmount);
    when(insertedTransaction.getCategory()).thenReturn(Optional.empty());

    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), TransactionEdit.class)
        .putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(insertedAccount))
        .putExtra(EIntents.TRANSACTION.toString(), new ParcelableTransaction(insertedTransaction));
    ActivityScenario.launch(intent);

    ArgumentCaptor<IView> captorView = ArgumentCaptor
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

  private void insertFieldsToView(String name, String amount, ETransactionType type) {
    onView(withId(R.id.transaction_name_editText)).perform(clearText());
    onView(withId(R.id.transaction_name_editText)).perform(typeText(name));
    onView(withId(R.id.transaction_amount_edittext)).perform(clearText());
    onView(withId(R.id.transaction_amount_edittext))
        .perform(typeText(amount));
    onView(withId(R.id.transaction_type_spinner)).perform(click());
    onData(allOf(is(instanceOf(ETransactionType.class)), is(type)))
        .perform(click());

    closeSoftKeyboard();
  }

  private void insertTransactionToView(Transaction transaction) {
    insertFieldsToView(transaction.getName(), Integer.toString(transaction.getAmount()),
        transaction.getType());
  }

  @Test
  public void TransactionEdit_doNothing_fieldsInitializedCorrectly() {
    onView(withId(R.id.transaction_name_editText))
        .check(matches(withText(insertedTransactionName)));
    onView(withId(R.id.transaction_amount_edittext))
        .check(matches(withText(Integer.toString(insertedTransactionAmount))));
    onView(withId(R.id.transaction_type_spinner))
        .check(matches(withSpinnerText(insertedTransactionType.toString())));
  }

  @Test
  public void TransactionEdit_submitEmptyNothing_clickedSaveCalled() {
    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(any());
  }

  @Test
  public void TransactionEdit_submitValidTransaction_clickedSaveCalled() {

    // This unfortunately has to be the real and not a mocked type, as I need to compare it
    Transaction real = new Transaction(insertedTransaction.getId(), null, ETransactionType.INCOME,
        100, "Test name");
    insertTransactionToView(real);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(real);
  }

  @Test
  public void TransactionEdit_editAndPressDeleteButton_clickedDeleteCalled() {
    verify(mockPresenter, times(0)).clickedDelete();
    onView(withId(R.id.transaction_name_editText)).perform(typeText("some text"));
    onView(withId(R.id.delete_menu_icon)).perform(click());
    verify(mockPresenter, times(1)).clickedDelete();
  }

  @Test
  public void TransactionEdit_submitTooLargeAmount_clickSaveCalled() {
    final String name = "Test name";
    final String amount = "100000000000000000";
    final ETransactionType type = ETransactionType.INCOME;

    insertFieldsToView(name, amount, type);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }

  @Test
  public void TransactionEdit_submitNegativeAmount_clickSaveCalled() {
    final String name = "Test name";
    final String amount = "-100";
    final ETransactionType type = ETransactionType.INCOME;

    insertFieldsToView(name, amount, type);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }

  @Test
  public void TransactionAEdit_submitTextAmount_clickSaveCalled() {
    final String name = "Test name";
    final String amount = "abd";
    final ETransactionType type = ETransactionType.INCOME;

    insertFieldsToView(name, amount, type);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }

  @Test
  public void TransactionEdit_submitZeroAmount_clickSaveCalled() {
    final String name = "Test name";
    final String amount = "0";
    final ETransactionType type = ETransactionType.INCOME;

    // This unfortunately has to be the real and not a mocked type, as I need to compare it
    Transaction real = new Transaction(UUID.randomUUID(), null, ETransactionType.INCOME, Integer.parseInt(amount),
        name);

    insertFieldsToView(name, amount, type);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(real);
  }

  @Test
  public void TransactionEdit_submitEmptyName_clickSaveCalled() {
    final String name = "";
    final String amount = "100";
    final ETransactionType type = ETransactionType.INCOME;

    insertFieldsToView(name, amount, type);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }

  @Test
  public void TransactionAdd_submitEmptyAmount_clickSaveCalled() {
    final String name = "Test name";
    final String amount = "";
    final ETransactionType type = ETransactionType.EXPENSE;

    Transaction real = new Transaction(UUID.randomUUID(), null, type, 0, name);

    insertFieldsToView(name, amount, type);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.transaction_save_button)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(real);
  }
}
