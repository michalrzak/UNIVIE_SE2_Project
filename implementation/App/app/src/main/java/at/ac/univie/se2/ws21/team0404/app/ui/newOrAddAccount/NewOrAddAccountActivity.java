package at.ac.univie.se2.ws21.team0404.app.ui.newOrAddAccount;

import android.content.Intent;
import android.os.Bundle;
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
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.AccountList;
import at.ac.univie.se2.ws21.team0404.app.ui.accountList.TemporaryDB;

// Part of the code here is temporary. It will be later moved into the Presenter Class

public class NewOrAddAccountActivity extends AppCompatActivity {

    Spinner accountTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_or_edit_account);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accountTypeSpinner = findViewById(R.id.account_type_spinner);
        ArrayAdapter<EAccountType> accountTypeArrayAdapter = new ArrayAdapter<EAccountType>(this, R.layout.support_simple_spinner_dropdown_item, EAccountType.values());
        accountTypeSpinner.setAdapter(accountTypeArrayAdapter);

        Button button = findViewById(R.id.edit_or_add_account_button);
        button.setOnClickListener(view -> addAppAccount());
    }

    private void addAppAccount(){
        EditText accountNameText = findViewById(R.id.account_name_edit_add);
        String accountNameValue = accountNameText.getText().toString();
        if (accountNameValue.length() == 0){
            Toast.makeText(getApplicationContext(), "Account name is invalid", Toast.LENGTH_SHORT).show();
        } else {
            TemporaryDB.addAppAccount(new AppAccount(EAccountType.valueOf(accountTypeSpinner.getSelectedItem().toString().toUpperCase()), accountNameValue));
            startActivity(new Intent(this, AccountList.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, AccountList.class));
        return true;
    }
}
