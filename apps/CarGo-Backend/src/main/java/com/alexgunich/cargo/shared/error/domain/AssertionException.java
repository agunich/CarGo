package com.alexgunich.cargo.shared.error.domain;

import java.util.Map;

/**
 * Abstract base class for assertion-related exceptions.
 * <p>
 * This class extends {@link RuntimeException} and serves as a foundation
 * for specific assertion exceptions in the application. It provides a
 * mechanism to represent assertion failures related to specific fields
 * and their corresponding error types.
 * </p>
 */
public abstract class AssertionException extends RuntimeException {

  private final String field;

  /**
   * Constructs a new AssertionException with the specified field and message.
   *
   * @param field the name of the field associated with the assertion failure
   * @param message the detail message for this exception
   */
  protected AssertionException(String field, String message) {
    super(message);
    this.field = field;
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} representing the specific error type
   */
  public abstract AssertionErrorType type();

  /**
   * Returns the name of the field associated with the assertion failure.
   *
   * @return the name of the field
   */
  public String field() {
    return field;
  }

  /**
   * Returns additional parameters related to the assertion exception.
   *
   * @return a map of parameters associated with the exception
   */
  public Map<String, String> parameters() {
    return Map.of();
  }
}
