package com.alexgunich.cargo.order.domain.order.aggregate;

import com.alexgunich.cargo.order.domain.order.vo.OrderPrice;
import com.alexgunich.cargo.order.domain.order.vo.OrderQuantity;
import com.alexgunich.cargo.order.domain.order.vo.ProductPublicId;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.vo.ProductName;
import org.jilt.Builder;

/**
 * Represents a product included in an order, containing details such as
 * its unique identifier, price, quantity, and name.
 */
@Builder
public class OrderedProduct {

  private final ProductPublicId productPublicId;
  private final OrderPrice price;
  private final OrderQuantity quantity;
  private final ProductName productName;

  /**
   * Constructs an {@code OrderedProduct} with the specified details.
   *
   * @param productPublicId the public identifier of the product.
   * @param price the price of the product in the order.
   * @param quantity the quantity of the product in the order.
   * @param productName the name of the product.
   */
  public OrderedProduct(ProductPublicId productPublicId, OrderPrice price, OrderQuantity quantity, ProductName productName) {
    this.productPublicId = productPublicId;
    this.price = price;
    this.quantity = quantity;
    this.productName = productName;
  }

  /**
   * Retrieves the public identifier of the product.
   *
   * @return the {@link ProductPublicId}.
   */
  public ProductPublicId getProductPublicId() {
    return productPublicId;
  }

  /**
   * Retrieves the price of the product in the order.
   *
   * @return the {@link OrderPrice}.
   */
  public OrderPrice getPrice() {
    return price;
  }

  /**
   * Retrieves the quantity of the product in the order.
   *
   * @return the {@link OrderQuantity}.
   */
  public OrderQuantity getQuantity() {
    return quantity;
  }

  /**
   * Retrieves the name of the product.
   *
   * @return the {@link ProductName}.
   */
  public ProductName getProductName() {
    return productName;
  }

  /**
   * Creates an {@code OrderedProduct} instance from the given product and quantity.
   *
   * <p>This method initializes a new {@code OrderedProduct} using the details of the
   * specified {@link Product} and the quantity to be included in the order.</p>
   *
   * @param quantity the quantity of the product in the order.
   * @param product the {@link Product} to be included in the order.
   * @return a new {@code OrderedProduct} instance.
   */
  public static OrderedProduct create(long quantity, Product product) {
    return OrderedProductBuilder.orderedProduct()
      .price(new OrderPrice(product.getPrice().value()))
      .quantity(new OrderQuantity(quantity))
      .productName(product.getName())
      .productPublicId(new ProductPublicId(product.getPublicId().value()))
      .build();
  }
}
