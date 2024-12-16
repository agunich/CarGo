package com.alexgunich.cargo.shared.authentication.infrastructure.primary;

import com.alexgunich.cargo.shared.authentication.application.AuthenticatedUser;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Converter for transforming a {@link Jwt} into an {@link AbstractAuthenticationToken}.
 * <p>
 * This class implements the {@link Converter} interface to convert JWT tokens
 * into Spring Security authentication tokens, including both standard
 * authorities and resource-specific roles.
 * </p>
 */
public class KindeJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  /**
   * Converts a {@link Jwt} token into an {@link AbstractAuthenticationToken}.
   *
   * @param source the JWT token to convert; must not be null
   * @return an {@link AbstractAuthenticationToken} representing the authenticated user
   */
  @Override
  public AbstractAuthenticationToken convert(@NonNull Jwt source) {
    return new JwtAuthenticationToken(source,
      Stream.concat(new JwtGrantedAuthoritiesConverter().convert(source).stream(), extractResourceRoles(source).stream())
        .collect(Collectors.toSet()));
  }

  /**
   * Extracts resource roles from the given JWT token and converts them into
   * {@link GrantedAuthority} instances.
   *
   * @param jwt the JWT token from which to extract roles
   * @return a collection of {@link GrantedAuthority} representing the user's roles
   */
  private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
    return AuthenticatedUser.extractRolesFromToken(jwt).stream()
      .map(SimpleGrantedAuthority::new)
      .collect(Collectors.toSet());
  }
}
