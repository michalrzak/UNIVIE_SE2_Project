package at.ac.univie.se2.ws21.team0404.app.model.categories;

import java.util.Objects;

public class Category {
    private final EIncomeOrExpense type;
    private String name;
    private boolean disabled = false;

    public Category(EIncomeOrExpense type, String name) {
        // TODO: These checks should be replaced in the future
        if (type == null || name == null) {
            throw new IllegalArgumentException("Null argument in Category constructor!");
        }

        this.name = name;
        this.type = type;
    }

    public EIncomeOrExpense getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // TODO: These checks should be replaced in the future
        if (name == null) {
            throw new IllegalArgumentException("Null argument in Category constructor!");
        }

        this.name = name;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void disable() {
        this.disabled = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return disabled == category.disabled && type == category.type && name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, disabled);
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}