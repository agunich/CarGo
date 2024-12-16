package com.alexgunich.cargo.order.infrastructure.secondary.repository;

import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.order.domain.user.repository.UserRepository;
import com.alexgunich.cargo.order.domain.user.vo.UserAddressToUpdate;
import com.alexgunich.cargo.order.domain.user.vo.UserEmail;
import com.alexgunich.cargo.order.domain.user.vo.UserPublicId;
import com.alexgunich.cargo.order.infrastructure.secondary.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository implementation for managing users using Spring Data JPA.
 * <p>
 * This class implements the {@link UserRepository} interface and provides CRUD operations
 * for {@link User} objects, using the {@link JpaUserRepository}. It handles saving,
 * retrieving, and updating user data in the underlying database.
 * </p>
 *
 * @see UserRepository
 * @see JpaUserRepository
 */
@Repository
public class SpringDataUserRepository implements UserRepository {

  private final JpaUserRepository jpaUserRepository;

  /**
   * Constructs a new {@link SpringDataUserRepository}.
   *
   * @param jpaUserRepository the JPA repository for {@link UserEntity}
   */
  public SpringDataUserRepository(JpaUserRepository jpaUserRepository) {
    this.jpaUserRepository = jpaUserRepository;
  }

  /**
   * Saves a new user or updates an existing user in the repository.
   * <p>
   * If the user has a valid database ID, the method will attempt to update the existing
   * user in the repository. If no existing user is found, the method will save a new user.
   * If the user does not have a valid database ID, a new user entity will be created and saved.
   * </p>
   *
   * @param user the {@link User} to be saved or updated
   */
  @Override
  public void save(User user) {
    if (user.getDbId() != null) {
      Optional<UserEntity> userToUpdateOpt = jpaUserRepository.findById(user.getDbId());
      if (userToUpdateOpt.isPresent()) {
        UserEntity userToUpdate = userToUpdateOpt.get();
        userToUpdate.updateFromUser(user);
        jpaUserRepository.saveAndFlush(userToUpdate);
      }
    } else {
      jpaUserRepository.save(UserEntity.from(user));
    }
  }

  /**
   * Retrieves a user by their public ID.
   *
   * @param userPublicId the public ID of the user to retrieve
   * @return an {@link Optional} containing the {@link User} if found, or empty if no user
   *         with the provided public ID exists
   */
  @Override
  public Optional<User> get(UserPublicId userPublicId) {
    return jpaUserRepository.findOneByPublicId(userPublicId.value())
      .map(UserEntity::toDomain);
  }

  /**
   * Retrieves a user by their email address.
   *
   * @param userEmail the email address of the user to retrieve
   * @return an {@link Optional} containing the {@link User} if found, or empty if no user
   *         with the provided email exists
   */
  @Override
  public Optional<User> getOneByEmail(UserEmail userEmail) {
    return jpaUserRepository.findByEmail(userEmail.value())
      .map(UserEntity::toDomain);
  }

  /**
   * Updates the address information for a specific user.
   *
   * @param userPublicId the public ID of the user whose address is to be updated
   * @param userAddress the updated address details
   */
  @Override
  public void updateAddress(UserPublicId userPublicId, UserAddressToUpdate userAddress) {
    jpaUserRepository.updateAddress(userPublicId.value(),
      userAddress.userAddress().street(),
      userAddress.userAddress().city(),
      userAddress.userAddress().country(),
      userAddress.userAddress().zipCode());
  }
}
