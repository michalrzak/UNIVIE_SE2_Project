package at.ac.univie.se2.ws21.team0404.app.ui.accountList;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.account.EIntentExtra;
import at.ac.univie.se2.ws21.team0404.app.ui.newAddDeleteAccount.NewAddDeleteAccount;

// This activity will be later replaced by the transaction list activity. It's here to help add the account-edit functionally
public class TemporaryIntermediaryActivity extends AppCompatActivity {

    private String intentExtraAccountName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        intentExtraAccountName = getIntent().getStringExtra(EIntentExtra.ACCOUNT_NAME.getValue());
        setTitle(intentExtraAccountName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.edit_menu_icon) {
            Intent intent = new Intent(this, NewAddDeleteAccount.class);
            intent.putExtra(EIntentExtra.ACCOUNT_NAME.getValue(), intentExtraAccountName);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AccountList.class);
            startActivity(intent);
        }
        return true;
    }
}
