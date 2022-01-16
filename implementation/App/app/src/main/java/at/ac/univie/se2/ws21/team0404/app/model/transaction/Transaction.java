package at.ac.univie.se2.ws21.team0404.app.model.transaction;

import at.ac.univie.se2.ws21.team0404.app.model.categories.Category;
import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import at.ac.univie.se2.ws21.team0404.app.utils.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Class, used to save information about a transaction.
 */
public class Transaction {

  private final UUID id;

  private String name;
  @Nullable
  private Category category;
  private ETransactionType type;
  private int amount; // in euro cent; TODO: change this to a special money class?

  /**
   * Validates the provided name against business rules.
   *
   * @param name name to be validated
   * @throws IllegalArgumentException thrown if name is invalid
   */
  private static void validateName(@NonNull String name) throws IllegalArgumentException {
    if (name.length() <= 0) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Validates the provided amount against business rules.
   *
   * @param amount the amount to be validated
   * @throws IllegalArgumentException thrown if the amount is invalid
   */
  private static void validateAmount(int amount) throws IllegalArgumentException {
    if (amount < 0) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Allow to define constructors, which also set the ID
   *
   * @param id       id of the transaction. Needs to be unique in the DB
   * @param category category of the transaction.
   * @param type     type of the transaction.
   * @param amount   amount of the transaction
   */
  public Transaction(UUID id, @Nullable Category category, @NonNull ETransactionType type,
      int amount, @NonNull String name) {
    validateAmount(amount);
    validateName(name);

    this.id = id;
    this.category = category;
    this.type = type;
    this.name = name;
    this.amount = amount;
  }

  /**
   * Constructs a Transaction and autogenerates an id.
   *
   * @param category category of the transaction.
   * @param type     type of the transaction.
   * @param amount   amount of the transaction
   */
  public Transaction(@Nullable Category category, @NonNull ETransactionType type, int amount,
      @NonNull String name) {
    this(UUID.randomUUID(), category, type, amount, name);
  }

  /**
   * Copy constructor that constructs a Transaction from another.
   *
   * @param transaction transaction to copy.
   */
  public Transaction(@NonNull Transaction transaction) {
    this(transaction.getId(), transaction.getRawCategory(), transaction.getType(),
        transaction.getAmount(), transaction.getName());
  }


  /*
   * Getters-section
   */

  public UUID getId() {
    return id;
  }

  /**
   * Returns the category of the transaction. Not every transaction needs to have a category
   * assigned. (See FR3 for reference)
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

  @NonNull
  public ETransactionType getType() {
    return type;
  }

  public int getAmount() {
    return amount;
  }

  public String getName() {
    return name;
  }


  /*
   * Setters section
   */

  public void setType(@NonNull ETransactionType newType) {
    type = newType;
  }

  public void setCategory(@NonNull Category newCategory) {
    category = newCategory;
  }

  public void setAmount(int newAmount) {
    validateAmount(newAmount);
    amount = newAmount;
  }

  public void setName(@NonNull String name) {
    this.name = name;
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
    return getId().hashCode(); // as id should be unique, it can be used as a hash code
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

    return id == that.getId() || name.equals(that.getName());
  }

  @NonNull
  @Override
  public String toString() {
    return "Transaction{" +
        "id=" + getId() +
        ", name='" + getName() + '\'' +
        ", category=" + getCategory() +
        ", type=" + getType() +
        ", amount=" + getAmount() +
        '}';
  }
}
