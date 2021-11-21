package at.ac.univie.se2.ws21.team0404.app.ui.transactions;


import android.app.Activity;
import android.content.Intent;
import at.ac.univie.se2.ws21.team0404.app.model.transaction.ParcelableTransaction;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;

public class TransactionNew extends ATransactionActivity {
  @Override
  void setup() { // empty implementation as this activity does not need to set any values to the views
  }

  @Override
  protected void saveButtonPressed() {
    Intent intent = new Intent();
    intent.putExtra(EIntents.TRANSACTION.toString(), new ParcelableTransaction(getTransactionFromForm()));
    setResult(Activity.RESULT_OK, intent);
    finish();
  }
}
