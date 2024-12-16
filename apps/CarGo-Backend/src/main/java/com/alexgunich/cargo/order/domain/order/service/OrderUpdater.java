package com.alexgunich.cargo.order.domain.order.service;

import com.alexgunich.cargo.order.domain.order.aggregate.*;
import com.alexgunich.cargo.order.domain.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for updating orders based on Stripe session information
 * and calculating product quantities.
 *
 * <p>The {@link OrderUpdater} class provides methods to update the order status based on
 * Stripe payment session information and to compute the quantity of products in an order.</p>
 */
public class OrderUpdater {

  private final OrderRepository orderRepository;

  /**
   * Constructs an {@link OrderUpdater} with the given {@link OrderRepository}.
   *
   * @param orderRepository the repository for managing {@link Order} entities.
   */
  public OrderUpdater(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  /**
   * Updates an order's status based on the Stripe session information.
   * Marks the order as paid upon successful payment confirmation.
   *
   * @param stripeSessionInformation the {@link StripeSessionInformation} containing the session ID
   *                                 and related information for updating the order.
   * @return the list of {@link OrderedProduct} objects associated with the order.
   * @throws IllegalStateException if the order cannot be found or if the payment validation fails.
   */
  public List<OrderedProduct> updateOrderFromStripe(StripeSessionInformation stripeSessionInformation) {
    // Retrieve the order based on the Stripe session ID
    Order order = orderRepository.findByStripeSessionId(stripeSessionInformation)
      .orElseThrow(() -> new IllegalStateException("Order not found for the provided Stripe session"));

    // Validate the payment status of the order
    order.validatePayment();

    // Update the order status in the repository
    orderRepository.updateStatusByPublicId(order.getStatus(), order.getPublicId());

    // Return the list of ordered products
    return order.getOrderedProducts();
  }

  /**
   * Computes the quantity of each ordered product.
   *
   * @param orderedProducts the list of {@link OrderedProduct} objects for which the quantities are to be computed.
   * @return a list of {@link OrderProductQuantity} representing the quantity of each ordered product.
   */
  public List<OrderProductQuantity> computeQuantity(List<OrderedProduct> orderedProducts) {
    List<OrderProductQuantity> orderProductQuantities = new ArrayList<>();

    // Map each ordered product to its respective quantity
    for (OrderedProduct orderedProduct : orderedProducts) {
      OrderProductQuantity orderProductQuantity = OrderProductQuantityBuilder.orderProductQuantity()
        .productPublicId(orderedProduct.getProductPublicId())
        .quantity(orderedProduct.getQuantity())
        .build();
      orderProductQuantities.add(orderProductQuantity);
    }

    // Return the list of computed product quantities
    return orderProductQuantities;
  }
}
