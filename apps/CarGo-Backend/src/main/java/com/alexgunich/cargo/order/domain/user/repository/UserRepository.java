package com.alexgunich.cargo.order.domain.user.repository;

import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.order.domain.user.vo.UserAddress;
import com.alexgunich.cargo.order.domain.user.vo.UserAddressToUpdate;
import com.alexgunich.cargo.order.domain.user.vo.UserEmail;
import com.alexgunich.cargo.order.domain.user.vo.UserPublicId;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 *
 * <p>This interface provides methods for saving, retrieving, and updating user data.
 * It supports operations such as getting a user by their public ID or email, and updating
 * the user's address information.</p>
 */
public interface UserRepository {

  /**
   * Saves a new or existing user in the repository.
   *
   * @param user the user to be saved.
   */
  void save(User user);

  /**
   * Retrieves a user by their public ID.
   *
   * @param userPublicId the public ID of the user.
   * @return an {@link Optional} containing the user if found, or empty if no user exists with the given public ID.
   */
  Optional<User> get(UserPublicId userPublicId);

  /**
   * Retrieves a user by their email address.
   *
   * @param userEmail the email address of the user.
   * @return an {@link Optional} containing the user if found, or empty if no user exists with the given email.
   */
  Optional<User> getOneByEmail(UserEmail userEmail);

  /**
   * Updates the address information for a specific user.
   *
   * @param userPublicId the public ID of the user whose address is to be updated.
   * @param userAddress the new address information for the user.
   */
  void updateAddress(UserPublicId userPublicId, UserAddressToUpdate userAddress);

}
