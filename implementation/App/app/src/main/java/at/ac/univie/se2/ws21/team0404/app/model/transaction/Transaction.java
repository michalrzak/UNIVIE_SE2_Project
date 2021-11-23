package at.ac.univie.se2.ws21.team0404.app.model.transaction;


import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;
import java.util.Optional;

/**
 * Class, used to save information about a transaction.
 */
public class Transaction {

  /**
   * Used to assign id's to transactions.
   * <p>
   * TODO: There is a better way for sure and I don't like that this is here. In the future in
   * roomDB we will be able to have an automatic key, however I am unsure how to solve this here
   */
  private static int counter;

  private final int id;

  @Nullable
  private Category category;
  private ETransactionType type;
  private int amount; // in euro cent; TODO: change this to a special money class?

  public Transaction(@Nullable Category category, @NonNull ETransactionType type, int amount) {
    if (!validateAmount(amount)) {
      throw new IllegalArgumentException(
          "amount is of an invalid value"); // TODO: make this own exception?
    }

    this.id = counter++;
    this.category = category;
    this.type = type;

    this.amount = amount;
  }


  /**
   * Allow subclasses to define constructors, which also set the ID
   *
   * @param id       id of the transaction. Needs to be unique in the DB
   * @param category category of the transaction.
   * @param type     type of the transaction.
   * @param amount   amount of the transaction
   */
  protected Transaction(int id, @Nullable Category category, @NonNull ETransactionType type,
      int amount) {
    this.id = id;
    this.category = category;
    this.type = type;

    if (!validateAmount(amount)) {
      throw new IllegalArgumentException(
          "amount is of an invalid value"); // TODO: make this own exception?
    }

    this.amount = amount;
  }

  public Transaction(Transaction transaction) {
    this.amount = transaction.getAmount();
    this.id = transaction.getId();
    this.category = transaction.getRawCategory();
    this.type = transaction.getType();
  }

  /**
   * Function, which validates the amount field. May be moved to special money class if it gets
   * implemented. For now, amount is allowed to be non-negative (so it can be zero). TODO: can
   * amount be zero?
   *
   * @param amount the variable to check
   * @return true if amount is valid, false if amount is invalid
   */
  private static boolean validateAmount(int amount) {
    return amount >= 0;
  }

  /*
   * Getters-section
   */

  public int getId() {
    return id;
  }

  /**
   * Not every transaction needs to have a category assigned. (See FR3 for reference)
   *
   * @return Optional category
   */
  @NonNull
  public Optional<Category> getCategory() {
    if (category == null) {
      return Optional.empty();
    }
    return Optional.of(category);
  }

  public void setCategory(@NonNull Category newCategory) {
    category = newCategory;
  }

  @NonNull
  public ETransactionType getType() {
    return type;
  }

  public void setType(@NonNull ETransactionType newType) {
    type = newType;
  }

  /*
   * Setters section
   */

  public int getAmount() {
    return amount;
  }

  public void setAmount(int newAmount) {
    if (!validateAmount(amount)) {
      throw new IllegalArgumentException(
          "amount is of an invalid value"); // TODO: make this own exception?
    }

    amount = newAmount;
  }

  /**
   * Allow subclasses to access the Category object without the Optional. Makes handling a bit
   * easier
   *
   * @return category, may be null
   */
  @Nullable
  protected Category getRawCategory() {
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
