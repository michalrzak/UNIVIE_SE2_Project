package org.clemy.androidapps.expense.ui.newaccount;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.model.AccountType;
import org.clemy.androidapps.expense.ui.LifecycleHandler;

public class NewAccountActivity extends AppCompatActivity implements NewAccountContract.View {
    private final NewAccountContract.Presenter presenter = new NewAccountPresenter();
    private final LifecycleHandler<NewAccountActivity> lifecycleHandler =
            new LifecycleHandler<>(presenter, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        findViewById(R.id.button_save).setOnClickListener(view -> {
            final EditText accountName = findViewById(R.id.account_name);
            Spinner accountTypeSpinner = findViewById(R.id.account_type);
            final EditText overdueLimit = findViewById(R.id.overdue_limit);
            if (!TextUtils.isEmpty(accountName.getText())) {
                Double overdueLimitValue = null;
                try {
                    overdueLimitValue = Double.parseDouble(overdueLimit.getText().toString());
                } catch (NumberFormatException e) {
                    overdueLimitValue = null;
                }

                presenter.createNewAccount(
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
        ArrayAdapter<AccountType> accountTypeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, accountTypes);
        accountTypeSpinner.setAdapter(accountTypeAdapter);
    }
}
