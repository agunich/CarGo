package com.alexgunich.cargo.shared.error.domain;

import java.util.Map;

/**
 * Exception thrown when a collection exceeds the allowed maximum size.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a specified collection field contains more elements than the permitted
 * maximum size. It provides a builder for constructing detailed exception
 * messages based on the context of the error.
 * </p>
 */
public class TooManyElementsException extends AssertionException {

  private final String maxSize;       // The maximum allowed size
  private final String currentSize;    // The actual size of the collection

  public TooManyElementsException(TooManyElementsExceptionBuilder builder) {
    super(builder.field, builder.message());
    maxSize = String.valueOf(builder.maxSize);
    currentSize = String.valueOf(builder.size);
  }

  /**
   * Creates a new builder for constructing a TooManyElementsException.
   *
   * @return a new instance of {@link TooManyElementsExceptionBuilder}
   */
  public static TooManyElementsExceptionBuilder builder() {
    return new TooManyElementsExceptionBuilder();
  }

  /**
   * Builder class for constructing a TooManyElementsException.
   */
  public static class TooManyElementsExceptionBuilder {

    private String field;   // The name of the field associated with the error
    private int maxSize;    // The maximum allowed size
    private int size;       // The actual size of the collection

    public TooManyElementsExceptionBuilder field(String field) {
      this.field = field;
      return this;
    }

    public TooManyElementsExceptionBuilder maxSize(int maxSize) {
      this.maxSize = maxSize;
      return this;
    }

    public TooManyElementsExceptionBuilder size(int size) {
      this.size = size;
      return this;
    }

    /**
     * Constructs the error message for the exception.
     *
     * @return the error message indicating the collection size is too large
     */
    private String message() {
      return new StringBuilder()
        .append("Size of collection \"")
        .append(field)
        .append("\" must be at most ")
        .append(maxSize)
        .append(" but was ")
        .append(size)
        .toString();
    }

    /**
     * Builds a new instance of TooManyElementsException.
     *
     * @return a newly created TooManyElementsException
     */
    public TooManyElementsException build() {
      return new TooManyElementsException(this);
    }
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating that there are too many elements
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.TOO_MANY_ELEMENTS;
  }

  /**
   * Returns additional parameters related to the exception.
   *
   * @return a map containing the maximum allowed size and the current size
   */
  @Override
  public Map<String, String> parameters() {
    return Map.of("maxSize", maxSize, "currentSize", currentSize);
  }
}
