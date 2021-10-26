package org.clemy.androidapps.expense.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;

public class NewAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        final Repository repository = Repository.getInstance();
        findViewById(R.id.button_save).setOnClickListener(view -> {
            final EditText accountName = findViewById(R.id.account_name);
            if (!TextUtils.isEmpty(accountName.getText())) {
                final Account newAccount =
                        new Account(accountName.getText().toString(), AccountType.BANK);
                repository.addAccount(newAccount);
            }
            finish();
        });
    }
}
