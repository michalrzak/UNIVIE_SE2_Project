package at.ac.univie.se2.ws21.team0404.app.model.account;

import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;

/**
 * A class which holds information on one account.
 * <p>
 * This class is not simply called "Account" as there exists another class Account in the android
 * framework and I want to avoid having to deal with this.
 */
public class AppAccount {

  private static double spendingLimitWarningThreshold = 1.2; // Balance < 120% spending limit triggers a warning
  private static int idCounter = 0;
  private static final double BALANCE_DEFAULT = 0.0;
  private static final double SPENDINGLIMIT_DEFAULT = 0.0;

  private final int id;
  private String name;
  private EAccountType type;
  private double spendingLimit;
  private double balance;

  /**
   * Checks whether the provided name is valid.
   *
   * @param name the name to be checked
   * @throws IllegalArgumentException if the provided name was invalid
   */
  private static void validateName(String name) throws IllegalArgumentException {
    if (name.length() <= 0) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Constructs a new AppAccount given all parameters. Validates the name and throws an `IllegalArgumentException`
   * if the name is invalid
   *
   * @param name name of the AppAccount. Must be longer than 0
   * @param type EAccountType of the account.
   * @param id an id to be given to the account
   * @param spendingLimit the spending limit of the account
   * @param balance the balance of the account.
   */
  protected AppAccount(@NonNull String name, @NonNull EAccountType type, int id, double spendingLimit, double balance) {
    validateName(name);
    this.name = name;
    this.type = type;
    this.id = id;
    this.spendingLimit = spendingLimit;
    this.balance = balance;
  }

  /**
   * Constructs a new AppAccount, autogenerating an ID and using a default balance.
   *
   * @param name name of the AppAccount. Must be longer than 0
   * @param type EAccountType of the account.
   * @param spendingLimit the spending limit of the account
   */
  public AppAccount(@NonNull String name, @NonNull EAccountType type, double spendingLimit) {
    this(name, type, idCounter++, spendingLimit, BALANCE_DEFAULT);
  }

  /**
   * Constructs a new AppAccount, autogenerating an ID.
   *
   * @param name name of the AppAccount. Must be longer than 0
   * @param type EAccountType of the account.
   * @param spendingLimit the spending limit of the account
   * @param balance the balance of the account
   */
  public AppAccount(@NonNull String name, @NonNull EAccountType type,
      @NonNull AppAccount oldAccount, double spendingLimit, double balance) {
    this(name, type, oldAccount.getId(), spendingLimit, balance);
  }


  public EAccountType getType() {
    return type;
  }

  public void setType(@NonNull EAccountType newType) {
    type = newType;
  }

  public String getName() {
    return name;
  }

  public void setName(@NonNull String newName) {
    validateName(name);

    name = newName;
  }

  public int getId() {
    return id;
  }

  public double getSpendingLimit() {
    return spendingLimit;
  }

  public void setSpendingLimit(double spendingLimit) {
    this.spendingLimit = spendingLimit;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public ESpendingLevel getSpendingLimitStatus() {
    if (spendingLimit == 0.0) {
      return ESpendingLevel.NONE;
    } else if (balance < spendingLimit) {
      return ESpendingLevel.OVER;
    } else if (balance <= spendingLimit * spendingLimitWarningThreshold) {
      return ESpendingLevel.WARNING;
    } else {
      return ESpendingLevel.NONE;
    }
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AppAccount)) {
      return false;
    }
    AppAccount that = (AppAccount) o;
    return name.equals(that.getName()) && type == that.getType();
  }

}
