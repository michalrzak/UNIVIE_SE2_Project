package at.ac.univie.se2.ws21.team0404.app.ui.transactions;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableTransaction;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.Transaction;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class TransactionNew extends ATransactionActivity {

  @Override
  void setup() { // empty implementation as this activity does not need to set any values to the views
  }

  @Override
  protected void saveButtonPressed() {
    Transaction newTransaction = getTransactionFromForm();

    assert (owner != null);

    try {
      Repository.getInstance().createTransaction(owner, newTransaction);
      Log.d("TransactionNew", "Successfully added transaction");
      finish();
    } catch (DataDoesNotExistException e) {
      Log.e("TransactionNew",
          "Tried to add transaction to an account which does not exist. Message: " + e
              .getMessage());
      Toast.makeText(this, "Error on saving transaction try again please.",
          Toast.LENGTH_LONG)
          .show();
      finish();
    } catch (DataExistsException e) {
      Log.e("TransactionNew",
          "Tried to add transaction, but transaction with this ID already exists. This should not happen. Message"
              + e.getMessage());
      Toast
          .makeText(this, "Error on saving transaction, try again please.",
              Toast.LENGTH_LONG)
          .show();
      // this does not need to finnish as the account seems to be still valid
    }
  }
}
