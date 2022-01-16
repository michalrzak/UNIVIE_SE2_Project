package at.ac.univie.se2.ws21.team0404.app.model.categories;

import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import java.util.Objects;
import java.util.UUID;

public class Category {

  @NonNull
  private final UUID id;
  private final ETransactionType type;
  private String name;
  private boolean disabled = false;

  /**
   * Checks whether the provided name does not validate any of the business rules.
   *
   * @param name the name to be checked
   * @throws IllegalArgumentException thrown if name is not valid
   */
  private static void validateName(@NonNull String name) throws IllegalArgumentException {
    if (name.length() <= 0) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Constructor used for constructor chaining. Sets all required fields.
   *
   * @param id   the id of the category
   * @param type `ETransactionType` of the category
   * @param name the name of the category, must be of length > 0
   */
  public Category(@NonNull UUID id, @NonNull ETransactionType type, @NonNull String name) {
    validateName(name);

    this.id = id;
    this.name = name;
    this.type = type;
  }

  /**
   * Creates a new Category object given a name and type. Checks if the name is valid, which throws
   * an `IllegalArgumentException` if not valid. Autogenerates an ID for this object.
   *
   * @param type `ETransactionType` of the category
   * @param name the name of the category, must be of length > 0
   * @throws IllegalArgumentException thrown if name is not valid
   */
  public Category(@NonNull ETransactionType type, @NonNull String name) {
    this(UUID.randomUUID(), type, name);
  }

  /**
   * Creates a new category and additionally allows for it to be disabled or enabled. Calls
   * `this/2`, which performs validation on the name and throws an `IllegalArgumentException` if the
   * name is not valid.
   *
   * @param type     `ETransactionType` of the category
   * @param name     the name of the category, must be of length > 0
   * @param disabled whether or not the category is disabled (deleted by the user)
   * @throws IllegalArgumentException thrown if name is not valid
   */
  public Category(@NonNull UUID id, @NonNull ETransactionType type, @NonNull String name, boolean disabled) {
    this(id, type, name);

    this.disabled = disabled;
  }

  /**
   * A simple copy constructor.
   *
   * @param category the category to copy.
   */
  public Category(@NonNull Category category) {
    this(category.getId(), category.getType(), category.getName(), category.isDisabled());
  }

  @NonNull
  public UUID getId() {
    return id;
  }

  public ETransactionType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    validateName(name);

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
    if (this == o) {
      return true;
    }
    // use instanceof to allow comparison of superclasses, null is not instanceof Category
    if (!(o instanceof Category)) {
      return false;
    }
    assert (o != null);

    Category category = (Category) o;
    return isDisabled() == category.isDisabled() && getType() == category.getType()
        && getName().equals(category.getName());
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