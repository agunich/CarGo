package com.alexgunich.cargo.order.infrastructure.primary.order;

import com.alexgunich.cargo.order.domain.order.aggregate.Order;
import com.alexgunich.cargo.order.domain.order.vo.OrderStatus;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

/**
 * A record class representing an order in the REST API response.
 * It includes the order's public ID, status, and a list of ordered items.
 */
@Builder
public record RestOrderRead(UUID publicId,
                            OrderStatus status,
                            List<RestOrderedItemRead> orderedItems) {

  /**
   * Converts an {@link Order} (domain model) to a {@link RestOrderRead} (REST API model).
   * This method transforms the order details from the domain model into the format required for the REST API response.
   *
   * @param order the {@link Order} object from the domain model
   * @return a corresponding {@link RestOrderRead} object in REST API format
   */
  public static RestOrderRead from(Order order) {
    return RestOrderReadBuilder.restOrderRead()
      .publicId(order.getPublicId().value()) // Extract the public ID from the domain model
      .status(order.getStatus()) // Extract the order status from the domain model
      .orderedItems(RestOrderedItemRead.from(order.getOrderedProducts())) // Convert the list of ordered products to REST API format
      .build();
  }
}
