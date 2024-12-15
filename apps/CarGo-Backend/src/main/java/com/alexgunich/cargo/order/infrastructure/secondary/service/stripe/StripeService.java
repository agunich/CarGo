package com.alexgunich.cargo.order.infrastructure.secondary.service.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.alexgunich.cargo.order.domain.order.CartPaymentException;
import com.alexgunich.cargo.order.domain.order.aggregate.DetailCartItemRequest;
import com.alexgunich.cargo.order.domain.order.vo.StripeSessionId;
import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service to interact with Stripe API for creating payment sessions for cart items.
 * <p>
 * This service provides methods to create a payment session with Stripe for processing payments.
 * It collects user and product information, creates a Stripe session, and returns the session ID.
 * </p>
 */
@Service
public class StripeService {

  @Value("${application.stripe.api-key}")
  private String apiKey;

  @Value("${application.client-base-url}")
  private String clientBaseUrl;

  public StripeService() {
  }

  /**
   * Initializes the Stripe API with the provided API key after the service is constructed.
   * <p>
   * This method is executed after the service is created and the Spring context is fully initialized.
   * </p>
   */
  @PostConstruct
  public void setApiKey() {
    Stripe.apiKey = apiKey;
  }

  /**
   * Creates a Stripe payment session for the provided cart details.
   * <p>
   * This method constructs a payment session on Stripe by including user and product information
   * along with cart item details. It sets success and failure URLs for redirection after the payment.
   * </p>
   *
   * @param connectedUser       the user making the purchase, which includes email and public ID
   * @param productsInformation the list of products available in the system, required to get product details
   * @param items               the list of cart items, including the quantity and product IDs
   * @return a {@link StripeSessionId} representing the Stripe session ID
   * @throws CartPaymentException if there is an error during the creation of the payment session
   */
  public StripeSessionId createPayment(User connectedUser,
                                       List<Product> productsInformation,
                                       List<DetailCartItemRequest> items) {
    SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
      .setMode(SessionCreateParams.Mode.PAYMENT)
      .putMetadata("user_public_id", connectedUser.getUserPublicId().value().toString())
      .setCustomerEmail(connectedUser.getEmail().value())
      .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
      .setSuccessUrl(this.clientBaseUrl + "/cart/success?session_id={CHECKOUT_SESSION_ID}")
      .setCancelUrl(this.clientBaseUrl + "/cart/failure");

    for (DetailCartItemRequest itemRequest : items) {
      Product productDetails = productsInformation.stream()
        .filter(product -> product.getPublicId().value().equals(itemRequest.productId().value()))
        .findFirst().orElseThrow();

      SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
        .putMetadata("product_id", productDetails.getPublicId().value().toString())
        .setName(productDetails.getName().value())
        .build();

      SessionCreateParams.LineItem.PriceData linePriceData = SessionCreateParams.LineItem.PriceData.builder()
        .setUnitAmountDecimal(BigDecimal.valueOf(Double.valueOf(productDetails.getPrice().value()).longValue() * 100))
        .setProductData(productData)
        .setCurrency("EUR")
        .build();

      SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
        .setQuantity(itemRequest.quantity())
        .setPriceData(linePriceData)
        .build();

      sessionBuilder.addLineItem(lineItem);
    }

    return createSession(sessionBuilder.build());
  }

  /**
   * Creates a Stripe session with the given session parameters.
   * <p>
   * This method interacts with the Stripe API to create a checkout session. In case of any error,
   * it throws a custom exception {@link CartPaymentException}.
   * </p>
   *
   * @param sessionInformation the session parameters used to create the payment session
   * @return a {@link StripeSessionId} representing the Stripe session ID
   * @throws CartPaymentException if there is an error while creating the Stripe session
   */
  private StripeSessionId createSession(SessionCreateParams sessionInformation) {
    try {
      Session session = Session.create(sessionInformation);
      return new StripeSessionId(session.getId());
    } catch (StripeException se) {
      throw new CartPaymentException("Error while creating Stripe session");
    }
  }
}
