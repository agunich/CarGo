package com.alexgunich.cargo.shared.error.domain;

/**
 * Exception thrown when a mandatory value is missing.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a required field is either blank, null, or empty. It provides static factory
 * methods to create instances with appropriate messages based on the reason for
 * the missing value.
 * </p>
 */
public final class MissingMandatoryValueException extends AssertionException {

  private MissingMandatoryValueException(String field, String message) {
    super(field, message);
  }

  /**
   * Creates an instance of MissingMandatoryValueException for a blank value.
   *
   * @param field the name of the field that is mandatory
   * @return a new MissingMandatoryValueException instance
   */
  public static MissingMandatoryValueException forBlankValue(String field) {
    return new MissingMandatoryValueException(field, defaultMessage(field, "blank"));
  }

  /**
   * Creates an instance of MissingMandatoryValueException for a null value.
   *
   * @param field the name of the field that is mandatory
   * @return a new MissingMandatoryValueException instance
   */
  public static MissingMandatoryValueException forNullValue(String field) {
    return new MissingMandatoryValueException(field, defaultMessage(field, "null"));
  }

  /**
   * Creates an instance of MissingMandatoryValueException for an empty value.
   *
   * @param field the name of the field that is mandatory
   * @return a new MissingMandatoryValueException instance
   */
  public static MissingMandatoryValueException forEmptyValue(String field) {
    return new MissingMandatoryValueException(field, defaultMessage(field, "empty"));
  }

  /**
   * Constructs a default message for the exception based on the field and reason.
   *
   * @param field the name of the field
   * @param reason the reason for the missing value
   * @return a default error message string
   */
  private static String defaultMessage(String field, String reason) {
    return new StringBuilder()
      .append("The field \"")
      .append(field)
      .append("\" is mandatory and wasn't set")
      .append(" (")
      .append(reason)
      .append(")")
      .toString();
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating a missing mandatory value
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.MISSING_MANDATORY_VALUE;
  }
}
