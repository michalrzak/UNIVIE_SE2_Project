package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;

/**
 * An Activity to that allows to fill out fields needed to add a Transaction
 */
public class TransactionAdd extends ATransactionActivity implements
    ITransactionActivityContract.IView {

  private TransactionAddPresenter presenter;
  private LifecycleHandler<TransactionAdd> lifecycleHandler;

  @Override
  protected void setup() {
    presenter = TransactionAddPresenter.create(owner, Repository.getInstance());
    lifecycleHandler = new LifecycleHandler<>(presenter, this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void saveButtonPressed() {
    presenter.clickedSave(getTransactionFromForm().orElse(null));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showTransactionInsertionSuccessful() {
    finish();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void showTransactionInsertionFailed() {
    Toast.makeText(this, "Error on saving transaction, try again please.",
        Toast.LENGTH_LONG)
        .show();
  }

  /**
   * Not needed.
   */
  @Override
  public void showTransactionDeletionSuccessful() {
  }

  @Override
  public void showTransactionDeletionFailed() {
  }
}
