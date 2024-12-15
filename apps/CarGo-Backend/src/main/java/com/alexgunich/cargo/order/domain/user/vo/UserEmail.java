package com.alexgunich.cargo.order.domain.user.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents a user's email address.
 *
 * <p>This class encapsulates the email address of a user and ensures that the email value does not exceed a maximum length of 255 characters.</p>
 */
public record UserEmail(String value) {

  /**
   * Validates the email address.
   * Ensures that the email value is not null and its length does not exceed 255 characters.
   *
   * @param value the email address associated with the user
   * @throws IllegalArgumentException if the value is null or exceeds the maximum allowed length of 255 characters
   */
  public UserEmail {
    Assert.field("value", value).maxLength(255); // Validates the email length constraint.
  }
}
