package com.alexgunich.cargo.order.infrastructure.primary.order;

import com.alexgunich.cargo.order.domain.order.aggregate.Order;
import com.alexgunich.cargo.order.domain.order.vo.OrderStatus;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

/**
 * A record class representing an order in the REST API response, specifically for administrative purposes.
 * It includes the order's public ID, status, a list of ordered items, the shipping address, and the user's email.
 */
@Builder
public record RestOrderReadAdmin(UUID publicId,
                                 OrderStatus status,
                                 List<RestOrderedItemRead> orderedItems,
                                 String address,
                                 String email) {

  /**
   * Converts an {@link Order} (domain model) to a {@link RestOrderReadAdmin} (REST API model).
   * This method transforms the order details from the domain model into the format required for the REST API response,
   * providing information needed by administrators, such as the order's public ID, status, ordered items, shipping address,
   * and the associated user's email.
   *
   * @param order the {@link Order} object from the domain model
   * @return a corresponding {@link RestOrderReadAdmin} object in REST API format
   */
  public static RestOrderReadAdmin from(Order order) {
    StringBuilder address = new StringBuilder();

    // Build the full address string if the order contains user address details
    if (order.getUser().getUserAddress() != null) {
      address.append(order.getUser().getUserAddress().street());
      address.append(", ");
      address.append(order.getUser().getUserAddress().city());
      address.append(", ");
      address.append(order.getUser().getUserAddress().zipCode());
      address.append(", ");
      address.append(order.getUser().getUserAddress().country());
    }

    return RestOrderReadAdminBuilder.restOrderReadAdmin()
      .publicId(order.getPublicId().value()) // Extract the public ID of the order
      .status(order.getStatus()) // Extract the order status
      .orderedItems(RestOrderedItemRead.from(order.getOrderedProducts())) // Convert ordered products to API format
      .address(address.toString()) // Provide the full address as a string
      .email(order.getUser().getEmail().value()) // Extract the user's email
      .build();
  }
}
