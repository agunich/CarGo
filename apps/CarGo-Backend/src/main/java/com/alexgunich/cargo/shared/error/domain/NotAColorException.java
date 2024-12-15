package com.alexgunich.cargo.shared.error.domain;

/**
 * Exception thrown when a value is not a valid color representation.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a provided value does not conform to the expected format for a color.
 * It can be instantiated with a specific field and message to provide
 * context about the error.
 * </p>
 */
public class NotAColorException extends AssertionException {

  /**
   * Constructs a new NotAColorException with the specified field and message.
   *
   * @param field the name of the field associated with the color validation
   * @param message the detail message for this exception
   */
  public NotAColorException(String field, String message) {
    super(field, message);
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating that a value is not a color
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NOT_A_COLOR;
  }
}
