package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
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
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails.TransactionAdd;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails.TransactionEdit;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionListTest {

  private final static int insertedAccountId = 99;
  private final static String insertedAccountName = "insertedAccount";
  private final static int insertedTransactionId = 10;
  private final static int insertedTransactionAmount = 100;
  private final static String insertedTransactionName = "insertedTransaction";
  @Mock
  private AppAccount insertedAccount;
  @Mock
  private Transaction insertedTransaction;
  private List<Transaction> transactionList = new ArrayList<>();

  @Mock
  private TransactionListPresenter mockPresenter;

  private ITransactionListContract.IView view;

  @Before
  public void setUp() {
    TransactionListPresenter.setFactory((AppAccount owner, Repository repository) -> mockPresenter);

    when(insertedAccount.getId()).thenReturn(insertedAccountId);
    when(insertedAccount.getName()).thenReturn(insertedAccountName);
    when(insertedAccount.getType()).thenReturn(EAccountType.BANK);

    when(insertedTransaction.getId()).thenReturn(insertedTransactionId);
    when(insertedTransaction.getName()).thenReturn(insertedTransactionName);
    when(insertedTransaction.getType()).thenReturn(ETransactionType.INCOME);
    when(insertedTransaction.getAmount()).thenReturn(insertedTransactionAmount);
    when(insertedTransaction.getCategory()).thenReturn(Optional.empty());
    transactionList.add(insertedTransaction);

    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), TransactionList.class)
        .putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(insertedAccount));
    ActivityScenario.launch(intent);

    ArgumentCaptor<ITransactionListContract.IView> captorView = ArgumentCaptor
        .forClass(ITransactionListContract.IView.class);
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
  public void TransactionList_showList_itemsShown() {
    view.showList(transactionList);
    onView(withId(R.id.list_recycler_view))
        .check(matches(hasDescendant(withText(insertedTransaction.getName()))));
  }

  @Test
  public void TransactionList_addNewTransaction_clickedFabCalled() {
    verify(mockPresenter, times(0)).clickFab();
    onView(withId(R.id.floating_button)).perform(click());
    verify(mockPresenter, times(1)).clickFab();
  }

  @Test
  public void TransactionList_editAccount_editAccountCalled() {
    verify(mockPresenter, times(0)).editAccount();
    onView(withId(R.id.edit_menu_icon)).perform(click());
    verify(mockPresenter, times(1)).editAccount();
  }

  @Test
  public void TransactionList_editTransaction_clickListItemCalled() {
    view.showList(transactionList);
    verify(mockPresenter, times(0)).clickListItem(insertedTransaction);
    onView(withText(insertedTransaction.getName())).perform(click());
    verify(mockPresenter, times(1)).clickListItem(insertedTransaction);
  }

  @Test
  public void TransactionList_fabRedirectCalled_correctIntent() {
    view.showFabRedirect();
    Intents.intended(hasComponent(TransactionAdd.class.getName()));
    Intents.intended(hasExtra(EIntents.ACCOUNT.toString(), insertedAccount));
  }

  @Test
  public void TransactionList_listItemRedirectCalled_correctIntent() {
    view.showListItemRedirect(insertedTransaction);
    Intents.intended(hasComponent(TransactionEdit.class.getName()));
    Intents.intended(hasExtra(EIntents.ACCOUNT.toString(), insertedAccount));
    Intents.intended(hasExtra(EIntents.TRANSACTION.toString(), insertedTransaction));
  }
}
