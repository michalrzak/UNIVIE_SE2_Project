package org.clemy.androidapps.expense.ui.newtransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.clemy.androidapps.expense.R;
import org.clemy.androidapps.expense.database.Repository;
import org.clemy.androidapps.expense.utils.android.LifecycleHandler;

public class NewTransactionActivity extends AppCompatActivity implements NewTransactionContract.View {
    public static final String INTENT_EXTRA_ACCOUNT_ID = "AccountId";

    private NewTransactionContract.Presenter presenter;
    private LifecycleHandler<NewTransactionActivity> lifecycleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        Intent intent = getIntent();
        Integer accountId = intent.getIntExtra(INTENT_EXTRA_ACCOUNT_ID, 0);
        presenter = new NewTransactionPresenter(Repository.getInstance(), accountId);
        lifecycleHandler = new LifecycleHandler<>(presenter, this);

        findViewById(R.id.button_save).setOnClickListener(view -> {
            final EditText transactionName = findViewById(R.id.transaction_name);
            if (!TextUtils.isEmpty(transactionName.getText())) {
                presenter.clickedSave(transactionName.getText().toString());
            }
            finish();
        });
    }
}