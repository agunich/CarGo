package com.alexgunich.cargo.product.infrastructure.secondary.repository;

import com.alexgunich.cargo.product.infrastructure.secondary.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link CategoryEntity} instances.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and
 * custom query methods for category entities stored in the database.
 * </p>
 */
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {

  /**
   * Retrieves a category entity by its public ID.
   *
   * @param publicId the unique public ID of the category
   * @return an {@link Optional} containing the found category entity, or empty if not found
   */
  Optional<CategoryEntity> findByPublicId(UUID publicId);

  /**
   * Deletes a category entity by its public ID.
   *
   * @param publicId the unique public ID of the category to delete
   * @return the number of entities deleted (should be 0 or 1)
   */
  int deleteByPublicId(UUID publicId);
}
