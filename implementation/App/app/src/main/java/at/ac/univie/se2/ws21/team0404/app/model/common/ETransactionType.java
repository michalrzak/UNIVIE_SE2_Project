package at.ac.univie.se2.ws21.team0404.app.model.common;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

/**
 * Enum, used to save the transaction type.
 */
public enum ETransactionType {
  INCOME("Income"), EXPENSE("Expense");

  private final String type;

  ETransactionType(@NonNull String type) {
    this.type = type;
  }

  @NonNull
  @Override
  public String toString() {
    return type;
  }
}
