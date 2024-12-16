package com.alexgunich.cargo.product.domain.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

import java.util.UUID;

/**
 * Represents a public identifier for a product.
 * <p>
 * This class is a value object that encapsulates a UUID, ensuring that
 * the identifier is not null.
 * </p>
 *
 * @param value the UUID representing the public identifier; must not be null
 * @throws IllegalArgumentException if the value is null
 */
public record PublicId(UUID value) {

  public PublicId {
    Assert.notNull("value", value);
  }
}
