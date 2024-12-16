package com.alexgunich.cargo.order.domain.user.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents the last name of a user.
 *
 * <p>This class encapsulates the user's last name and enforces a length constraint of 255 characters.</p>
 * <p>The constructor ensures that the last name provided is not null and does not exceed 255 characters.</p>
 */
public record UserLastname(String value) {

  /**
   * Constructor that validates the last name value.
   *
   * <p>Validates that the provided last name is not null and that its length does not exceed 255 characters.</p>
   *
   * @param value the last name of the user
   * @throws IllegalArgumentException if the last name is null or exceeds the maximum length of 255 characters
   */
  public UserLastname {
    Assert.field("value", value).maxLength(255); // Ensures the last name is valid and does not exceed 255 characters.
  }
}
