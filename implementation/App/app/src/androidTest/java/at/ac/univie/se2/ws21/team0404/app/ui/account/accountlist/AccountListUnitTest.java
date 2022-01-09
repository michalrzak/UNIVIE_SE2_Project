package at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails.AccountAdd;

@RunWith(MockitoJUnitRunner.class)
public class AccountListUnitTest {
    @Mock
    AppAccount mockTestAccount;
    final static int testAccountId = 99;
    final static String testAccountName = "testAccount";
    List<AppAccount> accountList = new ArrayList<>();

    @Mock
    AccountListPresenter mockPresenter;

    IAccountListContract.IView view;

    @Before
    public void setup() {
        AccountListPresenter.setFactory((Repository repository) -> mockPresenter);

        Intents.init();
        ActivityScenario.launch(AccountList.class);

        ArgumentCaptor<IAccountListContract.IView> captorView = ArgumentCaptor.forClass(IAccountListContract.IView.class);
        verify(mockPresenter).setView(captorView.capture());
        view = captorView.getValue();

        when(mockTestAccount.getId()).thenReturn(testAccountId);
        when(mockTestAccount.getName()).thenReturn(testAccountName);
        when(mockTestAccount.getType()).thenReturn(EAccountType.BANK);
        accountList.add(mockTestAccount);
    }

    @After
    public void teardown() {
        Intents.release();
    }

    @Test
    public void activityStarted_noInteraction_onCreateCalledOnce() {
        verify(mockPresenter, times(1)).onCreate();
    }

    @Test
    public void activityStarted_listProvided_viewShowsListEntries() {
        view.showList(accountList);
        onView(withId(R.id.list_recycler_view))
                .check(matches(hasDescendant(withText(containsString(testAccountName)))));
    }

    @Test
    public void activityStarted_fobClicked_presenterMethodCalled() {
        verify(mockPresenter, times(0)).clickFab();
        onView(withId(R.id.floating_button)).perform(click());
        verify(mockPresenter, times(1)).clickFab();
    }

    @Test
    public void activityStarted_fobRedirectCalled_viewShowsAddActivity() {
        view.showFabRedirect();
        Intents.intended(hasComponent(AccountAdd.class.getName()));
    }
}
