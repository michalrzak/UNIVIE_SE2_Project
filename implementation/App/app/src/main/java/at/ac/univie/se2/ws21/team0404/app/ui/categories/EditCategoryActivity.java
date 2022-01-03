package at.ac.univie.se2.ws21.team0404.app.ui.categories;

import android.view.View;
import android.widget.Toast;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataDoesNotExistException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;

public class EditCategoryActivity extends ACategoryActivity{
    @Override
    protected void setup(@Nullable Category category) {
        assert(category != null);
        editTextCategoryName.setText(category.getName());
        deleteButton.setVisibility(View.VISIBLE);
        submitButton.setText(R.string.save_category);

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
        categoryTypeHintText
                .setText(getResources().getString(R.string.category_type_cannot_be_changed));
    }

    private void saveCategoryToDb(boolean shouldDeleteCategory) {
        ETransactionType type =
                radioIncome.isChecked() ? ETransactionType.INCOME : ETransactionType.EXPENSE;
        String name = shouldDeleteCategory
                ? passedCategory.getName()
                : editTextCategoryName.getText().toString();
        Category newCategory = new Category(type, name);
        if (shouldDeleteCategory) {
            newCategory.disable();
        }

        try {
            Repository.getInstance().updateCategory(passedCategory.getName(), newCategory);
            finish();
        } catch (DataExistsException e) {
            Toast.makeText(getApplicationContext(), "Attempted to add already existing category!",
                    Toast.LENGTH_SHORT).show();
        } catch (DataDoesNotExistException e) {
            Toast.makeText(getApplicationContext(), "Attempted to edit a nonexistent category!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void submitCategory() {
        saveCategoryToDb(false);
    }

    @Override
    protected void deleteCategory() {
        saveCategoryToDb(true);
    }
}
