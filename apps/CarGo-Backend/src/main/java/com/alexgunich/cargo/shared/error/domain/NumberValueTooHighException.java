package com.alexgunich.cargo.shared.error.domain;

import java.util.Map;

/**
 * Exception thrown when a numeric value exceeds the allowed maximum.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a specified field contains a numeric value that is greater than the
 * permitted maximum. It provides a builder for constructing detailed
 * exception messages based on the context of the error.
 * </p>
 */
public final class NumberValueTooHighException extends AssertionException {

  private final String max;  // The maximum allowed value
  private final String value; // The actual value that was too high

  private NumberValueTooHighException(NumberValueTooHighExceptionBuilder builder) {
    super(builder.field, builder.message());
    max = builder.maxValue;
    value = builder.value;
  }

  /**
   * Creates a new builder for constructing a NumberValueTooHighException.
   *
   * @return a new instance of {@link NumberValueTooHighExceptionBuilder}
   */
  public static NumberValueTooHighExceptionBuilder builder() {
    return new NumberValueTooHighExceptionBuilder();
  }

  /**
   * Builder class for constructing a NumberValueTooHighException.
   */
  public static class NumberValueTooHighExceptionBuilder {

    private String field;   // The name of the field associated with the error
    private String maxValue; // The maximum allowed value
    private String value;    // The actual value that was too high

    public NumberValueTooHighExceptionBuilder field(String field) {
      this.field = field;
      return this;
    }

    public NumberValueTooHighExceptionBuilder maxValue(String maxValue) {
      this.maxValue = maxValue;
      return this;
    }

    public NumberValueTooHighExceptionBuilder value(String value) {
      this.value = value;
      return this;
    }

    /**
     * Constructs the error message for the exception.
     *
     * @return the error message indicating the field value is too high
     */
    public String message() {
      return new StringBuilder()
        .append("Value of field \"")
        .append(field)
        .append("\" must be at most ")
        .append(maxValue)
        .append(" but was ")
        .append(value)
        .toString();
    }

    /**
     * Builds a new instance of NumberValueTooHighException.
     *
     * @return a newly created NumberValueTooHighException
     */
    public NumberValueTooHighException build() {
      return new NumberValueTooHighException(this);
    }
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating that the number value is too high
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NUMBER_VALUE_TOO_HIGH;
  }

  /**
   * Returns additional parameters related to the exception.
   *
   * @return a map containing the maximum allowed value and the actual value
   */
  @Override
  public Map<String, String> parameters() {
    return Map.of("max", max, "value", value);
  }
}
