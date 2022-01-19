package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorylist;

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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableCategory;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails.CategoryAdd;
import at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails.CategoryEdit;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

@RunWith(MockitoJUnitRunner.class)
public class CategoryListTest {

  private final static UUID insertedCategoryId = UUID.randomUUID();
  private final static String insertedCategoryName = "insertedCategory";
  private final static ETransactionType insertedCategoryType = ETransactionType.INCOME;
  @Mock
  private Category insertedCategory;
  private final List<Category> categoryList = new ArrayList<>();

  @Mock
  private CategoryListPresenter mockPresenter;

  private ICategoryListContract.IView view;

  @Before
  public void setUp() {
    CategoryListPresenter.setFactory((Repository repository) -> mockPresenter);

    when(insertedCategory.getId()).thenReturn(insertedCategoryId);
    when(insertedCategory.getName()).thenReturn(insertedCategoryName);
    when(insertedCategory.getType()).thenReturn(insertedCategoryType);
    categoryList.add(insertedCategory);

    Intent intent = new Intent(ApplicationProvider.getApplicationContext(), CategoryList.class)
        .putExtra(EIntents.CATEGORY.toString(), new ParcelableCategory(insertedCategory));
    ActivityScenario.launch(intent);

    ArgumentCaptor<ICategoryListContract.IView> captorView = ArgumentCaptor
        .forClass(ICategoryListContract.IView.class);
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
  public void CategoryList_showList_itemsShown() {
    view.showList(categoryList);
    onView(withId(R.id.list_recycler_view))
        .check(matches(hasDescendant(withText(insertedCategory.getName()))));
  }

  @Test
  public void CategoryList_addNewCategory_clickedFabCalled() {
    verify(mockPresenter, times(0)).clickFab();
    onView(withId(R.id.floating_button)).perform(click());
    verify(mockPresenter, times(1)).clickFab();
  }


  @Test
  public void CategoryList_editCategory_clickListItemCalled() {
    view.showList(categoryList);
    verify(mockPresenter, times(0)).clickListItem(insertedCategory);
    onView(withText(insertedCategory.getName())).perform(click());
    verify(mockPresenter, times(1)).clickListItem(insertedCategory);
  }

  @Test
  public void CategoryList_fabRedirectCalled_correctIntent() {
    view.showFabRedirect();
    Intents.intended(hasComponent(CategoryAdd.class.getName()));
  }

  @Test
  public void CategoryList_listItemRedirectCalled_correctIntent() {
    view.showListItemRedirect(insertedCategory);
    Intents.intended(hasComponent(CategoryEdit.class.getName()));
    Intents.intended(hasExtra(EIntents.CATEGORY.toString(), insertedCategory));
  }
}
