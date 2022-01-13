package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;

public class TransactionAdd extends ATransactionActivity implements
    ITransactionActivityContract.IView {

  private TransactionAddPresenter presenter;
  private LifecycleHandler<TransactionAdd> lifecycleHandler;

  @Override
  protected void setup() { // empty implementation as this activity does not need to set any values to the views
    presenter = TransactionAddPresenter.create(owner, Repository.getInstance());
    lifecycleHandler = new LifecycleHandler<>(presenter, this);
  }

  @Override
  protected void saveButtonPressed() {
    presenter.clickedSave(getTransactionFromForm().orElse(null));
  }

  @Override
  public void showTransactionInsertionSuccessful() {
    finish();
  }

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
