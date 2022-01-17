package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

@RunWith(MockitoJUnitRunner.class)
public class AccountEditTest {

    private final static UUID accountId = UUID.randomUUID();
    private final String accountName = "Bob";
    private final EAccountType accountType = EAccountType.BANK;
    private final double spendingLimit = 100;
    private final double balance = 100;
    private final String newName = "new name";

    @Mock
    private AccountEditPresenter mockPresenter;
    private IAccountActivityContract.IView view;

    @Mock
    private AppAccount insertedAccount;

    @Before
    public void setUp(){
        AccountEditPresenter.setFactory((Repository repository) -> mockPresenter);

        when(insertedAccount.getId()).thenReturn(accountId);
        when(insertedAccount.getName()).thenReturn(accountName);
        when(insertedAccount.getSpendingLimit()).thenReturn(spendingLimit);
        when(insertedAccount.getType()).thenReturn(accountType);
        when(insertedAccount.getSpendingLimit()).thenReturn(balance);

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AccountEdit.class)
                .putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(insertedAccount));
        ActivityScenario.launch(intent);

        ArgumentCaptor<IAccountActivityContract.IView> captorView = ArgumentCaptor
                .forClass(IAccountActivityContract.IView.class);
        verify(mockPresenter).setView(captorView.capture());
        view = captorView.getValue();
    }

    private void insertFieldsToView(String name, EAccountType type, double spendingLimit){
        onView(withId(R.id.account_name_edit_add)).perform(replaceText(name));
        onView(withId(R.id.account_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(EAccountType.class)), is(type))).perform(click());
        onView(withId(R.id.account_limit_edit_add)).perform(replaceText(Double.toString(spendingLimit)));
        closeSoftKeyboard();
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
    public void AccountEdit_doNothing_fieldsInitializedCorrectly(){
        onView(withId(R.id.account_name_edit_add))
                .check(matches(withText(accountName)));
        onView(withId(R.id.account_type_spinner))
                .check(matches(withSpinnerText(accountType.toString())));
        onView(withId(R.id.account_limit_edit_add))
                .check(matches(withText(Double.toString(spendingLimit))));
    }

    @Test
    public void AccountEdit_submitEmptyNothing_clickedSaveCalled(){
        verify(mockPresenter, times(0)).clickedSave(any());
        onView(withId(R.id.edit_or_add_account_button)).perform(click());
        verify(mockPresenter, times(1)).clickedSave(any());
    }

    @Test
    public void AccountEdit_doNothingAndPressDeleteButton_clickedDeleteCalled() {
        verify(mockPresenter, times(0)).clickedDelete(any());
        onView(withId(R.id.delete_menu_icon)).perform(click());
        verify(mockPresenter, times(1)).clickedDelete(any());
    }

    @Test
    public void AccountEdit_submitValidAccount_clickedSaveCalled() {
        AppAccount real = new AppAccount(newName, insertedAccount.getType(),
                insertedAccount.getId(), insertedAccount.getSpendingLimit(), insertedAccount.getBalance());

        insertFieldsToView(real.getName(), real.getType(), real.getSpendingLimit());

        verify(mockPresenter, times(0)).clickedSave(any());
        onView(withId(R.id.edit_or_add_account_button)).perform(click());
        verify(mockPresenter, times(1)).clickedSave(real);
    }

    @Test
    public void AccountEdit_editAndPressDeleteButton_clickedDeleteCalled(){
        verify(mockPresenter, times(0)).clickedDelete(any());
        onView(withId(R.id.account_name_edit_add)).perform(replaceText(newName));
        onView(withId(R.id.delete_menu_icon)).perform(click());
        verify(mockPresenter, times(1)).clickedDelete(any());
    }

    @Test
    public void AccountEdit_submitEmptySpendingLimit_clickSaveCalled(){
        AppAccount real = new AppAccount(insertedAccount.getName(), insertedAccount.getType(),
                insertedAccount.getId(), 0, insertedAccount.getBalance());

        onView(withId(R.id.account_limit_edit_add)).perform(replaceText(Double.toString(real.getSpendingLimit())));
        verify(mockPresenter, times(0)).clickedSave(any());
        onView(withId(R.id.edit_or_add_account_button)).perform(click());
        verify(mockPresenter, times(1)).clickedSave(real);
    }
}
