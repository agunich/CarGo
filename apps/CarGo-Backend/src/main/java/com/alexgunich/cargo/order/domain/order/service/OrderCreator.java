package com.alexgunich.cargo.order.domain.order.service;

import com.alexgunich.cargo.order.domain.order.aggregate.DetailCartItemRequest;
import com.alexgunich.cargo.order.domain.order.aggregate.DetailCartRequest;
import com.alexgunich.cargo.order.domain.order.aggregate.Order;
import com.alexgunich.cargo.order.domain.order.aggregate.OrderedProduct;
import com.alexgunich.cargo.order.domain.order.repository.OrderRepository;
import com.alexgunich.cargo.order.domain.order.vo.StripeSessionId;
import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.order.infrastructure.secondary.service.stripe.StripeService;
import com.alexgunich.cargo.product.domain.aggregate.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for creating an order and processing payment through Stripe.
 *
 * <p>The {@link OrderCreator} class handles the creation of an {@link Order} by processing
 * cart items, linking them with product details, and initiating a payment session with Stripe.</p>
 */
public class OrderCreator {

  private final OrderRepository orderRepository;
  private final StripeService stripeService;

  /**
   * Constructs an {@link OrderCreator} with the given dependencies.
   *
   * @param orderRepository the repository for saving and retrieving orders.
   * @param stripeService the service used for interacting with Stripe payment API.
   */
  public OrderCreator(OrderRepository orderRepository, StripeService stripeService) {
    this.orderRepository = orderRepository;
    this.stripeService = stripeService;
  }

  /**
   * Creates an order by processing the provided product information and cart items.
   * Also initiate a Stripe payment session.
   *
   * @param productsInformations the list of {@link Product} details.
   * @param items the list of {@link DetailCartItemRequest} representing the cart items.
   * @param connectedUser the {@link User} placing the order.
   * @return the {@link StripeSessionId} for the payment session.
   */
  public StripeSessionId create(List<Product> productsInformations,
                                List<DetailCartItemRequest> items,
                                User connectedUser) {

    // Create a Stripe payment session and retrieve the session ID
    StripeSessionId stripeSessionId = this.stripeService.createPayment(connectedUser,
      productsInformations, items);

    // Initialize the list of ordered products
    List<OrderedProduct> orderedProducts = new ArrayList<>();

    // Map cart items to order products
    for(DetailCartItemRequest itemRequest: items) {
      Product productDetails = productsInformations.stream()
        .filter(product -> product.getPublicId().value().equals(itemRequest.productId().value()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Product not found for ID: " + itemRequest.productId()));

      // Create an ordered product and add it to the list
      OrderedProduct orderedProduct = OrderedProduct.create(itemRequest.quantity(), productDetails);
      orderedProducts.add(orderedProduct);
    }

    // Create the order and save it in the repository
    Order order = Order.create(connectedUser, orderedProducts, stripeSessionId);
    orderRepository.save(order);

    // Return the Stripe session ID for the payment session
    return stripeSessionId;
  }
}
