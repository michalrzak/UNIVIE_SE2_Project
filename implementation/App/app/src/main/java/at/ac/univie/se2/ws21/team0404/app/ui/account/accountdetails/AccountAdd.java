package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.utils.android.LifecycleHandler;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class AccountAdd extends AAccountActivity implements IAccountActivityContract.IView{

  private final AccountAddPresenter presenter = new AccountAddPresenter(Repository.getInstance());

  private LifecycleHandler<AccountAdd> lifecycleHandler;

  @Override
  protected void setup() {
    lifecycleHandler = new LifecycleHandler<>(presenter, this);
  }

  @Override
  protected void saveButtonPressed() {
    String accountNameValue = accountNameField.getText().toString();
    EAccountType accountType = EAccountType
        .valueOf(accountTypeSpinner.getSelectedItem().toString().toUpperCase());

    presenter.clickedSave(new AppAccount(accountNameValue, accountType));

  }

  @Override
  public void showAccountInsertionSuccessful() {
    finish();
  }

  @Override
  public void showAccountInsertionFailed() {
    Toast.makeText(getApplicationContext(), "This account already exists!", Toast.LENGTH_SHORT)
        .show();
  }

  /**
   * Not needed in this View
   */
  @Override
  public void showAccountDeletionSuccessful() {}
  @Override
  public void showAccountDeletionFailed() {}
}
