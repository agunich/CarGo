package com.alexgunich.cargo.product.infrastructure.secondary.repository;

import com.alexgunich.cargo.product.infrastructure.secondary.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link PictureEntity} instances.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations for
 * picture entities associated with products in the database.
 * </p>
 */
public interface JpaProductPictureRepository extends JpaRepository<PictureEntity, Long> {
}
