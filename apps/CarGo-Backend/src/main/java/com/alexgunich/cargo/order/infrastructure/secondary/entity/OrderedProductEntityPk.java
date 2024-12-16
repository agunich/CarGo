package com.alexgunich.cargo.order.infrastructure.secondary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.jilt.Builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Composite primary key class for the OrderedProduct entity.
 * This class represents a unique identifier for an ordered product, consisting of a reference to an order
 * and the product's public ID.
 * The key is used in conjunction with the ordered product entity to ensure uniqueness of an ordered product
 * in a given order.
 */
@Embeddable
@Builder
public class OrderedProductEntityPk implements Serializable {

  /**
   * The order associated with the ordered product.
   * This is a foreign key referencing the OrderEntity.
   */
  @ManyToOne
  @JoinColumn(name = "fk_order", nullable = false)
  private OrderEntity order;

  /**
   * The product's public ID associated with the ordered product.
   * This is a unique identifier for the product.
   */
  @Column(name = "fk_product", nullable = false)
  private UUID productPublicId;

  /**
   * Default constructor required by JPA.
   */
  public OrderedProductEntityPk() {
  }

  /**
   * Constructor to create a composite primary key with the specified order and product public ID.
   *
   * @param order          the order associated with the ordered product
   * @param productPublicId the unique identifier for the product
   */
  public OrderedProductEntityPk(OrderEntity order, UUID productPublicId) {
    this.order = order;
    this.productPublicId = productPublicId;
  }

  /**
   * Gets the order associated with the ordered product.
   *
   * @return the order associated with the ordered product
   */
  public OrderEntity getOrder() {
    return order;
  }

  /**
   * Sets the order associated with the ordered product.
   *
   * @param order the order associated with the ordered product
   */
  public void setOrder(OrderEntity order) {
    this.order = order;
  }

  /**
   * Gets the product's public ID associated with the ordered product.
   *
   * @return the product's public ID
   */
  public UUID getProductPublicId() {
    return productPublicId;
  }

  /**
   * Sets the product's public ID associated with the ordered product.
   *
   * @param productPublicId the unique identifier for the product
   */
  public void setProductPublicId(UUID productPublicId) {
    this.productPublicId = productPublicId;
  }

  /**
   * Compares this OrderedProductEntityPk to another object for equality.
   * Two OrderedProductEntityPk objects are considered equal if their product public IDs are the same.
   *
   * @param o the object to compare
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderedProductEntityPk that)) return false;
    return Objects.equals(productPublicId, that.productPublicId);
  }

  /**
   * Returns the hash code for this OrderedProductEntityPk.
   * The hash code is based on the product public ID.
   *
   * @return the hash code for the composite key
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(productPublicId);
  }
}
