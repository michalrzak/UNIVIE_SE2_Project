package at.ac.univie.se2.ws21.team0404.app.model.categories;

import at.ac.univie.se2.ws21.team0404.app.model.common.ETransactionType;
import at.ac.univie.se2.ws21.team0404.app.utils.NonNull;
import java.util.Objects;

public class Category {

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

  public Category(@NonNull ETransactionType type, @NonNull String name) {
    validateName(name);

    this.name = name;
    this.type = type;
  }

  public Category(@NonNull ETransactionType type, @NonNull String name, boolean disabled) {
    this(type, name);

    this.disabled = disabled;
  }

  public Category(@NonNull Category category) {
    this(category.getType(), category.getName(), category.isDisabled());
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