package at.ac.univie.se2.ws21.team0404.app.model.common;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

/**
 * Enum, used to save the transaction type.
 */
public enum ETransactionType {
  INCOME("Income", 1), EXPENSE("Expense", -1);

  private final String type;
  private final int sign;

  ETransactionType(@NonNull String type, int sign) {
    this.type = type;
    this.sign = sign;
  }

  public int getSign() {
    return sign;
  }

  @NonNull
  @Override
  public String toString() {
    return type;
  }
}
