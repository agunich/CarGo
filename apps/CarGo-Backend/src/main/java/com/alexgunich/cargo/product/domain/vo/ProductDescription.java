package com.alexgunich.cargo.product.domain.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents the description of a product with validation constraints.
 */
public record ProductDescription(String value) {

  /**
   * Constructs a ProductDescription instance.
   * Validates that the provided value is not null and has a minimum length of 10 characters.
   *
   * @param value the description of the product
   * @throws IllegalArgumentException if the value is null or its length is less than 10
   */
  public ProductDescription {
    Assert.field("value", value).notNull().minLength(10);
  }
}
