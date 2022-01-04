package at.ac.univie.se2.ws21.team0404.app.ui.account.accountdetails;

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
import at.ac.univie.se2.ws21.team0404.app.ui.account.accountlist.AccountList;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;

/**
 * Special class used to create Account specific activities
 */
public abstract class AAccountActivity extends AppCompatActivity {

  protected Spinner accountTypeSpinner;
  protected ArrayAdapter<EAccountType> accountTypeArrayAdapter;
  protected EditText accountNameField;

  protected Repository repository;

  /**
   * This method gets called at the end of onCreate()
   *
   * Override this to add further setup steps
   */
  protected abstract void setup();

  /**
   * This method gets called once the save button is pressed
   *
   * Override this to define the functionality of the save button
   */
  protected abstract void saveButtonPressed();

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    // when pressing back button
    finish();
    return true;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_account);

    assert(getSupportActionBar() != null);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    accountNameField = findViewById(R.id.account_name_edit_add);

    repository = Repository.getInstance();

    // dropdown with EAccountTypes
    accountTypeSpinner = findViewById(R.id.account_type_spinner);
    accountTypeArrayAdapter = new ArrayAdapter<>(this,
        R.layout.support_simple_spinner_dropdown_item, EAccountType.values());
    accountTypeSpinner.setAdapter(accountTypeArrayAdapter);

    Button button = findViewById(R.id.edit_or_add_account_button);
    button.setOnClickListener(view -> saveButtonPressed());
    button.setText(R.string.save_button);

    setup();
  }
}
