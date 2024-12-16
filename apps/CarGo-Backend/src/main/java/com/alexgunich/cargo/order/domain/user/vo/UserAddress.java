package com.alexgunich.cargo.order.domain.user.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

/**
 * Value object representing a user's address.
 *
 * <p>This class is used to encapsulate the street, city, zip code, and country of a user's address.
 * It validates that all the fields are not null.</p>
 */
@Builder
public record UserAddress(String street, String city, String zipCode, String country) {

  /**
   * Constructor that ensures the address fields are not null.
   *
   * @param street the street of the user's address.
   * @param city the city of the user's address.
   * @param zipCode the zip code of the user's address.
   * @param country the country of the user's address.
   * @throws IllegalArgumentException if any of the address fields are null.
   */
  public UserAddress {
    Assert.field("street", street).notNull();  // Validates that the street is not null.
    Assert.field("city", city).notNull();      // Validates that the city is not null.
    Assert.field("zipCode", zipCode).notNull(); // Validates that the zip code is not null.
    Assert.field("country", country).notNull(); // Validates that the country is not null.
  }
}
