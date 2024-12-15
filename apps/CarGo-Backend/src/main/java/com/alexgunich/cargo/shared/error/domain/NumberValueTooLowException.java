package com.alexgunich.cargo.shared.error.domain;

import java.util.Map;

/**
 * Exception thrown when a numeric value is below the allowed minimum.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a specified field contains a numeric value that is less than the
 * permitted minimum. It provides a builder for constructing detailed
 * exception messages based on the context of the error.
 * </p>
 */
public final class NumberValueTooLowException extends AssertionException {

  private final String min;  // The minimum allowed value
  private final String value; // The actual value that was too low

  private NumberValueTooLowException(NumberValueTooLowExceptionBuilder builder) {
    super(builder.field, builder.message());
    min = builder.minValue;
    value = builder.value;
  }

  /**
   * Creates a new builder for constructing a NumberValueTooLowException.
   *
   * @return a new instance of {@link NumberValueTooLowExceptionBuilder}
   */
  public static NumberValueTooLowExceptionBuilder builder() {
    return new NumberValueTooLowExceptionBuilder();
  }

  /**
   * Builder class for constructing a NumberValueTooLowException.
   */
  public static class NumberValueTooLowExceptionBuilder {

    private String field;   // The name of the field associated with the error
    private String minValue; // The minimum allowed value
    private String value;    // The actual value that was too low

    public NumberValueTooLowExceptionBuilder field(String field) {
      this.field = field;
      return this;
    }

    public NumberValueTooLowExceptionBuilder minValue(String minValue) {
      this.minValue = minValue;
      return this;
    }

    public NumberValueTooLowExceptionBuilder value(String value) {
      this.value = value;
      return this;
    }

    /**
     * Constructs the error message for the exception.
     *
     * @return the error message indicating the field value is too low
     */
    public String message() {
      return new StringBuilder()
        .append("Value of field \"")
        .append(field)
        .append("\" must be at least ")
        .append(minValue)
        .append(" but was ")
        .append(value)
        .toString();
    }

    /**
     * Builds a new instance of NumberValueTooLowException.
     *
     * @return a newly created NumberValueTooLowException
     */
    public NumberValueTooLowException build() {
      return new NumberValueTooLowException(this);
    }
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating that the number value is too low
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NUMBER_VALUE_TOO_LOW;
  }

  /**
   * Returns additional parameters related to the exception.
   *
   * @return a map containing the minimum allowed value and the actual value
   */
  @Override
