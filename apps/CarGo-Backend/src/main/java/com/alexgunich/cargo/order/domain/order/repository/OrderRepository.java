package com.alexgunich.cargo.order.domain.order.repository;

import com.alexgunich.cargo.order.domain.order.aggregate.Order;
import com.alexgunich.cargo.order.domain.order.aggregate.StripeSessionInformation;
import com.alexgunich.cargo.order.domain.order.vo.OrderStatus;
import com.alexgunich.cargo.order.domain.user.vo.UserPublicId;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Defines the repository interface for managing {@link Order} entities.
 *
 * <p>This interface provides methods for saving, updating, and retrieving orders,
 * as well as filtering them by user or pagination.</p>
 */
public interface OrderRepository {

  /**
   * Persists a new order to the repository.
   *
   * @param order the {@link Order} to save.
   */
  void save(Order order);

  /**
   * Updates the status of an order identified by its public ID.
   *
   * @param orderStatus the new {@link OrderStatus} to set.
   * @param orderPublicId the {@link PublicId} of the order to update.
   */
  void updateStatusByPublicId(OrderStatus orderStatus, PublicId orderPublicId);

  /**
   * Finds an order based on the Stripe session information.
   *
   * @param stripeSessionInformation the {@link StripeSessionInformation} containing the Stripe session ID.
   * @return an {@link Optional} containing the matching {@link Order}, or empty if none found.
   */
  Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation);

  /**
   * Retrieves all orders for a specific user, with pagination support.
   *
   * @param userPublicId the {@link UserPublicId} of the user whose orders are to be retrieved.
   * @param pageable the {@link Pageable} object for pagination details.
   * @return a {@link Page} containing the user's orders.
   */
  Page<Order> findAllByUserPublicId(UserPublicId userPublicId, Pageable pageable);

  /**
   * Retrieves all orders in the repository, with pagination support.
   *
   * @param pageable the {@link Pageable} object for pagination details.
   * @return a {@link Page} containing all orders.
   */
  Page<Order> findAll(Pageable pageable);

}
