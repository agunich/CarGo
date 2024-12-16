package com.alexgunich.cargo.order.infrastructure.primary;

import com.alexgunich.cargo.order.domain.user.aggregate.Authority;
import org.jilt.Builder;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * A record class representing a user's authority (role) in the REST API response.
 * It contains the authority name, which is a string representing the role's name.
 */
@Builder
public record RestAuthority(String name) {

  /**
   * Converts a set of {@link Authority} objects (domain model) into a set of authority names (strings).
   * This method extracts the role names from the domain model and collects them into a set.
   *
   * @param authorities a set of {@link Authority} objects from the domain model
   * @return a set of authority names as strings
   */
  public static Set<String> fromSet(Set<Authority> authorities) {
    return authorities.stream()
      .map(authority -> authority.getName().name())  // Extract the name of each authority
      .collect(Collectors.toSet());  // Collect into a set
  }
}
