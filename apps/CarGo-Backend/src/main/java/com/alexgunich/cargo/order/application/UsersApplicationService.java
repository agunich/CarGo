package com.alexgunich.cargo.order.application;

import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.order.domain.user.repository.UserRepository;
import com.alexgunich.cargo.order.domain.user.service.UserReader;
import com.alexgunich.cargo.order.domain.user.service.UserSynchronizer;
import com.alexgunich.cargo.order.domain.user.vo.UserAddressToUpdate;
import com.alexgunich.cargo.order.domain.user.vo.UserEmail;
import com.alexgunich.cargo.order.infrastructure.secondary.service.kinde.KindeService;
import com.alexgunich.cargo.shared.authentication.application.AuthenticatedUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for user-related operations, including
 * synchronization, retrieval, and updates. It serves as the
 * application layer for managing user information and interactions
 * with external identity providers (IdPs).
 */
@Service
public class UsersApplicationService {

  private final UserSynchronizer userSynchronizer;
  private final UserReader userReader;

  /**
   * Constructs a UsersApplicationService with the required dependencies.
   *
   * @param userRepository the repository for accessing user data.
   * @param kindeService the service for interacting with the Kinde identity provider.
   */
  public UsersApplicationService(UserRepository userRepository, KindeService kindeService) {
    this.userSynchronizer = new UserSynchronizer(userRepository, kindeService);
    this.userReader = new UserReader(userRepository);
  }

  /**
   * Retrieves the authenticated user after optionally synchronizing
   * with the identity provider (IdP).
   *
   * @param jwtToken the JWT token containing user authentication information.
   * @param forceResync a flag indicating whether to force synchronization with the IdP.
   * @return the authenticated {@link User}.
   * @throws RuntimeException if the user cannot be found in the repository after synchronization.
   */
  @Transactional
  public User getAuthenticatedUserWithSync(Jwt jwtToken, boolean forceResync) {
    userSynchronizer.syncWithIdp(jwtToken, forceResync);
    return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
      .orElseThrow();
  }

  /**
   * Retrieves the currently authenticated user without synchronizing
   * with the identity provider (IdP).
   *
   * @return the authenticated {@link User}.
   * @throws RuntimeException if the user cannot be found in the repository.
   */
  @Transactional(readOnly = true)
  public User getAuthenticatedUser() {
    return userReader.getByEmail(new UserEmail(AuthenticatedUser.username().get()))
      .orElseThrow();
  }

  /**
   * Updates the user's address information.
   *
   * @param userAddressToUpdate an object containing the address update details.
   */
  @Transactional
  public void updateAddress(UserAddressToUpdate userAddressToUpdate) {
    userSynchronizer.updateAddress(userAddressToUpdate);
  }

}
