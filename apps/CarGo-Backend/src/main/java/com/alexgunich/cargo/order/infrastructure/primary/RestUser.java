package com.alexgunich.cargo.order.infrastructure.primary;

import com.alexgunich.cargo.order.domain.user.aggregate.User;
import org.jilt.Builder;

import java.util.Set;
import java.util.UUID;

/**
 * A record class representing a user in the REST API response.
 * It contains the user's public ID, first name, last name, email, image URL, and authorities (roles).
 */
@Builder
public record RestUser(UUID publicId,
                       String firstName,
                       String lastName,
                       String email,
                       String imageUrl,
                       Set<String> authorities) {

  /**
   * Converts a {@link User} domain object into a {@link RestUser} for API response.
   * This method transforms a user from the domain model into a REST-friendly representation,
   * including the user's details and authorities.
   *
   * @param user a {@link User} domain object representing the user
   * @return a {@link RestUser} representing the user in a REST API response
   */
  public static RestUser from(User user) {
    RestUserBuilder restUserBuilder = RestUserBuilder.restUser();

    // Set the image URL if it exists
    if(user.getImageUrl() != null) {
      restUserBuilder.imageUrl(user.getImageUrl().value());
    }

    // Build and return the RestUser object
    return restUserBuilder
      .email(user.getEmail().value())
      .firstName(user.getFirstname().value())
      .lastName(user.getLastname().value())
      .publicId(user.getUserPublicId().value())
      .authorities(RestAuthority.fromSet(user.getAuthorities()))
      .build();
  }
}
