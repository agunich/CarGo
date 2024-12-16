package com.alexgunich.cargo.order.infrastructure.secondary.repository;

import com.alexgunich.cargo.order.infrastructure.secondary.entity.OrderedProductEntity;
import com.alexgunich.cargo.order.infrastructure.secondary.entity.OrderedProductEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and performing CRUD operations on the {@link OrderedProductEntity} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing a set of built-in methods for handling persistence
 * operations such as saving, deleting, and querying for {@link OrderedProductEntity} objects.
 * </p>
 * <p>
 * The primary key for the {@link OrderedProductEntity} is a composite key, represented by the {@link OrderedProductEntityPk}.
 * </p>
 *
 * @see JpaRepository
 * @see OrderedProductEntity
 * @see OrderedProductEntityPk
 */
public interface JpaOrderedProductRepository extends JpaRepository<OrderedProductEntity, OrderedProductEntityPk> {

}
