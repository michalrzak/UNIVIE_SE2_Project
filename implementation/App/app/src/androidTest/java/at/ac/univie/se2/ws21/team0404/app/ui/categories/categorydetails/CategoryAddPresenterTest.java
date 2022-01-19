package at.ac.univie.se2.ws21.team0404.app.ui.categories.categorydetails;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;

@RunWith(MockitoJUnitRunner.class)
public class CategoryAddPresenterTest {

  @Mock
  private Repository mockedRepository;

  @Mock
  private ICategoryActivityContract.IView mockedView;

  private CategoryAddPresenter presenter;

  @Before
  public void setUp() {
    presenter = CategoryAddPresenter.create(mockedRepository);
    presenter.setView(mockedView);
  }

  @Test
  public void CategoryAddPresenter_clickedSave_passedValidCategory_checkViewCall() {
    when(mockedRepository.createCategory(any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.SUCCESS));

    Category mockCategory = Mockito.mock(Category.class);
    presenter.clickedSave(mockCategory);

    verify(mockedRepository, times(1)).createCategory(mockCategory);
    verify(mockedView, times(1)).showCategoryInsertionSuccessful();
  }

  @Test
  public void CategoryAddPresenter_clickedSave_returnsError_checkViewCall() {
    when(mockedRepository.createCategory(any())).thenReturn(new ChangingData<>(
            ERepositoryReturnStatus.ERROR));

    Category mockCategory = Mockito.mock(Category.class);
    presenter.clickedSave(mockCategory);
    verify(mockedView, times(1)).showCategoryInsertionFailed();
  }

  @Test
  public void CategoryAddPresenter_clickedSave_passedNullCategory_checkViewCall() {
    when(mockedRepository.createCategory(any())).thenReturn(new ChangingData<>(
            ERepositoryReturnStatus.UPDATING));

    Category mockCategory = null;
    presenter.clickedSave(mockCategory);
    verify(mockedView, times(1)).showCategoryInsertionFailed();
  }
}
