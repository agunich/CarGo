package com.alexgunich.cargo.shared.error.domain;

/**
 * Exception thrown when a collection contains a null element.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a specified collection field contains a null element. It provides a
 * constructor that allows specifying the field name, which is included in
 * the exception message for clarity.
 * </p>
 */
public class NullElementInCollectionException extends AssertionException {

  /**
   * Constructs a new NullElementInCollectionException for the specified field.
   *
   * @param field the name of the field associated with the null element in the collection
   */
  public NullElementInCollectionException(String field) {
    super(field, message(field));
  }

  /**
   * Constructs the error message for the exception.
   *
   * @param field the name of the field
   * @return the error message indicating a null element in the collection
   */
  private static String message(String field) {
    return new StringBuilder()
      .append("The field \"")
      .append(field)
      .append("\" contains a null element")
      .toString();
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating a null element in the collection
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NULL_ELEMENT_IN_COLLECTION;
  }
}
