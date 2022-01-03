package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableTransaction;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

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

    nameEditText.setText(displayedTransaction.getName());
    amountEditText.setText(Integer.toString(displayedTransaction.getAmount()));
    typeSpinner.setSelection(typeAdapter.getPosition(displayedTransaction.getType()));
    if (displayedTransaction.getCategory().isPresent()) {
      Log.d("TX_details", "category was provided, name was: " + displayedTransaction.getCategory().get());
      updateCategorySelection(displayedTransaction.getCategory().get());
    }
  }

  @Override
  protected void saveButtonPressed() {
    assert (owner != null);

    Transaction newTransaction = getTransactionFromForm();
    int oldId = displayedTransaction.getId();

    try {
      Repository.getInstance().updateTransaction(owner, oldId, newTransaction);
      Log.d("TransactionEdit", "Successfully updated transaction");
      finish();
    } catch (DataDoesNotExistException e) {
      Log.e("TransactionEdit",
          "Tried to update a transaction from an account which does not exist. Message: "
              + e
              .getMessage());
      Toast.makeText(this, "Error on saving transaction try again please.",
          Toast.LENGTH_LONG)
          .show();
      finish();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.delete_menu_icon, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.delete_menu_icon) {

      int toBeDeleted = displayedTransaction.getId();
      try {
        Repository.getInstance().deleteTransaction(owner, toBeDeleted);
        Log.d("TransactionEdit", "Successfully deleted transaction");
      } catch (DataDoesNotExistException e) {
        Log.e("TransactionEdit",
            "Tried to delete a transaction from an account which does not exist or the"
                + "to be deleted transaction does not exists. Message: "
                + e
                .getMessage());
        Toast.makeText(this, "Error on saving transaction try again please.",
            Toast.LENGTH_LONG)
            .show();
      }
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}