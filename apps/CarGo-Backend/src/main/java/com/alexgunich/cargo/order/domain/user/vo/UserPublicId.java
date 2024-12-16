package com.alexgunich.cargo.order.domain.user.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

import java.util.UUID;

/**
 * Represents a unique identifier for a user.
 *
 * <p>This class encapsulates a UUID that is used to uniquely identify a user within the system.</p>
 * <p>The constructor ensures that the UUID provided is not null.</p>
 */
public record UserPublicId(UUID value) {

  /**
   * Constructor that validates the UUID value.
   *
   * <p>Validates that the provided UUID is not null.</p>
   *
   * @param value the unique identifier for the user
   * @throws IllegalArgumentException if the UUID value is null
   */
  public UserPublicId {
    Assert.notNull("value", value); // Ensures that the UUID value is not null.
  }
}
