package com.alexgunich.cargo.product.domain.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents the name of a category with validation constraints.
 */
public record CategoryName(String value) {

  /**
   * Constructs a CategoryName instance.
   * Validates that the provided value is not null and has a minimum length of 3 characters.
   *
   * @param value the name of the category
   * @throws IllegalArgumentException if the value is null or its length is less than 3
   */
  public CategoryName {
    Assert.field("value", value).notNull().minLength(3);
  }
}
