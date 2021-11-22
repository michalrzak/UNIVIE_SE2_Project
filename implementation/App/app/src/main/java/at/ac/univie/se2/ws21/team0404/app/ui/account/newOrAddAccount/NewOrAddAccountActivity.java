package at.ac.univie.se2.ws21.team0404.app.ui.account.newOrAddAccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.account.ParcelableAppAccount;
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountList.AccountList;
import at.ac.univie.se2.ws21.team0404.app.ui.transactions.transactionlist.TransactionList;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

public class NewOrAddAccountActivity extends AppCompatActivity {

  private Spinner accountTypeSpinner;
  private ArrayAdapter<EAccountType> accountTypeArrayAdapter;
  private EditText accountNameField;

  private AppAccount intentExtraAppAccount;

  private Repository repository;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_or_edit_account);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    accountNameField = findViewById(R.id.account_name_edit_add);

    repository = Repository.getInstance();

    // dropdown with EAccountTypes
    accountTypeSpinner = findViewById(R.id.account_type_spinner);
    accountTypeArrayAdapter = new ArrayAdapter<>(this,
        R.layout.support_simple_spinner_dropdown_item, EAccountType.values());
    accountTypeSpinner.setAdapter(accountTypeArrayAdapter);

    setInputFields();
  }

  // will be refactored later to look better
  private void addOrEditAppAccount() {
    String accountNameValue = accountNameField.getText().toString();
    EAccountType accountType = EAccountType
        .valueOf(accountTypeSpinner.getSelectedItem().toString().toUpperCase());
    try {
      // create new AppAccount
      if (intentExtraAppAccount == null) {
        repository.createAppAccount(new AppAccount(accountNameValue, accountType));
      } else {
        // edit existing AppAccount
        // TODO use finish to go back to the previous activity
        //  (Problem: previous IntentExtra has been modified -> need to use observer in TransactionListActivity to change AppAccount name in the title
        AppAccount newAppAccount = new AppAccount(accountNameValue, accountType,
                intentExtraAppAccount);
        repository.updateAppAccount(intentExtraAppAccount, newAppAccount);

        Intent intent = new Intent(this, TransactionList.class);
        intent.putExtra(EIntents.ACCOUNT.toString(), new ParcelableAppAccount(newAppAccount));
        setResult(Activity.RESULT_OK, intent);
      }
      finish();
    } catch (Exception e) {
      Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
          .show();
    }
  }

  private void setInputFields() {
    intentExtraAppAccount = getIntent().getParcelableExtra(EIntents.ACCOUNT.toString());

    Button button = findViewById(R.id.edit_or_add_account_button);
    button.setOnClickListener(view -> addOrEditAppAccount());

    if (intentExtraAppAccount != null) {
      accountNameField.setText(intentExtraAppAccount.getName());
      accountTypeSpinner
          .setSelection(accountTypeArrayAdapter.getPosition(intentExtraAppAccount.getType()));
      button.setText(R.string.edit_button);
    } else {
      button.setText(R.string.add_button);
    }
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
    } else {
      // when pressing back button
      finish();
    }
    return true;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    if (intentExtraAppAccount != null) {
      getMenuInflater().inflate(R.menu.delete_menu_icon, menu);
    }
    return true;
  }
}
