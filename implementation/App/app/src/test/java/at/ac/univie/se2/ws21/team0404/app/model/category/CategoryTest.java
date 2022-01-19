package at.ac.univie.se2.ws21.team0404.app.model.category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;

@RunWith(MockitoJUnitRunner.class)
public class CategoryTest {

    @Test
    public void createTwoCategories_idsNotEqual() {
        Category category = new Category(ETransactionType.EXPENSE, "test");
        Category otherCategory = new Category(ETransactionType.INCOME, "test2");
        assertNotEquals(category, otherCategory);
    }

    @Test
    public void disabledCategoryNotEqualToEnabled() {
        Category category = new Category(ETransactionType.EXPENSE, "test");
        Category category2 = new Category(category);

        category.disable();

        assertNotEquals(category, category2);
    }
}
