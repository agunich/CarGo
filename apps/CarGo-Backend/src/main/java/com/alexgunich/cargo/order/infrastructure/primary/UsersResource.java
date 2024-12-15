package com.alexgunich.cargo.order.infrastructure.primary;

import com.alexgunich.cargo.order.application.UsersApplicationService;
import com.alexgunich.cargo.order.domain.user.aggregate.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST controller for managing user-related API endpoints.
 * This includes fetching details about the authenticated user.
 */
@RestController
@RequestMapping("/api/users")
public class UsersResource {

  private final UsersApplicationService usersApplicationService;

  /**
   * Constructs a new UsersResource controller with the given UsersApplicationService.
   *
   * @param usersApplicationService the service responsible for handling user-related operations
   */
  public UsersResource(UsersApplicationService usersApplicationService) {
    this.usersApplicationService = usersApplicationService;
  }

  /**
   * Endpoint to retrieve details of the currently authenticated user.
   * This also includes an optional force resync of the user's data.
   *
   * @param jwtToken the JWT token representing the authenticated user
   * @param forceResync flag indicating whether to force synchronization of user data
   * @return a {@link ResponseEntity} containing the authenticated user's details as a {@link RestUser}
   */
  @GetMapping("/authenticated")
  public ResponseEntity<RestUser> getAuthenticatedUser(@AuthenticationPrincipal Jwt jwtToken,
                                                       @RequestParam boolean forceResync) {
    // Fetch the authenticated user with optional synchronization
    User authenticatedUser = usersApplicationService.getAuthenticatedUserWithSync(jwtToken, forceResync);

    // Convert the domain user to a RestUser
    RestUser restUser = RestUser.from(authenticatedUser);

    // Return the RestUser in the response body
    return ResponseEntity.ok(restUser);
  }
}
