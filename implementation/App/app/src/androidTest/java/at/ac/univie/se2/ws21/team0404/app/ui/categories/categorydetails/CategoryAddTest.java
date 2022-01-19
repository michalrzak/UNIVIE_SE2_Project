package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
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
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableCategory;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

@RunWith(MockitoJUnitRunner.class)
public class CategoryAddTest {

  private static class Fixtures {
    public static final String validName = "Test name";
    public static final String emptyName = "";
    public static final ETransactionType incomeType = ETransactionType.INCOME;
    public static final ETransactionType expenseType = ETransactionType.INCOME;
  }

  private final static UUID insertedCategoryId = UUID.randomUUID();
  private final static String insertedCategoryName= "insertedCategory";
  private final static ETransactionType insertedCategoryType= ETransactionType.EXPENSE;
  @Mock
  private CategoryAddPresenter mockPresenter;
  private ICategoryActivityContract.IView view;

  @Mock
  private Category insertedCategory;

  @Before
  public void setUp() {
    CategoryAddPresenter.setFactory((Repository repository) -> mockPresenter);

    when(insertedCategory.getId()).thenReturn(insertedCategoryId);
    when(insertedCategory.getName()).thenReturn(insertedCategoryName);
    when(insertedCategory.getType()).thenReturn(insertedCategoryType);

    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CategoryAdd.class)
        .putExtra(EIntents.CATEGORY.toString(), new ParcelableCategory(insertedCategory));
    ActivityScenario.launch(intent);

    ArgumentCaptor<ICategoryActivityContract.IView> captorView = ArgumentCaptor
        .forClass(ICategoryActivityContract.IView.class);
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
  public void CategoryAdd_submitEmptyNothing_clickedSaveCalled() {
    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.submitButton)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }

  private void insertFieldsToView(String name, ETransactionType type) {
    onView(withId(R.id.editTextCategoryName)).perform(typeText(name));
    closeSoftKeyboard();
    switch (type) {
      case INCOME:
        onView(withId(R.id.radioIncome)).perform(click());
        break;
      case EXPENSE:
        onView(withId(R.id.radioExpense)).perform(click());
        break;
      default:
          break;
    }
  }

  @Test
  public void CategoryAdd_submitValidCategory_clickedSaveCalled() {
    // This unfortunately has to be the real and not a mocked type, as I need to compare it
    Category real = new Category(Fixtures.expenseType, Fixtures.validName);

    insertFieldsToView(Fixtures.validName, Fixtures.expenseType);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.submitButton)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(real);
  }

  @Test
  public void CategoryAdd_submitEmptyName_clickSaveCalled() {
    insertFieldsToView(Fixtures.emptyName, Fixtures.incomeType);

    verify(mockPresenter, times(0)).clickedSave(any());
    onView(withId(R.id.submitButton)).perform(click());
    verify(mockPresenter, times(1)).clickedSave(null);
  }
}
