package com.alexgunich.cargo.order.infrastructure.secondary.repository;

import com.alexgunich.cargo.order.infrastructure.secondary.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link UserEntity} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations along with custom methods for
 * manipulating and retrieving {@link UserEntity} objects.
 * </p>
 * <p>
 * It includes methods for finding users by email or public ID, and updating a user's address.
 * </p>
 *
 * @see JpaRepository
 * @see UserEntity
 */
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

  /**
   * Finds a user by their email address.
   *
   * @param email the email address of the user
   * @return an {@link Optional} containing the user if found, or empty if no user with the specified email exists
   */
  Optional<UserEntity> findByEmail(String email);

  /**
   * Finds users by a list of public IDs.
   *
   * @param publicIds a list of public IDs to search for
   * @return a list of {@link UserEntity} objects that match the provided public IDs
   */
  List<UserEntity> findByPublicIdIn(List<UUID> publicIds);

  /**
   * Finds a user by their public ID.
   *
   * @param publicId the public ID of the user
   * @return an {@link Optional} containing the user if found, or empty if no user with the specified public ID exists
   */
  Optional<UserEntity> findOneByPublicId(UUID publicId);

  /**
   * Updates the address of a user identified by their public ID.
   *
   * @param userPublicId the public ID of the user whose address is to be updated
   * @param street the new street address
   * @param city the new city
   * @param country the new country
   * @param zipCode the new zip code
   */
  @Modifying
  @Query("UPDATE UserEntity user " +
    "SET user.addressStreet = :street, user.addressCity = :city, " +
    " user.addressCountry = :country, user.addressZipCode = :zipCode " +
    "WHERE user.publicId = :userPublicId")
  void updateAddress(UUID userPublicId, String street, String city, String country, String zipCode);
}
