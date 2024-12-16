package com.alexgunich.cargo.order.infrastructure.secondary.repository;

import com.alexgunich.cargo.order.domain.order.aggregate.Order;
import com.alexgunich.cargo.order.domain.order.aggregate.StripeSessionInformation;
import com.alexgunich.cargo.order.domain.order.repository.OrderRepository;
import com.alexgunich.cargo.order.domain.order.vo.OrderStatus;
import com.alexgunich.cargo.order.domain.user.vo.UserPublicId;
import com.alexgunich.cargo.order.infrastructure.secondary.entity.OrderEntity;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository implementation for managing orders using Spring Data JPA.
 * <p>
 * This class implements the {@link OrderRepository} interface and provides CRUD operations
 * for {@link Order} objects, using {@link JpaOrderRepository} and {@link JpaOrderedProductRepository}.
 * It handles the conversion between domain entities and JPA entities, as well as specific
 * query methods like updating the order status and finding orders by Stripe session ID.
 * </p>
 *
 * @see OrderRepository
 * @see JpaOrderRepository
 * @see JpaOrderedProductRepository
 */
@Repository
public class SpringDataOrderRepository implements OrderRepository {

  private final JpaOrderRepository jpaOrderRepository;
  private final JpaOrderedProductRepository jpaOrderedProductRepository;

  /**
   * Constructs a new {@link SpringDataOrderRepository}.
   *
   * @param jpaOrderRepository the JPA repository for {@link OrderEntity}
   * @param jpaOrderedProductRepository the JPA repository for ordered products
   */
  public SpringDataOrderRepository(JpaOrderRepository jpaOrderRepository,
                                   JpaOrderedProductRepository jpaOrderedProductRepository) {
    this.jpaOrderRepository = jpaOrderRepository;
    this.jpaOrderedProductRepository = jpaOrderedProductRepository;
  }

  /**
   * Saves a new order into the repository.
   * <p>
   * This method converts the provided {@link Order} to an {@link OrderEntity} and saves it
   * to the database. It also saves the associated ordered products.
   * </p>
   *
   * @param order the {@link Order} to be saved
   */
  @Override
  public void save(Order order) {
    OrderEntity orderEntityToCreate = OrderEntity.from(order);
    OrderEntity orderSavedEntity = jpaOrderRepository.save(orderEntityToCreate);

    orderSavedEntity.getOrderedProducts()
      .forEach(orderedProductEntity -> orderedProductEntity.getId().setOrder(orderSavedEntity));
    jpaOrderedProductRepository.saveAll(orderSavedEntity.getOrderedProducts());
  }

  /**
   * Updates the status of an order based on its public ID.
   *
   * @param orderStatus the new status to set for the order
   * @param orderPublicId the public ID of the order whose status is to be updated
   */
  @Override
  public void updateStatusByPublicId(OrderStatus orderStatus, PublicId orderPublicId) {
    jpaOrderRepository.updateStatusByPublicId(orderStatus, orderPublicId.value());
  }

  /**
   * Finds an order by its associated Stripe session ID.
   *
   * @param stripeSessionInformation the Stripe session information containing the session ID
   * @return an {@link Optional} containing the {@link Order} if found, or empty if no order
   *         with the provided Stripe session ID exists
   */
  @Override
  public Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation) {
    return jpaOrderRepository.findByStripeSessionId(stripeSessionInformation.stripeSessionId().value())
      .map(OrderEntity::toDomain);
  }

  /**
   * Finds all orders associated with a specific user, paginated.
   *
   * @param userPublicId the public ID of the user
   * @param pageable the pagination information
   * @return a {@link Page} of {@link Order} objects for the specified user
   */
  @Override
  public Page<Order> findAllByUserPublicId(UserPublicId userPublicId, Pageable pageable) {
    return jpaOrderRepository.findAllByUserPublicId(userPublicId.value(), pageable)
      .map(OrderEntity::toDomain);
  }

  /**
   * Finds all orders, paginated.
   *
   * @param pageable the pagination information
   * @return a {@link Page} of {@link Order} objects
   */
  @Override
  public Page<Order> findAll(Pageable pageable) {
    return jpaOrderRepository.findAll(pageable).map(OrderEntity::toDomain);
  }
}
