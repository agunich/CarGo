package com.alexgunich.cargo.order.domain.order.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Value object representing the session ID for a Stripe payment.
 *
 * <p>The {@link StripeSessionId} class encapsulates the session ID used by Stripe for processing payments,
 * ensuring that the session ID value is not null.</p>
 */
public record StripeSessionId(String value) {

  /**
   * Constructor that validates the Stripe session ID.
   *
   * @param value the session ID for the Stripe payment.
   * @throws IllegalArgumentException if the session ID value is null.
   */
  public StripeSessionId {
    Assert.notNull("value", value);
  }
}
