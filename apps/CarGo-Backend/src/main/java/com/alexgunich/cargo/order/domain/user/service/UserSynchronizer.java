package com.alexgunich.cargo.order.domain.user.service;

import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.order.domain.user.repository.UserRepository;
import com.alexgunich.cargo.order.domain.user.vo.UserAddressToUpdate;
import com.alexgunich.cargo.order.infrastructure.secondary.service.kinde.KindeService;
import com.alexgunich.cargo.shared.authentication.application.AuthenticatedUser;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service class responsible for synchronizing user data with the Identity Provider (IdP).
 *
 * <p>This service retrieves user information from the IdP (e.g., Kinde), compares it with the existing data in the
 * system, and updates the user's profile if necessary.</p>
 */
public class UserSynchronizer {

  private final UserRepository userRepository;
  private final KindeService kindeService;

  private static final String UPDATE_AT_KEY = "last_signed_in";

  /**
   * Constructs a {@link UserSynchronizer} instance.
   *
   * @param userRepository the repository to store and retrieve user data.
   * @param kindeService the service that communicates with the Identity Provider (IdP).
   */
  public UserSynchronizer(UserRepository userRepository, KindeService kindeService) {
    this.userRepository = userRepository;
    this.kindeService = kindeService;
  }

  /**
   * Synchronizes the user data with the Identity Provider (IdP).
   *
   * <p>This method checks whether the user exists in the system based on their email. If the user exists, it compares
   * the modification timestamp from the IdP with the last modification date in the system to decide whether an update is needed.
   * If the user does not exist, they are created.</p>
   *
   * @param jwtToken the JWT token containing the user's claims, including the user information from the IdP.
   * @param forceResync a flag indicating whether to force synchronization even if the data has not changed.
   */
  public void syncWithIdp(Jwt jwtToken, boolean forceResync) {
    Map<String, Object> claims = jwtToken.getClaims();
    List<String> rolesFromToken = AuthenticatedUser.extractRolesFromToken(jwtToken);
    Map<String, Object> userInfo = kindeService.getUserInfo(claims.get("sub").toString());
    User user = User.fromTokenAttributes(userInfo, rolesFromToken);

    Optional<User> existingUser = userRepository.getOneByEmail(user.getEmail());
    if (existingUser.isPresent()) {
      if (claims.get(UPDATE_AT_KEY) != null) {
        Instant lastModifiedDate = existingUser.orElseThrow().getLastModifiedDate();
        Instant idpModifiedDate = Instant.ofEpochSecond((Integer) claims.get(UPDATE_AT_KEY));

        // Only update if the IdP modification date is later or if forceResync is true
        if (idpModifiedDate.isAfter(lastModifiedDate) || forceResync) {
          updateUser(user, existingUser.get());
        }
      }
    } else {
      // Create a new user if they do not exist in the system
      user.initFieldForSignup();
      userRepository.save(user);
    }
  }

  /**
   * Updates an existing user with new information from the IdP.
   *
   * @param user the new user information from the IdP.
   * @param existingUser the existing user in the system that needs to be updated.
   */
  private void updateUser(User user, User existingUser) {
    existingUser.updateFromUser(user);
    userRepository.save(existingUser);
  }

  /**
   * Updates the user's address in the system.
   *
   * @param userAddressToUpdate the user address to be updated.
   */
  public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
    userRepository.updateAddress(userAddressToUpdate.userPublicId(), userAddressToUpdate);
  }
}
