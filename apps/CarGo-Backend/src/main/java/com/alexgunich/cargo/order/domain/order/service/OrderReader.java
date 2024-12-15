package com.alexgunich.cargo.order.domain.order.service;

import com.alexgunich.cargo.order.domain.order.aggregate.Order;
import com.alexgunich.cargo.order.domain.order.repository.OrderRepository;
import com.alexgunich.cargo.order.domain.user.vo.UserPublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service class responsible for reading and retrieving orders.
 *
 * <p>The {@link OrderReader} class provides methods for retrieving orders from the
 * repository, either for a specific user or for all orders, with support for pagination.</p>
 */
public class OrderReader {

  private final OrderRepository orderRepository;

  /**
   * Constructs an {@link OrderReader} with the given {@link OrderRepository}.
   *
   * @param orderRepository the repository for managing {@link Order} entities.
   */
  public OrderReader(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  /**
   * Retrieves all orders for a specific user with pagination support.
   *
   * @param userPublicId the {@link UserPublicId} of the user whose orders are to be retrieved.
   * @param pageable the {@link Pageable} object containing pagination details.
   * @return a {@link Page} of {@link Order} objects associated with the given user.
   */
  public Page<Order> findAllByUserPublicId(UserPublicId userPublicId, Pageable pageable) {
    return orderRepository.findAllByUserPublicId(userPublicId, pageable);
  }

  /**
   * Retrieves all orders with pagination support.
   *
   * @param pageable the {@link Pageable} object containing pagination details.
   * @return a {@link Page} of all {@link Order} objects.
   */
  public Page<Order> findAll(Pageable pageable) {
    return orderRepository.findAll(pageable);
  }
}
