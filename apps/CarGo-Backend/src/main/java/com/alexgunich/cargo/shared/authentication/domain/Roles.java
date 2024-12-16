package com.alexgunich.cargo.shared.authentication.domain;

import com.alexgunich.cargo.shared.error.domain.Assert;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A record representing a set of roles assigned to a user or entity.
 * <p>
 * This class encapsulates a collection of {@link Role} instances and provides
 * methods to check for roles and manage the collection.
 * </p>
 */
public record Roles(Set<Role> roles) {

  /** An empty Roles instance with no assigned roles. */
  public static final Roles EMPTY = new Roles(null);

  /**
   * Constructs a Roles instance with an unmodifiable set of roles.
   *
   * @param roles the set of roles to assign, must not be null
   */
  public Roles(Set<Role> roles) {
    this.roles = Collections.unmodifiableSet(roles);
  }

  /**
   * Checks if the roles set is not empty.
   *
   * @return true if there are roles assigned, false otherwise
   */
  public boolean hasRole() {
    return !roles.isEmpty();
  }

  /**
   * Checks if a specific role is present in the roles set.
   *
   * @param role the role to check for, must not be null
   * @return true if the role is present, false otherwise
   * @throws IllegalArgumentException if the role is null
   */
  public boolean hasRole(Role role) {
    Assert.notNull("role", role);
    return roles.contains(role);
  }

  /**
   * Returns a stream of roles in the roles set.
   *
   * @return a stream of {@link Role} instances
   */
  public Stream<Role> stream() {
    return get().stream();
  }

  /**
   * Returns the set of roles.
   *
   * @return the unmodifiable set of roles
   */
  public Set<Role> get() {
    return roles();
  }
}
