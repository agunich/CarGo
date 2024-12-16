package com.alexgunich.cargo.order.infrastructure.primary.order;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Address;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.alexgunich.cargo.order.application.OrderApplicationService;
import com.alexgunich.cargo.order.domain.order.CartPaymentException;
import com.alexgunich.cargo.order.domain.order.aggregate.*;
import com.alexgunich.cargo.order.domain.order.vo.StripeSessionId;
import com.alexgunich.cargo.order.domain.user.vo.*;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.alexgunich.cargo.product.infrastructure.primary.ProductsAdminResource.ROLE_ADMIN;

/**
 * REST Controller for handling order-related endpoints.
 * Provides functionality for creating orders, handling payments, and managing order data.
 */
@RestController
@RequestMapping("/api/orders")
public class OrderResource {

  private final OrderApplicationService orderApplicationService;

  @Value("${application.stripe.webhook-secret}")
  private String webhookSecret;

  public OrderResource(OrderApplicationService orderApplicationService) {
    this.orderApplicationService = orderApplicationService;
  }

  /**
   * Fetches the details of the cart based on the provided product IDs.
   *
   * @param productIds the list of product IDs to fetch details for
   * @return the details of the cart with the given product IDs
   */
  @GetMapping("/get-cart-details")
  public ResponseEntity<RestDetailCartResponse> getDetails(@RequestParam List<UUID> productIds) {
    List<DetailCartItemRequest> cartItemRequests = productIds.stream()
      .map(uuid -> new DetailCartItemRequest(new PublicId(uuid), 1))
      .toList();

    DetailCartRequest detailCartRequest = DetailCartRequestBuilder.detailCartRequest().items(cartItemRequests).build();
    DetailCartResponse cartDetails = orderApplicationService.getCartDetails(detailCartRequest);
    return ResponseEntity.ok(RestDetailCartResponse.from(cartDetails));
  }

  /**
   * Initializes the payment process by creating a Stripe session.
   *
   * @param items the list of cart items to be included in the payment
   * @return the response containing the Stripe session information
   * @throws CartPaymentException if there is an issue with payment initialization
   */
  @PostMapping("/init-payment")
  public ResponseEntity<RestStripeSession> initPayment(@RequestBody List<RestCartItemRequest> items) {
    List<DetailCartItemRequest> detailCartItemRequests = RestCartItemRequest.to(items);
    try {
      StripeSessionId stripeSessionInformation = orderApplicationService.createOrder(detailCartItemRequests);
      RestStripeSession restStripeSession = RestStripeSession.from(stripeSessionInformation);
      return ResponseEntity.ok(restStripeSession);
    } catch (CartPaymentException cpe) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Handles the Stripe webhook to process payment events.
   *
   * @param paymentEvent the Stripe event payload
   * @param stripeSignature the Stripe signature to verify the event
   * @return HTTP status indicating success or failure
   */
  @PostMapping("/webhook")
  public ResponseEntity<Void> webhookStripe(@RequestBody String paymentEvent,
                                            @RequestHeader("Stripe-Signature") String stripeSignature) {
    Event event = null;
    try {
      event = Webhook.constructEvent(
        paymentEvent, stripeSignature, webhookSecret
      );
    } catch (SignatureVerificationException e) {
      return ResponseEntity.badRequest().build();
    }

    Optional<StripeObject> rawStripeObjectOpt = event.getDataObjectDeserializer().getObject();

    switch (event.getType()) {
      case "checkout.session.completed":
        handleCheckoutSessionCompleted(rawStripeObjectOpt.orElseThrow());
        break;
    }

    return ResponseEntity.ok().build();
  }

  /**
   * Handles the 'checkout.session.completed' event from Stripe.
   * This method updates the order and user address information after successful payment.
   *
   * @param rawStripeObject the Stripe session object that contains order details
   */
  private void handleCheckoutSessionCompleted(StripeObject rawStripeObject) {
    if (rawStripeObject instanceof Session session) {
      Address address = session.getCustomerDetails().getAddress();

      UserAddress userAddress = UserAddressBuilder.userAddress()
        .city(address.getCity())
        .country(address.getCountry())
        .zipCode(address.getPostalCode())
        .street(address.getLine1())
        .build();

      UserAddressToUpdate userAddressToUpdate = UserAddressToUpdateBuilder.userAddressToUpdate()
        .userAddress(userAddress)
        .userPublicId(new UserPublicId(UUID.fromString(session.getMetadata().get("user_public_id"))))
        .build();

      StripeSessionInformation sessionInformation = StripeSessionInformationBuilder.stripeSessionInformation()
        .userAddress(userAddressToUpdate)
        .stripeSessionId(new StripeSessionId(session.getId()))
        .build();

      orderApplicationService.updateOrder(sessionInformation);
    }
  }

  /**
   * Retrieves the orders associated with the currently authenticated user.
   *
   * @param pageable pagination information
   * @return a paginated list of orders for the connected user
   */
  @GetMapping("/user")
  public ResponseEntity<Page<RestOrderRead>> getOrdersForConnectedUser(Pageable pageable) {
    Page<Order> orders = orderApplicationService.findOrdersForConnectedUser(pageable);
    PageImpl<RestOrderRead> restOrderReads = new PageImpl<>(
      orders.getContent().stream().map(RestOrderRead::from).toList(),
      pageable,
      orders.getTotalElements()
    );
    return ResponseEntity.ok(restOrderReads);
  }

  /**
   * Retrieves the orders for an administrator. Only accessible by users with the 'ROLE_ADMIN' role.
   *
   * @param pageable pagination information
   * @return a paginated list of orders for administrators
   */
  @GetMapping("/admin")
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<Page<RestOrderReadAdmin>> getOrdersForAdmin(Pageable pageable) {
    Page<Order> orders = orderApplicationService.findOrdersForAdmin(pageable);
    PageImpl<RestOrderReadAdmin> restOrderReads = new PageImpl<>(
      orders.getContent().stream().map(RestOrderReadAdmin::from).toList(),
      pageable,
      orders.getTotalElements()
    );
    return ResponseEntity.ok(restOrderReads);
  }
}
