package com.alexgunich.cargo.order.infrastructure.primary.order;

import com.alexgunich.cargo.order.domain.order.vo.StripeSessionId;
import org.jilt.Builder;

/**
 * A record class representing a Stripe session in the REST API response.
 * It includes the session's ID.
 */
@Builder
public record RestStripeSession(String id) {

  /**
   * Converts a {@link StripeSessionId} (domain model) to a {@link RestStripeSession} (REST API model).
   * This method transforms the Stripe session details from the domain model into the format required for the REST API response.
   *
   * @param stripeSessionId the {@link StripeSessionId} object from the domain model
   * @return a corresponding {@link RestStripeSession} object in REST API format
   */
  public static RestStripeSession from(StripeSessionId stripeSessionId) {
    return RestStripeSessionBuilder.restStripeSession()
      .id(stripeSessionId.value()) // Extract the Stripe session ID
      .build();
  }
}
