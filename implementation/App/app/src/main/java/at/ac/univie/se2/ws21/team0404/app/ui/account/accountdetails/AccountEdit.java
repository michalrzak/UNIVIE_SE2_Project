package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist.AccountList;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist.TransactionList;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class AccountEdit extends AAccountActivity {

  private AppAccount intentExtraAppAccount;

  @Override
  protected void setup() {
    intentExtraAppAccount = getIntent().getParcelableExtra(EIntents.ACCOUNT.toString());
    assert (intentExtraAppAccount != null);
      accountNameField.setText(intentExtraAppAccount.getName());
      accountTypeSpinner
          .setSelection(accountTypeArrayAdapter.getPosition(intentExtraAppAccount.getType()));
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.delete_menu_icon, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.delete_menu_icon) {
      // when pressing delete icon
      try {
        repository.deleteAppAccount(intentExtraAppAccount);
      } catch (DataDoesNotExistException e) {
        Toast.makeText(getApplicationContext(), "This error shouldn't happen", Toast.LENGTH_SHORT)
            .show();
      }
      Intent intent = new Intent(this, AccountList.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void saveButtonPressed() {
    String accountNameValue = accountNameField.getText().toString();
    EAccountType accountType = EAccountType
        .valueOf(accountTypeSpinner.getSelectedItem().toString().toUpperCase());

    try {
      AppAccount newAppAccount = new AppAccount(accountNameValue, accountType,
          intentExtraAppAccount);
      repository.updateAppAccount(newAppAccount);

      Intent intent = new Intent(this, TransactionList.class);
      intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(newAppAccount));
      setResult(Activity.RESULT_OK, intent);
    } catch (DataDoesNotExistException e) {
      Toast.makeText(getApplicationContext(),
          "The account you were trying to delete does not exist. Please try again.",
          Toast.LENGTH_SHORT).show();
    }
    finish();
  }
}
