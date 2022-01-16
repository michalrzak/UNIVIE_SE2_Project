package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import at.ac.univie.se2.ws21.team0404.app.database.ERepositoryReturnStatus;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.ChangingData;
import java.util.UUID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TransactionEditPresenterTest {

  private final static UUID insertedTransactionId = UUID.randomUUID();
  @Mock
  private AppAccount insertedAccount;
  @Mock
  private Transaction insertedTransaction;
  @Mock
  private Repository mockedRepository;

  @Mock
  private ITransactionActivityContract.IView mockedView;

  private TransactionEditPresenter presenter;

  @Before
  public void setUp() {
    when(insertedTransaction.getId()).thenReturn(insertedTransactionId);

    presenter = TransactionEditPresenter
        .create(insertedAccount, insertedTransaction, mockedRepository);
    presenter.setView(mockedView);
  }

  @Test
  public void TransactionEditPresenter_clickedSave_passedValidTransaction_checkViewCall() {
    when(mockedRepository.updateTransaction(any(), any(), any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.SUCCESS));

    Transaction mockTransaction = Mockito.mock(Transaction.class);
    presenter.clickedSave(mockTransaction);
    verify(mockedRepository, times(1))
        .updateTransaction(insertedAccount, insertedTransactionId, mockTransaction);
    verify(mockedView, times(1)).showTransactionInsertionSuccessful();
  }

  @Test
  public void TransactionEditPresenter_clickedSave_returnsError_checkViewCall() {
    when(mockedRepository.updateTransaction(any(), any(), any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.ERROR));

    Transaction mockTransaction = Mockito.mock(Transaction.class);
    presenter.clickedSave(mockTransaction);
    verify(mockedView, times(1)).showTransactionInsertionFailed();
  }

  @Test
  public void TransactionEditPresenter_clickedDelete_returnSuccess_checkViewCall() {
    when(mockedRepository.deleteTransaction(any(), any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.SUCCESS));

    presenter.clickedDelete();
    verify(mockedRepository, times(1)).deleteTransaction(insertedAccount, insertedTransactionId);
    verify(mockedView, times(1)).showTransactionDeletionSuccessful();
  }

  @Test
  public void TransactionEditPresenter_clickedDelete_returnsError_checkViewCall() {
    when(mockedRepository.deleteTransaction(any(), any())).thenReturn(new ChangingData<>(
        ERepositoryReturnStatus.ERROR));

    presenter.clickedDelete();
    verify(mockedRepository, times(1)).deleteTransaction(insertedAccount, insertedTransactionId);
    verify(mockedView, times(0)).showTransactionDeletionSuccessful();
  }
}
