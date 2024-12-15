package com.alexgunich.cargo.order.domain.user.service;

import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.order.domain.user.repository.UserRepository;
import com.alexgunich.cargo.order.domain.user.vo.UserEmail;
import com.alexgunich.cargo.order.domain.user.vo.UserPublicId;

import java.util.Optional;

/**
 * Service class for reading user data from the repository.
 *
 * <p>This service provides methods to retrieve user information based on their email or public ID.</p>
 */
public class UserReader {

  private final UserRepository userRepository;

  /**
   * Constructs a {@link UserReader} instance.
   *
   * @param userRepository the repository to fetch user data from.
   */
  public UserReader(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieves a user by their email address.
   *
   * @param userEmail the email address of the user to retrieve.
   * @return an {@link Optional} containing the user if found, or empty if no user exists with the given email.
   */
  public Optional<User> getByEmail(UserEmail userEmail) {
    return userRepository.getOneByEmail(userEmail);
  }

  /**
   * Retrieves a user by their public ID.
   *
   * @param userPublicId the public ID of the user to retrieve.
   * @return an {@link Optional} containing the user if found, or empty if no user exists with the given public ID.
   */
  public Optional<User> getByPublicId(UserPublicId userPublicId) {
    return userRepository.get(userPublicId);
  }
}
