package at.ac.univie.se2.ws21.team0404.app.ui.newOrAddAccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.AppAccount;
import at.ac.univie.se2.ws21.team0404.app.model.account.EAccountType;
import at.ac.univie.se2.ws21.team0404.app.model.account.EIntentExtra;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.AccountList;
import at.ac.univie.se2.ws21.team0404.app.database.TemporaryDB;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.TemporaryIntermediaryActivity;

// Part of the code here is temporary. It will be later moved into the Presenter Class

public class NewOrAddAccountActivity extends AppCompatActivity {

    private Spinner accountTypeSpinner;
    private ArrayAdapter<EAccountType> accountTypeArrayAdapter;

    private String intentExtraAccountName;
    private EditText accountNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_or_edit_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accountNameField = findViewById(R.id.account_name_edit_add);

        // dropdown with EAccountTypes
        accountTypeSpinner = findViewById(R.id.account_type_spinner);
        accountTypeArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, EAccountType.values());
        accountTypeSpinner.setAdapter(accountTypeArrayAdapter);

        Button button = findViewById(R.id.edit_or_add_account_button);
        button.setOnClickListener(view -> addAppAccount());

        setFields();
    }

    // will be refactored later to look better
    private void addAppAccount() {
        String accountNameValue = accountNameField.getText().toString();
        if (accountNameValue.length() == 0) {
            Toast.makeText(getApplicationContext(), "Account name is invalid", Toast.LENGTH_SHORT).show();
        } else {
            EAccountType accountType = EAccountType.valueOf(accountTypeSpinner.getSelectedItem().toString().toUpperCase());
            // create new AppAccount
            if (intentExtraAccountName == null) {
                TemporaryDB.addAppAccount(new AppAccount(accountType, accountNameValue));
                startActivity(new Intent(this, AccountList.class));
            } else {
                // edit existing AppAccount
                AppAccount appAccount = TemporaryDB.getAppAccount(intentExtraAccountName);
                appAccount.setName(accountNameValue);
                appAccount.setType(accountType);
                Intent intent = new Intent(this, TemporaryIntermediaryActivity.class);
                intent.putExtra(EIntentExtra.ACCOUNT_NAME.getValue(), accountNameValue);
                startActivity(intent);
            }
        }
    }

    private void setFields() {
        intentExtraAccountName = getIntent().getStringExtra(EIntentExtra.ACCOUNT_NAME.getValue());
        if (intentExtraAccountName != null) {
            accountNameField.setText(intentExtraAccountName);
            AppAccount appAccount = TemporaryDB.getAppAccount(intentExtraAccountName);
            accountTypeSpinner.setSelection(accountTypeArrayAdapter.getPosition(appAccount.getType()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_menu_icon) {
            TemporaryDB.removeAppAccount(intentExtraAccountName);
            Intent intent = new Intent(this, AccountList.class);
            startActivity(intent);
        } else {
            // when pressing back arrow
            if (intentExtraAccountName == null)
                startActivity(new Intent(this, AccountList.class));
            else {
                Intent intent = new Intent(this, TemporaryIntermediaryActivity.class);
                intent.putExtra(EIntentExtra.ACCOUNT_NAME.getValue(), intentExtraAccountName);
                startActivity(intent);
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (intentExtraAccountName != null)
            getMenuInflater().inflate(R.menu.delete_menu_icon, menu);
        return true;
    }
}
