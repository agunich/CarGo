package com.alexgunich.cargo.order.domain.order.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Value object representing the price of an order.
 *
 * <p>The {@link OrderPrice} class encapsulates the price of an order with a validation to ensure
 * the price value is strictly positive.</p>
 */
public record OrderPrice(double value) {

  /**
   * Constructor that validates the price value.
   *
   * @param value the price of the order.
   * @throws IllegalArgumentException if the price value is not strictly positive.
   */
  public OrderPrice {
    Assert.field("value", value).strictlyPositive();
  }
}
