package com.alexgunich.cargo.order.domain.order.service;

import com.alexgunich.cargo.order.domain.order.aggregate.DetailCartResponse;
import com.alexgunich.cargo.order.domain.order.aggregate.DetailCartResponseBuilder;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.aggregate.ProductCart;

import java.util.List;

/**
 * Service class responsible for reading and constructing details about a cart.
 *
 * <p>The {@link CartReader} class processes a list of {@link Product} objects and
 * converts them into a {@link DetailCartResponse}, which contains the corresponding
 * cart products.</p>
 */
public class CartReader {

  /**
   * Default constructor for {@code CartReader}.
   */
  public CartReader() {
  }

  /**
   * Converts a list of {@link Product} objects into a {@link DetailCartResponse},
   * which contains a list of {@link ProductCart} objects representing the cart's details.
   *
   * @param products the list of {@link Product} objects to be converted into cart details.
   * @return a {@link DetailCartResponse} containing the cart details.
   */
  public DetailCartResponse getDetails(List<Product> products) {
    // Convert each Product into a ProductCart and collect them into a list
    List<ProductCart> cartProducts = products.stream().map(ProductCart::from).toList();

    // Return the constructed DetailCartResponse with the list of ProductCart objects
    return DetailCartResponseBuilder.detailCartResponse().products(cartProducts)
      .build();
  }
}
