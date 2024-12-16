package com.alexgunich.cargo.order.domain.order.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Value object representing the quantity of an order.
 *
 * <p>The {@link OrderQuantity} class encapsulates the quantity of an ordered product,
 * ensuring that the quantity is positive (greater than zero).</p>
 */
public record OrderQuantity(long value) {

  /**
   * Constructor that validates the quantity value.
   *
   * @param value the quantity of the product in the order.
   * @throws IllegalArgumentException if the quantity value is not positive (i.e., less than or equal to zero).
   */
  public OrderQuantity {
    Assert.field("value", value).positive();
  }
}
