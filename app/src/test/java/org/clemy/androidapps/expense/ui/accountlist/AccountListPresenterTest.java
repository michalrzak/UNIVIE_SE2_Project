package org.clemy.androidapps.expense.ui.accountlist;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.clemy.androidapps.expense.database.MemoryDb;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.AccountList;
import org.clemy.androidapps.expense.utils.ChangingData;
import org.clemy.androidapps.expense.utils.ChangingDataImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountListPresenterTest {

    @Mock
    Repository mockRepository;

    @Mock
    AccountListContract.View mockView;

    @Mock
    AccountList mockAccountList;

    private AccountListContract.Presenter accountListPresenter;

    @Before
    public void setUp() {
        ChangingData<AccountList> changingData = new ChangingDataImpl<>(mockAccountList);
        when(mockRepository.getAccounts()).thenReturn(changingData);

        accountListPresenter = new AccountListPresenter(mockRepository);
        accountListPresenter.setView(mockView);
        accountListPresenter.onCreate();
    }

    @Test
    public void presenterCallsView_showAccountList_once() {
        verify(mockView, never()).showAccountList(mockAccountList);
        accountListPresenter.onStart();
        verify(mockView, times(1)).showAccountList(mockAccountList);
        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void presenterCallsView_showNewAccount_once() {
        verify(mockView, never()).showNewAccount();
        accountListPresenter.newAccount();
        verify(mockView, times(1)).showNewAccount();
        verifyNoMoreInteractions(mockView);
    }

    @Test
    public void switchToMemoryDb_activatesA_MemoryDb() {
        verify(mockRepository, never()).setDatabaseStrategy(any());
        accountListPresenter.switchToMemoryDb();
        verify(mockRepository, times(1)).setDatabaseStrategy(isA(MemoryDb.class));
    }

    /*
    @Ignore("RoomDb not available on host platform")
    @Test
    public void switchToSavedDb_activatesA_RoomDb() {
        verify(mockRepository, never()).setDatabaseStrategy(any());
        accountListPresenter.switchToSavedDb();
        verify(mockRepository, times(1)).setDatabaseStrategy(isA(RoomDb.class));
    }
    */
}
