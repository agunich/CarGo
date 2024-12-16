package com.alexgunich.cargo.order.domain.order.aggregate;

import com.alexgunich.cargo.product.domain.aggregate.ProductCart;
import org.jilt.Builder;

import java.util.List;

/**
 * Represents a detailed response for a shopping cart,
 * containing a list of products and their associated details.
 *
 * <p>This class encapsulates the products in the cart and provides
 * getter and setter methods for accessing and modifying the list.</p>
 */
@Builder
public class DetailCartResponse {

  private List<ProductCart> products;

  /**
   * Constructs a new {@code DetailCartResponse} with the specified list of products.
   *
   * @param products a list of {@link ProductCart} representing the products in the cart.
   */
  public DetailCartResponse(List<ProductCart> products) {
    this.products = products;
  }

  /**
   * Retrieves the list of products in the cart.
   *
   * @return a list of {@link ProductCart} objects.
   */
  public List<ProductCart> getProducts() {
    return products;
  }

  /**
   * Sets the list of products in the cart.
   *
   * @param products a list of {@link ProductCart} to set.
   */
  public void setProducts(List<ProductCart> products) {
    this.products = products;
  }
}
