package at.ac.univie.se2.ws21.team0404.app.model.categories;

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
}