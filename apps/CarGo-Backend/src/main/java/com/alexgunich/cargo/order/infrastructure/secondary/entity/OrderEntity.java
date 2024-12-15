package com.alexgunich.cargo.order.infrastructure.secondary.entity;

import com.alexgunich.cargo.order.domain.order.aggregate.Order;
import com.alexgunich.cargo.order.domain.order.aggregate.OrderBuilder;
import com.alexgunich.cargo.order.domain.order.aggregate.OrderedProduct;
import com.alexgunich.cargo.order.domain.order.vo.OrderStatus;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import com.alexgunich.cargo.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Entity representing an Order in the database.
 * This entity contains details of the order, including its public ID, status,
 * stripe session ID, and associated ordered products and user.
 * It is mapped to the "order" table and extends the AbstractAuditingEntity
 * to inherit audit fields (createdAt, updatedAt).
 */
@Entity
@Table(name = "order")
@Builder
public class OrderEntity extends AbstractAuditingEntity<Long> {

  /**
   * The primary key identifier for the Order entity.
   * This ID is auto-generated using a sequence generator.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequenceGenerator")
  @SequenceGenerator(name = "orderSequenceGenerator", sequenceName = "order_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  /**
   * The public unique identifier for the order.
   * This is a UUID value that is unique for each order.
   */
  @Column(name = "public_id", nullable = false, unique = true)
  private UUID publicId;

  /**
   * The current status of the order (e.g., PENDING, COMPLETED).
   */
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private OrderStatus status;

  /**
   * The Stripe session ID associated with this order, used for payment processing.
   */
  @Column(name = "stripe_session_id", nullable = false)
  private String stripeSessionId;

  /**
   * A set of ordered products associated with this order.
   * It is mapped to the OrderedProductEntity and removed when the order is deleted.
   */
  @OneToMany(mappedBy = "id.order", cascade = CascadeType.REMOVE)
  private Set<OrderedProductEntity> orderedProducts = new HashSet<>();

  /**
   * The user who placed the order.
   * This is a many-to-one relationship with the UserEntity.
   */
  @ManyToOne
  @JoinColumn(name = "fk_customer", nullable = false)
  private UserEntity user;

  /**
   * Default constructor required by JPA.
   */
  public OrderEntity() {
  }

  /**
   * Constructor to initialize the OrderEntity with specific values.
   *
   * @param id              the unique ID of the order
   * @param publicId        the public unique identifier for the order
   * @param status          the status of the order
   * @param stripeSessionId the Stripe session ID for payment
   * @param orderedProducts the set of ordered products associated with this order
   * @param user            the user who placed the order
   */
  public OrderEntity(Long id, UUID publicId, OrderStatus status, String stripeSessionId,
                     Set<OrderedProductEntity> orderedProducts, UserEntity user) {
    this.id = id;
    this.publicId = publicId;
    this.status = status;
    this.stripeSessionId = stripeSessionId;
    this.orderedProducts = orderedProducts;
    this.user = user;
  }

  /**
   * Converts a domain Order object into its corresponding OrderEntity representation.
   *
   * @param order the domain order object
   * @return the corresponding OrderEntity
   */
  public static OrderEntity from(Order order) {
    Set<OrderedProductEntity> orderedProductEntities = order.getOrderedProducts()
      .stream().map(OrderedProductEntity::from).collect(Collectors.toSet());

    return OrderEntityBuilder.orderEntity()
      .publicId(order.getPublicId().value())
      .status(order.getStatus())
      .stripeSessionId(order.getStripeId())
      .orderedProducts(orderedProductEntities)
      .user(UserEntity.from(order.getUser()))
      .build();
  }

  /**
   * Converts an OrderEntity into its corresponding domain Order object.
   *
   * @param orderEntity the OrderEntity to convert
   * @return the corresponding domain Order
   */
  public static Order toDomain(OrderEntity orderEntity) {
    Set<OrderedProduct> orderedProducts = orderEntity.getOrderedProducts().stream()
      .map(OrderedProductEntity::toDomain).collect(Collectors.toSet());

    return OrderBuilder.order()
      .publicId(new PublicId(orderEntity.getPublicId()))
      .status(orderEntity.getStatus())
      .stripeId(orderEntity.getStripeSessionId())
      .user(UserEntity.toDomain(orderEntity.getUser()))
      .orderedProducts(orderedProducts.stream().toList())
      .build();
  }

  /**
   * Gets the ID of the order.
   *
   * @return the ID of the order
   */
  @Override
  public Long getId() {
    return id;
  }

  /**
   * Sets the ID of the order.
   *
   * @param id the ID to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets the public ID of the order.
   *
   * @return the public ID of the order
   */
  public UUID getPublicId() {
    return publicId;
  }

  /**
   * Sets the public ID of the order.
   *
   * @param publicId the public ID to set
   */
  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  /**
   * Gets the status of the order.
   *
   * @return the status of the order
   */
  public OrderStatus getStatus() {
    return status;
  }

  /**
   * Sets the status of the order.
   *
   * @param status the status to set
   */
  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  /**
   * Gets the Stripe session ID associated with the order.
   *
   * @return the Stripe session ID
   */
  public String getStripeSessionId() {
    return stripeSessionId;
  }

  /**
   * Sets the Stripe session ID for the order.
   *
   * @param stripeSessionId the Stripe session ID to set
   */
  public void setStripeSessionId(String stripeSessionId) {
    this.stripeSessionId = stripeSessionId;
  }

  /**
   * Gets the set of ordered products associated with the order.
   *
   * @return the set of ordered products
   */
  public Set<OrderedProductEntity> getOrderedProducts() {
    return orderedProducts;
  }

  /**
   * Sets the ordered products for the order.
   *
   * @param orderedProducts the ordered products to set
   */
  public void setOrderedProducts(Set<OrderedProductEntity> orderedProducts) {
    this.orderedProducts = orderedProducts;
  }

  /**
   * Gets the user associated with the order.
   *
   * @return the user who placed the order
   */
  public UserEntity getUser() {
    return user;
  }

  /**
   * Sets the user associated with the order.
   *
   * @param user the user to set
   */
  public void setUser(UserEntity user) {
    this.user = user;
  }

  /**
   * Compares this OrderEntity to another object for equality.
   * Two OrderEntities are considered equal if their public ID, status, and Stripe session ID match.
   *
   * @param o the object to compare
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderEntity order)) return false;
    return Objects.equals(publicId, order.publicId) && status == order.status && Objects.equals(stripeSessionId, order.stripeSessionId);
  }

  /**
   * Returns the hash code for this OrderEntity.
   * The hash code is based on the public ID, status, and Stripe session ID.
   *
   * @return the hash code of the OrderEntity
   */
  @Override
  public int hashCode() {
    return Objects.hash(publicId, status, stripeSessionId);
  }
}
