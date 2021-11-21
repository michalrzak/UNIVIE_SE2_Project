package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import android.app.Activity;
import android.content.Intent;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ParcelableTransaction;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

public class TransactionDetails extends ATransactionActivity {

  /**
   * Holds the transaction displayed by this activity. Can be null, should no Transaction be sent
   * with the intent (Probably add implementation).
   */
  private Transaction displayedTransaction;

  @Override
  void setup() {
    Intent intent = getIntent();
    displayedTransaction = intent.getParcelableExtra(EIntents.TRANSACTION.toString());
    assert (displayedTransaction != null);

    amountEditText.setText(Integer.toString(displayedTransaction.getAmount()));
    typeSpinner.setSelection(typeAdapter.getPosition(displayedTransaction.getType()));
    // TODO: category spinner
  }

  @Override
  protected void saveButtonPressed() {
    Intent intent = new Intent();
    intent.putExtra(EIntents.TRANSACTION.toString(),
        new ParcelableTransaction(getTransactionFromForm()));
    intent.putExtra(EIntents.TRANSACTION_ID.toString(), displayedTransaction.getId());
    setResult(Activity.RESULT_OK, intent);
    finish();
  }
}