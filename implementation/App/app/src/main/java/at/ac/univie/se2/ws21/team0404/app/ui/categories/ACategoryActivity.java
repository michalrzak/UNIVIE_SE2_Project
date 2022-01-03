package at.ac.univie.se2.ws21.team0404.app.ui.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.model.android.ParcelableCategory;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.utils.EIntents;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

public abstract class ACategoryActivity extends AppCompatActivity {

    protected ParcelableCategory passedCategory;

    protected EditText editTextCategoryName;
    protected RadioButton radioIncome;
    protected RadioButton radioExpense;
    protected TextView categoryTypeHintText;
    protected Button deleteButton;
    protected Button submitButton;

    protected abstract void setup(@Nullable Category category);
    protected abstract void submitCategory();
    protected abstract void deleteCategory();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_category);

        ActionBar actionBar = getSupportActionBar();
        assert (actionBar != null);
        actionBar.setDisplayHomeAsUpEnabled(true);

        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        radioIncome = findViewById(R.id.radioIncome);
        radioExpense = findViewById(R.id.radioExpense);
        categoryTypeHintText = findViewById(R.id.categoryTypeHintText);
        deleteButton = findViewById(R.id.deleteButton);
        submitButton = findViewById(R.id.submitButton);

        Intent passedIntent = getIntent();
        passedCategory = passedIntent
                .getParcelableExtra(EIntents.CATEGORY.toString());

        setup(passedCategory);
    }

    public void onSubmit(View view) {
        submitCategory();
    }

    public void onDelete(View view) {
        deleteCategory();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}
