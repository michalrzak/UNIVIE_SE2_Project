package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionListPresenterTest {

  @Mock
  private AppAccount insertedAccount;

  @Mock
  private Repository mockedRepository;

  private TransactionListPresenter presenter;

  @Before
  public void setUp() {

    List<AppAccount> accountList = new ArrayList<>();
    accountList.add(insertedAccount);

    when(mockedRepository.getTransactionList(insertedAccount))
        .thenReturn(new ChangingData<>(new ArrayList<>()));

    presenter = TransactionListPresenter.create(insertedAccount, mockedRepository);
  }

  @Test
  public void TransactionListPresenter_createNew_gotListFromRepository() {
    presenter.viewCreated();
    verify(mockedRepository, times(1)).getTransactionList(insertedAccount);
  }

  @Test
  public void TransactionListPresenter_editAccount_viewGetsCalled() {
    ITransactionListContract.IView mockedView = Mockito.mock(ITransactionListContract.IView.class);
    presenter.setView(mockedView);
    presenter.editAccount();
    verify(mockedView, times(1)).showEditAccount();
  }

  @Test
  public void TransactionListPresenter_clickFab_viewGetsCalled() {
    ITransactionListContract.IView mockedView = Mockito.mock(ITransactionListContract.IView.class);
    presenter.setView(mockedView);
    presenter.clickFab();
    verify(mockedView, times(1)).showFabRedirect();
  }

  @Test
  public void TransactionListPresenter_clickListItem_viewGetsCalled() {
    ITransactionListContract.IView mockedView = Mockito.mock(ITransactionListContract.IView.class);
    presenter.setView(mockedView);
    Transaction mockedTransaction = Mockito.mock(Transaction.class);
    presenter.clickListItem(mockedTransaction);
    verify(mockedView, times(1)).showListItemRedirect(mockedTransaction);
  }


}
