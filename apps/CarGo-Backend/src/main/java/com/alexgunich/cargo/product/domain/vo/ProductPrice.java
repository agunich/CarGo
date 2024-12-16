package com.alexgunich.cargo.product.domain.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents the price of a product.
 * <p>
 * This class is a value object that encapsulates the price of a product,
 * ensuring that the price is a positive value.
 * </p>
 *
 * <p>
 * The minimum valid price for a product is 0.1.
 * </p>
 *
 * @param value the price of the product; must be greater than or equal to 0.1
 * @throws IllegalArgumentException if the value is less than 0.1
 */
public record ProductPrice(double value) {

  public ProductPrice {
    Assert.field("value", value).min(0.1);
  }
}
