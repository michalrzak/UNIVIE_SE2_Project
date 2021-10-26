package org.clemy.androidapps.expense.ui.accountlist;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.ui.newaccount.NewAccountActivity;
import org.junit.Rule;
import org.junit.Test;

public class AccountListActivityTest {
    @Rule
    public ActivityScenarioRule<AccountListActivity> rule = new ActivityScenarioRule<>(AccountListActivity.class);

    @Test
    public void switchTo_TestDatabase() {
        //TODO: mock presenter
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Use Test Database")).perform(click());
        //TODO: check if it worked
    }

    @Test
    public void click_NewAccount() {
        //TODO: mock presenter and split this test into 1 clicking the button and 1 checking the intent
        Intents.init();
        onView(withId(R.id.floating_button)).perform(click());
        Intents.intended(hasComponent(NewAccountActivity.class.getName()));
    }
}