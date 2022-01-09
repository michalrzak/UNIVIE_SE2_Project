package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist.AccountList;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class AccountEdit extends AAccountActivity implements IAccountActivityContract.IView {

  private AppAccount intentExtraAppAccount;

  private LifecycleHandler<AccountEdit> lifecycleHandler;
  private final AccountEditPresenter presenter = new AccountEditPresenter(Repository.getInstance());

  @Override
  protected void setup() {
    intentExtraAppAccount = getIntent().getParcelableExtra(EIntents.ACCOUNT.toString());
    assert (intentExtraAppAccount != null);
    accountNameField.setText(intentExtraAppAccount.getName());
    accountTypeSpinner
        .setSelection(accountTypeArrayAdapter.getPosition(intentExtraAppAccount.getType()));

    lifecycleHandler = new LifecycleHandler<>(presenter, this);
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
      presenter.clickedDelete(intentExtraAppAccount);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void saveButtonPressed() {
    String accountNameValue = accountNameField.getText().toString();
    EAccountType accountType = EAccountType
        .valueOf(accountTypeSpinner.getSelectedItem().toString().toUpperCase());

    AppAccount newAppAccount = new AppAccount(accountNameValue, accountType,
        intentExtraAppAccount);
    presenter.clickedSave(newAppAccount);
  }

  @Override
  public void showAccountInsertionSuccessful() {
    finish();
  }

  @Override
  public void showAccountInsertionFailed() {
    Toast.makeText(getApplicationContext(),
        "The account you were trying to delete does not exist. Please try again.",
        Toast.LENGTH_SHORT).show();
    finish();
  }

  @Override
  public void showAccountDeletionSuccessful() {
    finish();
  }

  @Override
  public void showAccountDeletionFailed() {
    Toast.makeText(getApplicationContext(),
        "The account you were trying to delete does not exist. Please try again.",
        Toast.LENGTH_SHORT)
        .show();
    finish();
  }
}
