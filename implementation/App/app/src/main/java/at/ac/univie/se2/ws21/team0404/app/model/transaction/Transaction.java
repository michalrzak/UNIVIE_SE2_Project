package at.ac.univie.se2.ws21.team0404.app.model.transaction;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;

/**
 * Class, used to save information about a transaction.
 */
public class Transaction {
    private int id;
    private Category category;

    public Category getCategory() {
        return category;
    }

    @Override
    public int hashCode() {
        return id; // as id should be unique, it can be used as a hash code
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        Transaction that = (Transaction) o;
        return id == that.id;
    }
}
