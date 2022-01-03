package at.ac.univie.se2.ws21.team0404.app.ui.categories;

import android.view.View;
import android.widget.Toast;

import at.ac.univie.se2.ws21.team0404.app.R;
import at.ac.univie.se2.ws21.team0404.app.database.Repository;
import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.DataExistsException;
import at.ac.univie.se2.ws21.team0404.app.utils.exceptions.UnreachableCodePathReachedException;

public class AddCategoryActivity extends ACategoryActivity {
    @Override
    protected void setup(@Nullable Category category) {
        radioIncome.toggle();
        deleteButton.setVisibility(View.GONE);
        submitButton.setText(R.string.create_category);
    }

    @Override
    protected void submitCategory() {
        ETransactionType type =
                radioIncome.isChecked() ? ETransactionType.INCOME : ETransactionType.EXPENSE;
        String name = editTextCategoryName.getText().toString();
        Category newCategory = new Category(type, name);

        try {
            Repository.getInstance().createCategory(newCategory);
            finish();
        } catch (DataExistsException e) {
            Toast.makeText(getApplicationContext(), "Attempted to add already existing category!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void deleteCategory() {
        throw new UnreachableCodePathReachedException("AddCategoryActivity.deleteCategory()");
    }
}
