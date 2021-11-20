package at.ac.univie.se2.ws21.team0404.app.ui.categories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.categories.EIncomeOrExpense;
import at.ac.univie.se2.ws21.team0404.app.model.categories.ParcelableCategory;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class AddOrEditCategoryActivity extends AppCompatActivity {

    private ParcelableCategory passedCategory;

    private EditText editTextCategoryName;
    private RadioButton radioIncome;
    private RadioButton radioExpense;
    private RadioGroup radioGroup;
    private TextView categoryTypeHintText;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_category);

        editTextCategoryName = findViewById(R.id.editTextCategoryName);
        radioIncome = findViewById(R.id.radioIncome);
        radioExpense = findViewById(R.id.radioExpense);
        radioGroup = findViewById(R.id.radioGroup);
        categoryTypeHintText = findViewById(R.id.categoryTypeHintText);
        deleteButton = findViewById(R.id.deleteButton);

        Intent passedIntent = getIntent();
        passedCategory = (ParcelableCategory) passedIntent.getParcelableExtra(ParcelableCategory.class.getName());

        setup(passedCategory);
    }

    private void setup(Category category) {
        // No category - we are creating a new category and no setup is necessary
        if (category == null) {
            radioIncome.toggle();
            deleteButton.setVisibility(View.GONE);
            return;
        }

        editTextCategoryName.setText(category.getName());
        deleteButton.setVisibility(View.VISIBLE);

        switch (category.getType()) {
            case INCOME:
                radioIncome.toggle();
                break;
            case EXPENSE:
                radioExpense.toggle();
                break;
        }

        radioIncome.setEnabled(false);
        radioExpense.setEnabled(false);
        categoryTypeHintText.setText(getResources().getString(R.string.category_type_cannot_be_changed));
    }

    private void saveCategoryToDb(boolean shouldDeleteCategory) {
        EIncomeOrExpense type = radioIncome.isChecked() ? EIncomeOrExpense.INCOME : EIncomeOrExpense.EXPENSE;
        String name = shouldDeleteCategory
                ? passedCategory.getName()
                : editTextCategoryName.getText().toString();
        Category newCategory = new Category(type, name);
        if (shouldDeleteCategory) newCategory.disable();

        // TODO: Handle more elegantly
        try {
            if (passedCategory == null) {
                Repository.getInstance().getDatabase().addCategory(newCategory);
            } else {
                Repository.getInstance().getDatabase().updateCategory(passedCategory.getName(), newCategory);
            }

            finish();
        } catch (DataExistsException e) {
            Toast.makeText(getApplicationContext(), "Attempted to add already existing category!", Toast.LENGTH_SHORT).show();
        } catch (DataDoesNotExistException e) {
            Toast.makeText(getApplicationContext(), "Attempted to edit a nonexistent category!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onSubmit(View view) {
        saveCategoryToDb(false);
    }

    public void onDelete(View view) {
        saveCategoryToDb(true);
        // TODO: navigate user out of this activity
    }
}