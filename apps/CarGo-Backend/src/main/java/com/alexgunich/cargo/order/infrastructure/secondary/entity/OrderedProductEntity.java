package com.alexgunich.cargo.order.infrastructure.secondary.entity;

import com.alexgunich.cargo.order.domain.order.aggregate.OrderedProduct;
import com.alexgunich.cargo.order.domain.order.aggregate.OrderedProductBuilder;
import com.alexgunich.cargo.order.domain.order.vo.OrderPrice;
import com.alexgunich.cargo.order.domain.order.vo.OrderQuantity;
import com.alexgunich.cargo.order.domain.order.vo.ProductPublicId;
import com.alexgunich.cargo.product.domain.vo.ProductName;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.jilt.Builder;

import java.util.List;
import java.util.Objects;

/**
 * Entity class representing an ordered product in the system.
 * This class is mapped to the "ordered_product" table in the database.
 */
@Entity
@Table(name = "ordered_product")
@Builder
public class OrderedProductEntity {

  /**
   * Composite primary key for the ordered product entity.
   */
  @EmbeddedId
  private OrderedProductEntityPk id;

  /**
   * The price of the ordered product.
   */
  @Column(name = "price", nullable = false)
  private Double price;

  /**
   * The quantity of the ordered product.
   */
  @Column(name = "quantity", nullable = false)
  private long quantity;

  /**
   * The name of the ordered product.
   */
  @Column(name = "product_name", nullable = false)
  private String productName;

  /**
   * Default constructor for JPA.
   */
  public OrderedProductEntity() {
  }

  /**
   * Constructor to create an OrderedProductEntity with the specified fields.
   *
   * @param id          the composite key
   * @param price       the price of the ordered product
   * @param quantity    the quantity of the ordered product
   * @param productName the name of the ordered product
   */
  public OrderedProductEntity(OrderedProductEntityPk id, Double price, long quantity, String productName) {
    this.id = id;
    this.price = price;
    this.quantity = quantity;
    this.productName = productName;
  }

  /**
   * Converts an OrderedProduct domain object into an OrderedProductEntity.
   *
   * @param orderedProduct the OrderedProduct domain object
   * @return the corresponding OrderedProductEntity object
   */
  public static OrderedProductEntity from(OrderedProduct orderedProduct) {
    OrderedProductEntityPk compositeId = OrderedProductEntityPkBuilder.orderedProductEntityPk()
      .productPublicId(orderedProduct.getProductPublicId().value())
      .build();

    return OrderedProductEntityBuilder.orderedProductEntity()
      .price(orderedProduct.getPrice().value())
      .quantity(orderedProduct.getQuantity().value())
      .productName(orderedProduct.getProductName().value())
      .id(compositeId)
      .build();
  }

  /**
   * Converts a list of OrderedProduct domain objects into a list of OrderedProductEntity objects.
   *
   * @param orderedProducts the list of OrderedProduct domain objects
   * @return the corresponding list of OrderedProductEntity objects
   */
  public static List<OrderedProductEntity> from(List<OrderedProduct> orderedProducts) {
    return orderedProducts.stream().map(OrderedProductEntity::from).toList();
  }

  /**
   * Converts an OrderedProductEntity into an OrderedProduct domain object.
   *
   * @param orderedProductEntity the OrderedProductEntity object
   * @return the corresponding OrderedProduct domain object
   */
  public static OrderedProduct toDomain(OrderedProductEntity orderedProductEntity) {
    return OrderedProductBuilder.orderedProduct()
      .productPublicId(new ProductPublicId(orderedProductEntity.getId().getProductPublicId()))
      .quantity(new OrderQuantity(orderedProductEntity.getQuantity()))
      .price(new OrderPrice(orderedProductEntity.getPrice()))
      .productName(new ProductName(orderedProductEntity.getProductName()))
      .build();
  }

  /**
   * Gets the composite ID of the ordered product entity.
   *
   * @return the composite ID
   */
  public OrderedProductEntityPk getId() {
    return id;
  }

  /**
   * Sets the composite ID of the ordered product entity.
   *
   * @param id the composite ID
   */
  public void setId(OrderedProductEntityPk id) {
    this.id = id;
  }

  /**
   * Gets the price of the ordered product.
   *
   * @return the price of the ordered product
   */
  public Double getPrice() {
    return price;
  }

  /**
   * Sets the price of the ordered product.
   *
   * @param price the price of the ordered product
   */
  public void setPrice(Double price) {
    this.price = price;
  }

  /**
   * Gets the quantity of the ordered product.
   *
   * @return the quantity of the ordered product
   */
  public long getQuantity() {
    return quantity;
  }

  /**
   * Sets the quantity of the ordered product.
   *
   * @param quantity the quantity of the ordered product
   */
  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets the name of the ordered product.
   *
   * @return the name of the ordered product
   */
  public String getProductName() {
    return productName;
  }

  /**
   * Sets the name of the ordered product.
   *
   * @param productName the name of the ordered product
   */
  public void setProductName(String productName) {
    this.productName = productName;
  }

  /**
   * Checks if two OrderedProductEntity objects are equal.
   * Two entities are considered equal if their price, quantity, and product name are the same.
   *
   * @param o the object to compare
   * @return true if the entities are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderedProductEntity that)) return false;
    return quantity == that.quantity && Objects.equals(price, that.price) && Objects.equals(productName, that.productName);
  }

  /**
   * Returns a hash code for the OrderedProductEntity object.
   * The hash code is based on the price, quantity, and product name.
   *
   * @return the hash code of the entity
   */
  @Override
  public int hashCode() {
    return Objects.hash(price, quantity, productName);
  }
}
