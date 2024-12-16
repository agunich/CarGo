package com.alexgunich.cargo.order.domain.order.aggregate;

import com.alexgunich.cargo.order.domain.order.vo.StripeSessionId;
import com.alexgunich.cargo.order.domain.user.vo.UserAddressToUpdate;
import org.jilt.Builder;

import java.util.List;

/**
 * Represents information about a Stripe session, including payment session details,
 * user address updates, and quantities of products in an order.
 *
 * <p>This record encapsulates the necessary details for interacting with Stripe during
 * the order process.</p>
 *
 * @param stripeSessionId the {@link StripeSessionId} associated with the payment session.
 * @param userAddress the {@link UserAddressToUpdate} containing the user's updated address information.
 * @param orderProductQuantity a list of {@link OrderProductQuantity} representing the products and their quantities in the order.
 */
@Builder
public record StripeSessionInformation(StripeSessionId stripeSessionId,
                                       UserAddressToUpdate userAddress,
                                       List<OrderProductQuantity> orderProductQuantity) {
}
