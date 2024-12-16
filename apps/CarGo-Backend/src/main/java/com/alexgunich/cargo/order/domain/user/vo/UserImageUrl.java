package com.alexgunich.cargo.order.domain.user.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;

/**
 * Represents a user's image URL.
 *
 * <p>This class encapsulates the URL of the user's profile image and ensures that the URL does not exceed a maximum length of 1000 characters.</p>
 */
public record UserImageUrl(String value) {

  /**
   * Validates the image URL.
   * Ensures that the URL value is not null and its length does not exceed 1000 characters.
   *
   * @param value the URL of the user's image
   * @throws IllegalArgumentException if the value is null or exceeds the maximum allowed length of 1000 characters
   */
  public UserImageUrl {
    Assert.field("value", value).maxLength(1000); // Validates the image URL length constraint.
  }
}
