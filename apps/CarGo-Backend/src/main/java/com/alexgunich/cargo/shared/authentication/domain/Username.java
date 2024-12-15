package com.alexgunich.cargo.shared.authentication.domain;

import com.alexgunich.cargo.shared.error.domain.Assert;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * A record representing a username.
 * <p>
 * This class encapsulates a username string and enforces validation rules
 * to ensure that the username is not blank and does not exceed a specified
 * length. It provides methods for retrieval and creation of instances.
 * </p>
 */
public record Username(String username) {

  /**
   * Validates the username during construction.
   *
   * @param username the username to validate; must not be blank and must not exceed 100 characters
   */
  public Username {
    Assert.field("username", username).notBlank().maxLength(100);
  }

  /**
   * Retrieves the username.
   *
   * @return the username as a string
   */
  public String get() {
    return username();
  }

  /**
   * Creates an Optional Username instance from a given string.
   *
   * @param username the username string; can be null or blank
   * @return an Optional containing a Username if the input is valid, otherwise an empty Optional
   */
  public static Optional<Username> of(String username) {
    return Optional.ofNullable(username).filter(StringUtils::isNotBlank).map(Username::new);
  }
}
