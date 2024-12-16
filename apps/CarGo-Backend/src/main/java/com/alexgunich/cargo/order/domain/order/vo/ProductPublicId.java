package com.alexgunich.cargo.order.domain.order.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

import java.util.UUID;

/**
 * Value object representing the public identifier of a product.
 *
 * <p>The {@link ProductPublicId} class encapsulates a product's public ID, ensuring that the ID is not null.</p>
 */
public record ProductPublicId(UUID value) {

  /**
   * Constructor that validates the public ID value.
   *
   * @param value the public identifier of the product.
   * @throws IllegalArgumentException if the public ID value is null.
   */
  public ProductPublicId {
    Assert.notNull("value", value);
  }
}
