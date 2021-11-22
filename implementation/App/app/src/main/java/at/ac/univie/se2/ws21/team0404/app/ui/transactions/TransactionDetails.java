package at.ac.univie.se2.ws21.team0404.app.ui.transactions;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ParcelableTransaction;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.ui.account.newOrAddAccount.NewOrAddAccountActivity;
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
    if (displayedTransaction.getCategory().isPresent()) {
      Log.d("TX_details", "category was provided, name was: " + displayedTransaction.getCategory().get());
      categorySpinner.setSelection(categoryAdapter.getPosition(displayedTransaction.getCategory()
          .get()));
    }
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.delete_menu_icon, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.delete_menu_icon) {
      Intent intent = new Intent();
      intent.putExtra(EIntents.TRANSACTION_ID.toString(), displayedTransaction.getId());
      setResult(Activity.RESULT_FIRST_USER, intent); //TODO: Change this to something more meaningful
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}