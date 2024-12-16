package com.alexgunich.cargo.product.domain.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents the brand of a product with validation constraints.
 */
public record ProductBrand(String value) {

  /**
   * Constructs a ProductBrand instance.
   * Validates that the provided value is not null and has a minimum length of 3 characters.
   *
   * @param value the brand name of the product
   * @throws IllegalArgumentException if the value is null or its length is less than 3
   */
  public ProductBrand {
    Assert.field("value", value).notNull().minLength(3);
  }
}
