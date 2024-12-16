package com.alexgunich.cargo.order.domain.user.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Value object representing the name of an authority (role) granted to a user.
 *
 * <p>This class is used to encapsulate the name of the authority assigned to a user. The name is validated to ensure
 * it is not null.</p>
 */
public record AuthorityName(String name) {

  /**
   * Constructor that ensures the authority name is not null.
   *
   * @param name the name of the authority (role).
   * @throws IllegalArgumentException if the name is null.
   */
  public AuthorityName {
    Assert.field("name", name).notNull();  // Validates that the authority name is not null.
  }
}
