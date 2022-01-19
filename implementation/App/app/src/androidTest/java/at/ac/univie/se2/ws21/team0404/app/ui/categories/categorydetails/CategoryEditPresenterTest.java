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

import java.util.UUID;

import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;

@RunWith(MockitoJUnitRunner.class)
public class CategoryEditPresenterTest {

  private final static UUID insertedCategoryUUID = UUID.randomUUID();
  @Mock
  private Category insertedCategory;
  @Mock
  private Repository mockedRepository;

  @Mock
  private ICategoryActivityContract.IView mockedView;

  private CategoryEditPresenter presenter;

  @Before
  public void setUp() {
    when(insertedCategory.getId()).thenReturn(insertedCategoryUUID);

    presenter = CategoryEditPresenter
        .create(insertedCategory, mockedRepository);
    presenter.setView(mockedView);
  }

  @Test
  public void CategoryEditPresenter_clickedSave_passedValidCategory_checkViewCall() {
    when(mockedRepository.updateCategory(any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.SUCCESS));

    Category mockCategory = Mockito.mock(Category.class);
    presenter.clickedSave(mockCategory);
    verify(mockedRepository, times(1))
        .updateCategory(mockCategory);
    verify(mockedView, times(1)).showCategoryInsertionSuccessful();
  }

  @Test
  public void CategoryEditPresenter_clickedSave_returnsError_checkViewCall() {
    when(mockedRepository.updateCategory(any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.ERROR));

    Category mockCategory = Mockito.mock(Category.class);
    presenter.clickedSave(mockCategory);
    verify(mockedView, times(1)).showCategoryInsertionFailed();
  }

  @Test
  public void CategoryEditPresenter_clickedDelete_returnSuccess_checkViewCall() {
    when(mockedRepository.updateCategory(any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.SUCCESS));

    presenter.clickedDelete(insertedCategory);
    verify(mockedRepository, times(1)).updateCategory(insertedCategory);
    verify(mockedView, times(1)).showCategoryDeletionSuccessful();
  }

  @Test
  public void CategoryEditPresenter_clickedDelete_returnsError_checkViewCall() {
    when(mockedRepository.updateCategory(any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.ERROR));

    Category mockCategory = Mockito.mock(Category.class);

    presenter.clickedDelete(insertedCategory);

    verify(mockedRepository, times(1)).updateCategory(insertedCategory);
    verify(mockedView, times(1)).showCategoryDeletionFailed();
  }
}
