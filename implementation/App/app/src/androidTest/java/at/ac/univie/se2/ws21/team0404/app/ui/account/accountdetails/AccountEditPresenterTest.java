package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

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
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;

@RunWith(MockitoJUnitRunner.class)
public class AccountEditPresenterTest {

    @Mock
    private Repository mockRepository;

    @Mock
    private IAccountActivityContract.IView mockedView;

    private AccountEditPresenter presenter;

    @Before
    public void setUp(){

        presenter = AccountEditPresenter.create(mockRepository);
        presenter.setView(mockedView);
    }

    @Test
    public void AccountEditPresenter_clickedSave_passedValidAccount_checkViewCall(){
        when(mockRepository.updateAppAccount(any())).thenReturn(new ChangingData<>(
                ERepositoryReturnStatus.SUCCESS));

        AppAccount mockedAccount = Mockito.mock(AppAccount.class);
        presenter.clickedSave(mockedAccount);

        verify(mockRepository, times(1)).updateAppAccount(mockedAccount);
        verify(mockedView, times(1)).showAccountInsertionSuccessful();
    }

    @Test
    public void AccountEditPresenter_clickedSave_returnsError_checkViewCall(){
        when(mockRepository.updateAppAccount(any())).thenReturn(new ChangingData<>(
                ERepositoryReturnStatus.ERROR));

        AppAccount mockedAccount = Mockito.mock(AppAccount.class);
        presenter.clickedSave(mockedAccount);

        verify(mockRepository, times(1)).updateAppAccount(mockedAccount);
        verify(mockedView, times(1)).showAccountInsertionFailed();
    }

    @Test
    public void AccountEditPresenter_clickedDelete_returnSuccess_checkViewCall(){
        when(mockRepository.deleteAppAccount(any())).thenReturn(new ChangingData<>(
                ERepositoryReturnStatus.SUCCESS));

        AppAccount mockedAccount = Mockito.mock(AppAccount.class);
        presenter.clickedDelete(mockedAccount);

        verify(mockRepository, times(1)).deleteAppAccount(mockedAccount);
        verify(mockedView, times(1)).showAccountDeletionSuccessful();
    }

    @Test
    public void AccountEditPresenter_clickedDelete_returnsError_checkViewCall(){
        when(mockRepository.deleteAppAccount(any())).thenReturn(new ChangingData<>(
                ERepositoryReturnStatus.ERROR));

        AppAccount mockedAccount = Mockito.mock(AppAccount.class);
        presenter.clickedDelete(mockedAccount);

        verify(mockRepository, times(1)).deleteAppAccount(mockedAccount);
        verify(mockedView, times(1)).showAccountDeletionFailed();
    }
}
