package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionAddPresenterTest {

  @Mock
  private AppAccount insertedAccount;

  @Mock
  private Repository mockedRepository;

  @Mock
  private ITransactionActivityContract.IView mockedView;

  private TransactionAddPresenter presenter;

  @Before
  public void setUp() {
    presenter = TransactionAddPresenter.create(insertedAccount, mockedRepository);
    presenter.setView(mockedView);
  }

  @Test
  public void TransactionAddPresenter_clickedSave_passedValidTransaction_checkViewCall() {
    when(mockedRepository.createTransaction(any(), any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.SUCCESS));

    Transaction mockTransaction = Mockito.mock(Transaction.class);
    presenter.clickedSave(mockTransaction);

    verify(mockedRepository, times(1)).createTransaction(insertedAccount, mockTransaction);
    verify(mockedView, times(1)).showTransactionInsertionSuccessful();
  }

  @Test
  public void TransactionAddPresenter_clickedSave_returnsError_checkViewCall() {
    when(mockedRepository.createTransaction(any(), any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.ERROR));

    Transaction mockTransaction = Mockito.mock(Transaction.class);
    presenter.clickedSave(mockTransaction);
    verify(mockedView, times(1)).showTransactionInsertionFailed();
  }

  @Test
  public void TransactionAddPresenter_clickedSave_passedNullTransaction_checkViewCall() {
    when(mockedRepository.createTransaction(any(), any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.UPDATING));

    Transaction mockTransaction = null;
    presenter.clickedSave(mockTransaction);
    verify(mockedView, times(1)).showTransactionInsertionFailed();
  }

}
