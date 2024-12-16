package com.alexgunich.cargo.shared.authentication.domain;

import com.alexgunich.cargo.shared.error.domain.Assert;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Enum representing the various roles within the application.
 * <p>
 * This enum defines four roles: ADMIN, USER, ANONYMOUS, and UNKNOWN.
 * Each role is prefixed with "ROLE_" for consistency in representation.
 * </p>
 */
public enum Role {
  ADMIN,
  USER,
  ANONYMOUS,
  UNKNOWN;

  private static final String PREFIX = "ROLE_";
  private static final Map<String, Role> ROLES = buildRoles();

  /**
   * Builds a map of role keys to their corresponding Role enum values.
   *
   * @return an unmodifiable map of role keys and their corresponding Role values
   */
  private static Map<String, Role> buildRoles() {
    return Stream.of(values()).collect(Collectors.toUnmodifiableMap(Role::key, Function.identity()));
  }

  /**
   * Returns the key for the role, prefixed with "ROLE_".
   *
   * @return the string representation of the role key
   */
  public String key() {
    return PREFIX + name();
  }

  /**
   * Converts a string representation of a role to its corresponding Role enum value.
   *
   * @param role the string representation of the role
   * @return the corresponding Role enum value, or UNKNOWN if the role is not recognized
   * @throws IllegalArgumentException if the role string is blank
   */
  public static Role from(String role) {
    Assert.notBlank("role", role);
    return ROLES.getOrDefault(role, UNKNOWN);
  }
}
