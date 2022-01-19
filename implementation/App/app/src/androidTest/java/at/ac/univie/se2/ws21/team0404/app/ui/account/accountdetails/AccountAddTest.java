package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;

@RunWith(MockitoJUnitRunner.class)
public class AccountAddTest {

    private final String accountName = "Bob";
    private final EAccountType accountType = EAccountType.BANK;
    private final String spendingLimit = "100";

    private final String empty = "";

    @Mock
    private AccountAddPresenter mockPresenter;
    private IAccountActivityContract.IView view;

    @Before
    public void setUp(){
        AccountAddPresenter.setFactory((Repository repository) -> mockPresenter);

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AccountAdd.class);
        ActivityScenario.launch(intent);

        ArgumentCaptor<IAccountActivityContract.IView> captorView = ArgumentCaptor
                .forClass(IAccountActivityContract.IView.class);
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

    private void insertFieldsToView(String accountName, EAccountType accountType, String spendingLimit){
        onView(withId(R.id.account_name_edit_add)).perform(typeText(accountName));
        onView(withId(R.id.account_type_spinner)).perform(click());
        onData(allOf(is(instanceOf(EAccountType.class)), is(accountType))).perform(click());
        onView(withId(R.id.account_limit_edit_add)).perform(typeText(spendingLimit));
        closeSoftKeyboard();
    }

    @Test
    public void AccountAdd_submitEmptyNothing_clickedSaveCalled(){
        verify(mockPresenter, times(0)).clickedSave(any());
        onView(withId(R.id.edit_or_add_account_button)).perform(click());
        verify(mockPresenter, times(1)).clickedSave(null);
    }

    @Test
    public void AccountAdd_submitValidAccount_clickedSaveCalled(){
        insertFieldsToView(accountName, accountType, spendingLimit);

        AppAccount real = new AppAccount(accountName, accountType, Double.parseDouble(spendingLimit));

        verify(mockPresenter, times(0)).clickedSave(any());
        onView(withId(R.id.edit_or_add_account_button)).perform(click());
        verify(mockPresenter, times(1)).clickedSave(real);
    }

    @Test
    public void AccountAdd_submitEmptyName_clickSaveCalled(){
        insertFieldsToView(empty, accountType, spendingLimit);

        verify(mockPresenter, times(0)).clickedSave(any());
        onView(withId(R.id.edit_or_add_account_button)).perform(click());
        verify(mockPresenter, times(1)).clickedSave(null);
    }

    @Test
    public void AccountAdd_submitEmptySpendingLimit_clickSaveCalled(){
        insertFieldsToView(accountName, accountType, empty);

        AppAccount real = new AppAccount(accountName, accountType, 0);

        verify(mockPresenter, times(0)).clickedSave(any());
        onView(withId(R.id.edit_or_add_account_button)).perform(click());
        verify(mockPresenter, times(1)).clickedSave(real);
    }
}
