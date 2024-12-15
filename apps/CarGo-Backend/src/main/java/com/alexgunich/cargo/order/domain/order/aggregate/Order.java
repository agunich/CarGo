package com.alexgunich.cargo.order.domain.order.aggregate;

import com.alexgunich.cargo.order.domain.order.vo.OrderStatus;
import com.alexgunich.cargo.order.domain.order.vo.StripeSessionId;
import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

/**
 * Represents a customer's order, including its status, associated user,
 * payment details, and the products in the order.
 */
@Builder
public class Order {

  private OrderStatus status;
  private User user;
  private String stripeId;
  private PublicId publicId;
  private List<OrderedProduct> orderedProducts;

  /**
   * Constructs an {@code Order} with the specified details.
   *
   * @param status the current status of the order (e.g., PENDING, PAID).
   * @param user the {@link User} who placed the order.
   * @param stripeId the Stripe session ID associated with the order's payment.
   * @param publicId a unique public identifier for the order.
   * @param orderedProducts a list of {@link OrderedProduct} objects in the order.
   */
  public Order(OrderStatus status, User user, String stripeId, PublicId publicId, List<OrderedProduct> orderedProducts) {
    this.status = status;
    this.user = user;
    this.stripeId = stripeId;
    this.publicId = publicId;
    this.orderedProducts = orderedProducts;
  }

  /**
   * Retrieves the current status of the order.
   *
   * @return the {@link OrderStatus} of the order.
   */
  public OrderStatus getStatus() {
    return status;
  }

  /**
   * Retrieves the user who placed the order.
   *
   * @return the {@link User} associated with the order.
   */
  public User getUser() {
    return user;
  }

  /**
   * Retrieves the Stripe session ID for the order.
   *
   * @return a {@link String} representing the Stripe session ID.
   */
  public String getStripeId() {
    return stripeId;
  }

  /**
   * Retrieves the unique public identifier of the order.
   *
   * @return the {@link PublicId} of the order.
   */
  public PublicId getPublicId() {
    return publicId;
  }

  /**
   * Retrieves the list of products in the order.
   *
   * @return a list of {@link OrderedProduct} objects.
   */
  public List<OrderedProduct> getOrderedProducts() {
    return orderedProducts;
  }

  /**
   * Creates a new order with default values and specified details.
   *
   * <p>The new order is initialized with:
   * <ul>
   *   <li>A randomly generated {@link PublicId}.</li>
   *   <li>Status set to {@code OrderStatus.PENDING}.</li>
   *   <li>The provided user, ordered products, and Stripe session ID.</li>
   * </ul>
   *
   * @param connectedUser the {@link User} who placed the order.
   * @param orderedProducts a list of {@link OrderedProduct} in the order.
   * @param stripeSessionId the {@link StripeSessionId} for the order payment.
   * @return a new {@code Order} instance.
   */
  public static Order create(User connectedUser, List<OrderedProduct> orderedProducts,
                             StripeSessionId stripeSessionId) {
    return OrderBuilder.order()
      .publicId(new PublicId(UUID.randomUUID()))
      .user(connectedUser)
      .status(OrderStatus.PENDING)
      .orderedProducts(orderedProducts)
      .stripeId(stripeSessionId.value())
      .build();
  }

  /**
   * Updates the order's status to indicate that the payment has been validated.
   */
  public void validatePayment() {
    this.status = OrderStatus.PAID;
  }
}
