package com.alexgunich.cargo.shared.error.domain;

import java.time.Instant;

/**
 * Exception thrown when a time value is not after a specified time.
 * <p>
 * This class extends {@link AssertionException} and is used to indicate that
 * a given time does not meet the required condition of being after another
 * specified time. It provides builder methods for constructing detailed
 * exception messages based on the context of the error.
 * </p>
 */
public final class NotAfterTimeException extends AssertionException {

  private NotAfterTimeException(String field, String message) {
    super(field, message);
  }

  /**
   * Returns the type of the assertion error.
   *
   * @return an {@link AssertionErrorType} indicating the time is not after
   */
  @Override
  public AssertionErrorType type() {
    return AssertionErrorType.NOT_AFTER_TIME;
  }

  /**
   * Creates a builder for an exception indicating that the time must be strictly after a specified value.
   *
   * @return a new instance of {@link NotAfterTimeExceptionValueBuilder}
   */
  public static NotAfterTimeExceptionValueBuilder strictlyNotAfter() {
    return new NotAfterTimeExceptionBuilder("must be strictly after");
  }

  /**
   * Creates a builder for an exception indicating that the time must be after a specified value.
   *
   * @return a new instance of {@link NotAfterTimeExceptionValueBuilder}
   */
  public static NotAfterTimeExceptionValueBuilder notAfter() {
    return new NotAfterTimeExceptionBuilder("must be after");
  }

  /**
   * Builder for constructing a NotAfterTimeException.
   */
  public static final class NotAfterTimeExceptionBuilder
    implements NotAfterTimeExceptionValueBuilder, NotAfterTimeExceptionFieldBuilder, NotAfterTimeExceptionOtherBuilder {

    private final String hint;
    private Instant value;
    private String field;
    private Instant other;

    private NotAfterTimeExceptionBuilder(String hint) {
      this.hint = hint;
    }

    @Override
    public NotAfterTimeExceptionFieldBuilder value(Instant value) {
      this.value = value;
      return this;
    }

    @Override
    public NotAfterTimeExceptionOtherBuilder field(String field) {
      this.field = field;
      return this;
    }

    @Override
    public NotAfterTimeException other(Instant other) {
      this.other = other;
      return build();
    }

    private NotAfterTimeException build() {
      return new NotAfterTimeException(field, message());
    }

    private String message() {
      return "Time %s in \"%s\" %s %s but wasn't".formatted(value, field, hint, other);
    }
  }

  /**
   * Interface for the first step of the NotAfterTimeException builder.
   */
  public interface NotAfterTimeExceptionValueBuilder {
    NotAfterTimeExceptionFieldBuilder value(Instant value);
  }

  /**
   * Interface for the second step of the NotAfterTimeException builder.
   */
  public interface NotAfterTimeExceptionFieldBuilder {
    NotAfterTimeExceptionOtherBuilder field(String field);
  }

  /**
   * Interface for the final step of the NotAfterTimeException builder.
   */
  public interface NotAfterTimeExceptionOtherBuilder {
    NotAfterTimeException other(Instant other);
  }
}
