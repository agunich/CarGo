package com.alexgunich.cargo.product.domain.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents the name of a product.
 * <p>
 * This class is a value object that encapsulates the name of a product,
 * ensuring that the name is not null and adheres to specific length constraints.
 * </p>
 *
 * <p>
 * The valid length for a product name is between 3 and 256 characters.
 * </p>
 *
 * @param value the name of the product; must not be null and must be between 3 and 256 characters in length
 * @throws IllegalArgumentException if the value is null or does not meet the length constraints
 */
public record ProductName(String value) {

  public ProductName {
    Assert.notNull("value", value);
    Assert.field("value", value).minLength(3).maxLength(256);
  }
}
