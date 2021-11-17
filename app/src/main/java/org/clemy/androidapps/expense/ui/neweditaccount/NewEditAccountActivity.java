package org.clemy.androidapps.expense.ui.neweditaccount;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.model.Account;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.utils.android.LifecycleHandler;

public class NewEditAccountActivity extends AppCompatActivity implements NewEditAccountContract.View {
    public static final String INTENT_EXTRA_ACCOUNT_ID = "AccountId";

    private NewEditAccountContract.Presenter presenter;
    private LifecycleHandler<NewEditAccountActivity> lifecycleHandler;

    private ArrayAdapter<AccountType> accountTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newedit_account);

        Intent intent = getIntent();
        Integer accountId = null;
        if (intent.hasExtra(INTENT_EXTRA_ACCOUNT_ID)) {
            accountId = intent.getIntExtra(INTENT_EXTRA_ACCOUNT_ID, 0);
        }
        presenter = new NewEditAccountPresenter(Repository.getInstance(), accountId);
        lifecycleHandler = new LifecycleHandler<>(presenter, this);

        findViewById(R.id.button_save).setOnClickListener(view -> {
            final EditText accountName = findViewById(R.id.account_name);
            final Spinner accountTypeSpinner = findViewById(R.id.account_type);
            final EditText overdueLimit = findViewById(R.id.overdue_limit);
            if (!TextUtils.isEmpty(accountName.getText())) {
                Double overdueLimitValue;
                try {
                    overdueLimitValue = Double.parseDouble(overdueLimit.getText().toString());
                } catch (NumberFormatException e) {
                    overdueLimitValue = null;
                }

                presenter.clickedSave(
                        accountName.getText().toString(),
                        (AccountType) accountTypeSpinner.getSelectedItem(),
                        overdueLimitValue);
            }
            finish();
        });
    }

    @Override
    public void showAccountTypes(AccountType[] accountTypes) {
        Spinner accountTypeSpinner = findViewById(R.id.account_type);
        accountTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, accountTypes);
        accountTypeSpinner.setAdapter(accountTypeAdapter);
    }

    @Override
    public void setEditMode() {
        setTitle(R.string.edit_account_title);
        final Button saveButton = findViewById(R.id.button_save);
        saveButton.setText(R.string.save);
    }

    @Override
    public void showAccountData(Account account) {
        final EditText accountName = findViewById(R.id.account_name);
        accountName.setText(account.getName());
        final Spinner accountTypeSpinner = findViewById(R.id.account_type);
        accountTypeSpinner.setSelection(accountTypeAdapter.getPosition(account.getType()));
        final EditText overdueLimit = findViewById(R.id.overdue_limit);
        overdueLimit.setText(account.getOverdueLimitOptional().map(Object::toString).orElse(""));
    }
}
