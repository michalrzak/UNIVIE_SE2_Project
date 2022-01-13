package at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactiondetails;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class TransactionEdit extends ATransactionActivity implements
    ITransactionActivityContract.IView {

  /**
   * Holds the transaction displayed by this activity. Can be null, should no Transaction be sent
   * with the intent (Probably add implementation).
   */
  private Transaction displayedTransaction;

  private TransactionEditPresenter presenter;
  private LifecycleHandler<TransactionEdit> lifecycleHandler;

  @Override
  protected void setup() {
    Intent intent = getIntent();
    displayedTransaction = intent.getParcelableExtra(EIntents.TRANSACTION.toString());
    assert (displayedTransaction != null);

    nameEditText.setText(displayedTransaction.getName());
    amountEditText.setText(Integer.toString(displayedTransaction.getAmount()));
    typeSpinner.setSelection(typeAdapter.getPosition(displayedTransaction.getType()));
    if (displayedTransaction.getCategory().isPresent()) {
      Log.d("TX_details",
          "category was provided, name was: " + displayedTransaction.getCategory().get());
      updateCategorySelection(displayedTransaction.getCategory().get());
    }

    presenter = TransactionEditPresenter.create(owner, displayedTransaction, Repository.getInstance());
    lifecycleHandler = new LifecycleHandler<>(presenter, this);
  }

  @Override
  protected void saveButtonPressed() {
    presenter.clickedSave(getTransactionFromForm().orElse(null));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.delete_menu_icon, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.delete_menu_icon) {
      presenter.clickedDelete();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void showTransactionInsertionSuccessful() {
    finish();
  }

  @Override
  public void showTransactionInsertionFailed() {
    Toast.makeText(this, "Error on saving transaction try again please.",
        Toast.LENGTH_LONG).show();
  }

  @Override
  public void showTransactionDeletionSuccessful() {
    finish();
  }

  @Override
  public void showTransactionDeletionFailed() {
    Toast.makeText(this, "Error on saving transaction try again please.",
        Toast.LENGTH_LONG).show();
    finish();
  }
}