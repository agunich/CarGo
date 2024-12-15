package com.alexgunich.cargo.shared.error.domain;

import java.util.Map;

/**
 * Exception thrown when a string value is shorter than the allowed minimum length.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a specified string field contains a value that is shorter than the
 * permitted minimum length. It provides a builder for constructing detailed
 * exception messages based on the context of the error.
 * </p>
 */
public final class StringTooShortException extends AssertionException {

  private final String minLength;       // The minimum allowed length
  private final String currentLength;    // The actual length of the string

  private StringTooShortException(StringTooShortExceptionBuilder builder) {
    super(builder.field, builder.message());
    minLength = String.valueOf(builder.minLength);
    currentLength = String.valueOf(builder.value.length());
  }

  /**
   * Creates a new builder for constructing a StringTooShortException.
   *
   * @return a new instance of {@link StringTooShortExceptionBuilder}
   */
  public static StringTooShortExceptionBuilder builder() {
    return new StringTooShortExceptionBuilder();
  }

  /**
   * Builder class for constructing a StringTooShortException.
   */
  static final class StringTooShortExceptionBuilder {

    private String value;   // The actual string value
    private int minLength;  // The minimum allowed length
    private String field;   // The name of the field associated with the error

    private StringTooShortExceptionBuilder() {}

    public StringTooShortExceptionBuilder field(String field) {
      this.field = field;
      return this;
    }

    public StringTooShortExceptionBuilder value(String value) {
      this.value = value;
      return this;
    }

    public StringTooShortExceptionBuilder minLength(int minLength) {
      this.minLength = minLength;
      return this;
    }

    /**
     * Constructs the error message for the exception.
     *
     * @return the error message indicating the string length is too short
     */
    private String message() {
      return "The value \"%s\" in field \"%s\" must be at least %d long but was only %d"
        .formatted(value, field, minLength, value.length());
    }

    /**
     * Builds a new instance of StringTooShortException.
     *
     * @return a newly created StringTooShortException
     */
    public StringTooShortException build() {
      return new StringTooShortException(this);
    }
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating that the string is too short
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.STRING_TOO_SHORT;
  }

  /**
   * Returns additional parameters related to the exception.
   *
   * @return a map containing the minimum allowed length and the current length
   */
  @Override
  public Map<String, String> parameters() {
    return Map.of("minLength", minLength, "currentLength", currentLength);
  }
}
