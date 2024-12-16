package com.alexgunich.cargo.order.domain.order;

/**
 * Exception thrown when there is an error during the payment process for a cart.
 *
 * <p>The {@link CartPaymentException} is a custom runtime exception that can be thrown to indicate
 * a failure in the cart payment process, providing a descriptive error message.</p>
 */
public class CartPaymentException extends RuntimeException {

  /**
   * Constructs a new {@link CartPaymentException} with the specified detail message.
   *
   * @param message the detail message that explains the reason for the exception.
   */
  public CartPaymentException(String message) {
    super(message);
  }
}
