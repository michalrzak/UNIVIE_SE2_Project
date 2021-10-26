package org.clemy.androidapps.expense.ui.newaccount;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.ui.LifecycleHandler;

public class NewAccountActivity extends AppCompatActivity implements NewAccountContract.View {
    private final NewAccountContract.Presenter presenter = new NewAccountPresenter();
    private final LifecycleHandler<NewAccountActivity> lifecycleHandler =
            new LifecycleHandler<>(presenter, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        final Repository repository = Repository.getInstance();
        findViewById(R.id.button_save).setOnClickListener(view -> {
            final EditText accountName = findViewById(R.id.account_name);
            if (!TextUtils.isEmpty(accountName.getText())) {
                presenter.createNewAccount(accountName.getText().toString());
            }
            finish();
        });
    }
}
