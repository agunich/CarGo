package com.alexgunich.cargo.shared.error.domain;

import java.util.Map;

/**
 * Exception thrown when a string value exceeds the allowed maximum length.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a specified string field contains a value that is longer than the
 * permitted maximum length. It provides a builder for constructing detailed
 * exception messages based on the context of the error.
 * </p>
 */
public final class StringTooLongException extends AssertionException {

  private final String maxLength;       // The maximum allowed length
  private final String currentLength;    // The actual length of the string

  private StringTooLongException(StringTooLongExceptionBuilder builder) {
    super(builder.field, builder.message());
    maxLength = String.valueOf(builder.maxLength);
    currentLength = String.valueOf(builder.value.length());
  }

  /**
   * Creates a new builder for constructing a StringTooLongException.
   *
   * @return a new instance of {@link StringTooLongExceptionBuilder}
   */
  public static StringTooLongExceptionBuilder builder() {
    return new StringTooLongExceptionBuilder();
  }

  /**
   * Builder class for constructing a StringTooLongException.
   */
  static final class StringTooLongExceptionBuilder {

    private String value;   // The actual string value
    private int maxLength;  // The maximum allowed length
    private String field;   // The name of the field associated with the error

    private StringTooLongExceptionBuilder() {}

    public StringTooLongExceptionBuilder field(String field) {
      this.field = field;
      return this;
    }

    public StringTooLongExceptionBuilder value(String value) {
      this.value = value;
      return this;
    }

    public StringTooLongExceptionBuilder maxLength(int maxLength) {
      this.maxLength = maxLength;
      return this;
    }

    /**
     * Constructs the error message for the exception.
     *
     * @return the error message indicating the string length is too long
     */
    private String message() {
      return "The value \"%s\" in field \"%s\" must be at most %d long but was %d"
        .formatted(value, field, maxLength, value.length());
    }

    /**
     * Builds a new instance of StringTooLongException.
     *
     * @return a newly created StringTooLongException
     */
    public StringTooLongException build() {
      return new StringTooLongException(this);
    }
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating that the string is too long
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.STRING_TOO_LONG;
  }

  /**
   * Returns additional parameters related to the exception.
   *
   * @return a map containing the maximum allowed length and the current length
   */
  @Override
  public Map<String, String> parameters() {
    return Map.of("maxLength", maxLength, "currentLength", currentLength);
  }
}
