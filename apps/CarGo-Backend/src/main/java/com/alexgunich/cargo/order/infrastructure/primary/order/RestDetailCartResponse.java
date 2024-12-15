package com.alexgunich.cargo.order.infrastructure.primary.order;

import com.alexgunich.cargo.order.domain.order.aggregate.DetailCartResponse;
import org.jilt.Builder;

import java.util.List;

/**
 * A record class representing the response for detailed cart information in the REST API.
 * It contains a list of products in the cart, represented by {@link RestProductCart}.
 */
@Builder
public record RestDetailCartResponse(List<RestProductCart> products) {

  /**
   * Converts a {@link DetailCartResponse} (domain model) to a {@link RestDetailCartResponse} (REST API model).
   * This method transforms the detailed cart response from the domain to the format required for the REST API response.
   *
   * @param detailCartResponse the {@link DetailCartResponse} object from the domain model
   * @return a corresponding {@link RestDetailCartResponse} object in REST API format
   */
  public static RestDetailCartResponse from(DetailCartResponse detailCartResponse) {
    return RestDetailCartResponseBuilder.restDetailCartResponse()
      .products(detailCartResponse.getProducts().stream()
        .map(RestProductCart::from)  // Convert each product in the domain model to its REST representation
        .toList())
      .build();
  }
}
