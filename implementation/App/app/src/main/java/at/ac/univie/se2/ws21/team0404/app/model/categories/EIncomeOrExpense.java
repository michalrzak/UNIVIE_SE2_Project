package at.ac.univie.se2.ws21.team0404.app.model.categories;

public enum EIncomeOrExpense {
    INCOME("Income"), EXPENSE("Expense");

    private final String type;

    EIncomeOrExpense(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
