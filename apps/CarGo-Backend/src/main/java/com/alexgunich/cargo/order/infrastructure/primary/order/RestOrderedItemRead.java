package com.alexgunich.cargo.order.infrastructure.primary.order;

import com.alexgunich.cargo.order.domain.order.aggregate.OrderedProduct;
import org.jilt.Builder;

import java.util.List;

/**
 * A record class representing the details of an ordered product in the REST API response.
 * It contains the product's quantity, price, and name.
 */
@Builder
public record RestOrderedItemRead(long quantity,
                                  double price,
                                  String name) {

  /**
   * Converts an {@link OrderedProduct} (domain model) to a {@link RestOrderedItemRead} (REST API model).
   * This method transforms the ordered product details from the domain model into the format required for the REST API response.
   *
   * @param orderedProduct the {@link OrderedProduct} object from the domain model
   * @return a corresponding {@link RestOrderedItemRead} object in REST API format
   */
  public static RestOrderedItemRead from(OrderedProduct orderedProduct) {
    return RestOrderedItemReadBuilder.restOrderedItemRead()
      .name(orderedProduct.getProductName().value()) // Extract the product name from the domain model
      .quantity(orderedProduct.getQuantity().value()) // Extract the quantity from the domain model
      .price(orderedProduct.getPrice().value()) // Extract the price from the domain model
      .build();
  }

  /**
   * Converts a list of {@link OrderedProduct} (domain model) objects to a list of {@link RestOrderedItemRead} (REST API model) objects.
   * This method transforms a collection of ordered products into the corresponding collection of REST API response objects.
   *
   * @param orderedProducts the list of {@link OrderedProduct} objects from the domain model
   * @return a list of corresponding {@link RestOrderedItemRead} objects in REST API format
   */
  public static List<RestOrderedItemRead> from(List<OrderedProduct> orderedProducts) {
    return orderedProducts.stream().map(RestOrderedItemRead::from).toList(); // Convert each OrderedProduct to RestOrderedItemRead
  }

}
