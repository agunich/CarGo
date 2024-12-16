package com.alexgunich.cargo.shared.error.domain;

import java.time.Instant;

/**
 * Exception thrown when a time value is not before a specified time.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a given time does not meet the required condition of being before another
 * specified time. It provides builder methods for constructing detailed
 * exception messages based on the context of the error.
 * </p>
 */
public final class NotBeforeTimeException extends AssertionException {

  private NotBeforeTimeException(String field, String message) {
    super(field, message);
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating the time is not before
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NOT_BEFORE_TIME;
  }

  /**
   * Creates a builder for an exception indicating that the time must be strictly before a specified value.
   *
   * @return a new instance of {@link NotBeforeTimeExceptionValueBuilder}
   */
  public static NotBeforeTimeExceptionValueBuilder strictlyNotBefore() {
    return new NotBeforeTimeExceptionBuilder("must be strictly before");
  }

  /**
   * Creates a builder for an exception indicating that the time must be before a specified value.
   *
   * @return a new instance of {@link NotBeforeTimeExceptionValueBuilder}
   */
  public static NotBeforeTimeExceptionValueBuilder notBefore() {
    return new NotBeforeTimeExceptionBuilder("must be before");
  }

  /**
   * Builder for constructing a NotBeforeTimeException.
   */
  public static final class NotBeforeTimeExceptionBuilder
    implements NotBeforeTimeExceptionValueBuilder, NotBeforeTimeExceptionFieldBuilder, NotBeforeTimeExceptionOtherBuilder {

    private final String hint;
    private Instant value;
    private String field;
    private Instant other;

    private NotBeforeTimeExceptionBuilder(String hint) {
      this.hint = hint;
    }

    @Override
    public NotBeforeTimeExceptionFieldBuilder value(Instant value) {
      this.value = value;
      return this;
    }

    @Override
    public NotBeforeTimeExceptionOtherBuilder field(String field) {
      this.field = field;
      return this;
    }

    @Override
    public NotBeforeTimeException other(Instant other) {
      this.other = other;
      return build();
    }

    private NotBeforeTimeException build() {
      return new NotBeforeTimeException(field, message());
    }

    private String message() {
      return "Time %s in \"%s\" %s %s but wasn't".formatted(value, field, hint, other);
    }
  }

  /**
   * Interface for the first step of the NotBeforeTimeException builder.
   */
  public interface NotBeforeTimeExceptionValueBuilder {
    NotBeforeTimeExceptionFieldBuilder value(Instant value);
  }

  /**
   * Interface for the second step of the NotBeforeTimeException builder.
   */
  public interface NotBeforeTimeExceptionFieldBuilder {
    NotBeforeTimeExceptionOtherBuilder field(String field);
  }

  /**
   * Interface for the final step of the NotBeforeTimeException builder.
   */
  public interface NotBeforeTimeExceptionOtherBuilder {
    NotBeforeTimeException other(Instant other);
  }
}
