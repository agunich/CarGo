package com.alexgunich.cargo.order.domain.user.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents a user's first name.
 *
 * <p>This class encapsulates the first name of a user and ensures that the first name does not exceed a maximum length of 255 characters.</p>
 */
public record UserFirstname(String value) {

  /**
   * Validates the first name.
   * Ensures that the first name value is not null and its length does not exceed 255 characters.
   *
   * @param value the first name of the user
   * @throws IllegalArgumentException if the value is null or exceeds the maximum allowed length of 255 characters
   */
  public UserFirstname {
    Assert.field("value", value).maxLength(255); // Validates the first name length constraint.
  }
}
