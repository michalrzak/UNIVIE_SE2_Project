package at.ac.univie.se2.ws21.team0404.app.utils;

/**
 * An Enum containing Intent names to be used with putExtra() and getStringExtra() methods of
 * Intent.
 */
public enum EIntents {
  CATEGORY("Category"), TRANSACTION("Transaction"), ACCOUNT("Account"), TRANSACTION_ID(
      "transaction_id") ,TRANSACTION_DELETED("transaction_deleted"), CHART_TYPE("chart_type"),
   TRANSACTION_TYPE("transaction_type"), START_DATE("start_date"), END_DATE("end_date");

  private final String name;

  EIntents(@NonNull String name) {
    this.name = name;
  }

  @NonNull
  @Override
  public String toString() {
    return name;
  }
}
