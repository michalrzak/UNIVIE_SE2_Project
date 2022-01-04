package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

import android.widget.Toast;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class AccountAdd extends AAccountActivity{

  @Override
  protected void setup() {}

  @Override
  protected void saveButtonPressed() {
    String accountNameValue = accountNameField.getText().toString();
    EAccountType accountType = EAccountType
        .valueOf(accountTypeSpinner.getSelectedItem().toString().toUpperCase());

    try {
      repository.createAppAccount(new AppAccount(accountNameValue, accountType));
      finish();
    } catch (DataExistsException e) {
      Toast.makeText(getApplicationContext(), "This account already exists!", Toast.LENGTH_SHORT)
          .show();
    }

  }
}
