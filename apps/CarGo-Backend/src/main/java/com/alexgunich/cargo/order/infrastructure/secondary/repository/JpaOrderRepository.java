package com.alexgunich.cargo.order.infrastructure.secondary.repository;

import com.alexgunich.cargo.order.domain.order.vo.OrderStatus;
import com.alexgunich.cargo.order.infrastructure.secondary.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link OrderEntity} entities.
 * <p>
 * This interface extends {@link JpaRepository}, providing standard CRUD operations along with custom methods for
 * manipulating and retrieving {@link OrderEntity} objects.
 * </p>
 * <p>
 * It includes methods for updating an order's status, finding orders by Stripe session ID, and finding orders by a user's
 * public ID, with pagination support.
 * </p>
 *
 * @see JpaRepository
 * @see OrderEntity
 * @see OrderStatus
 */
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {

  /**
   * Updates the status of an order based on its public ID.
   *
   * @param orderStatus the new status of the order
   * @param orderPublicId the public ID of the order whose status is to be updated
   */
  @Modifying
  @Query("UPDATE OrderEntity order SET order.status = :orderStatus WHERE order.publicId = :orderPublicId")
  void updateStatusByPublicId(OrderStatus orderStatus, UUID orderPublicId);

  /**
   * Finds an order by its Stripe session ID.
   *
   * @param stripeSessionId the Stripe session ID of the order
   * @return an {@link Optional} containing the order if found, or empty if not found
   */
  Optional<OrderEntity> findByStripeSessionId(String stripeSessionId);

  /**
   * Finds all orders for a specific user, with pagination support.
   *
   * @param userPublicId the public ID of the user whose orders are to be found
   * @param pageable the pagination information
   * @return a {@link Page} of orders belonging to the specified user
   */
  Page<OrderEntity> findAllByUserPublicId(UUID userPublicId, Pageable pageable);

}
