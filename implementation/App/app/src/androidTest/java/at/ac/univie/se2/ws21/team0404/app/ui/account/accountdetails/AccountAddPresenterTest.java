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
public class AccountAddPresenterTest {

    @Mock
    private Repository mockedRepository;

    @Mock
    private IAccountActivityContract.IView mockedView;

    private AccountAddPresenter presenter;


    @Before
    public void setUp(){
        presenter = AccountAddPresenter.create(mockedRepository);
        presenter.setView(mockedView);
    }

    @Test
    public void AccountAddPresenter_clickedSave_passedValidAppAccount_checkViewCall(){
        when(mockedRepository.createAppAccount(any())).thenReturn(new ChangingData<>(
                ERepositoryReturnStatus.SUCCESS));

        AppAccount mockedAccount = Mockito.mock(AppAccount.class);
        presenter.clickedSave(mockedAccount);

        verify(mockedRepository, times(1)).createAppAccount(mockedAccount);
        verify(mockedView, times(1)).showAccountInsertionSuccessful();
    }

    @Test
    public void AccountAddPresenter_clickedSave_returnsError_checkViewCall(){
        when(mockedRepository.createAppAccount(any())).thenReturn(new ChangingData<>(
                ERepositoryReturnStatus.ERROR));

        AppAccount mockedAccount = Mockito.mock(AppAccount.class);
        presenter.clickedSave(mockedAccount);

        verify(mockedRepository, times(1)).createAppAccount(mockedAccount);
        verify(mockedView, times(1)).showAccountInsertionFailed();
    }

    @Test
    public void AccountAddPresenter_clickedSave_passedNullAccount_checkViewCall(){
        when(mockedRepository.createAppAccount(any())).thenReturn(new ChangingData<>(
                ERepositoryReturnStatus.ERROR));

        AppAccount mockedAccount = null;
        presenter.clickedSave(mockedAccount);

        verify(mockedRepository, times(0)).createAppAccount(mockedAccount);
        verify(mockedView, times(1)).showAccountInsertionFailed();
    }
}
