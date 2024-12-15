package com.alexgunich.cargo.order.infrastructure.primary.order;

import com.alexgunich.cargo.product.domain.aggregate.ProductCart;
import com.alexgunich.cargo.product.infrastructure.primary.RestPicture;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

/**
 * A record class representing a product in the shopping cart in the REST API response.
 * It includes the product's name, price, brand, picture, quantity, and public ID.
 */
@Builder
public record RestProductCart(String name,
                              double price,
                              String brand,
                              RestPicture picture,
                              int quantity,
                              UUID publicId) {

  /**
   * Converts a {@link ProductCart} (domain model) to a {@link RestProductCart} (REST API model).
   * This method transforms the product details from the domain model into the format required for the REST API response.
   *
   * @param productCart the {@link ProductCart} object from the domain model
   * @return a corresponding {@link RestProductCart} object in REST API format
   */
  public static RestProductCart from(ProductCart productCart) {
    return RestProductCartBuilder.restProductCart()
      .name(productCart.getName().value()) // Extract the product name
      .price(productCart.getPrice().value()) // Extract the price of the product
      .brand(productCart.getBrand().value()) // Extract the product brand
      .picture(RestPicture.fromDomain(productCart.getPicture())) // Convert product picture to API model
      .publicId(productCart.getPublicId().value()) // Extract the product's public ID
      .build();
  }

  /**
   * Converts a list of {@link ProductCart} (domain models) to a list of {@link RestProductCart} (REST API models).
   *
   * @param productCarts the list of {@link ProductCart} objects from the domain model
   * @return a list of corresponding {@link RestProductCart} objects in REST API format
   */
  public static List<RestProductCart> from(List<ProductCart> productCarts) {
    return productCarts.stream().map(RestProductCart::from).toList();
  }
}
