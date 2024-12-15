package com.alexgunich.cargo.order.domain.order.aggregate;

import com.alexgunich.cargo.order.domain.order.vo.OrderQuantity;
import com.alexgunich.cargo.order.domain.order.vo.ProductPublicId;
import org.jilt.Builder;

/**
 * Represents the quantity of a specific product in an order.
 *
 * <p>This record encapsulates the quantity of a product and its unique public identifier.</p>
 *
 * @param quantity the {@link OrderQuantity} representing the amount of the product in the order.
 * @param productPublicId the {@link ProductPublicId} that uniquely identifies the product.
 */
@Builder
public record OrderProductQuantity(OrderQuantity quantity, ProductPublicId productPublicId) {
}
