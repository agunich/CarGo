package com.alexgunich.cargo.order.domain.order.aggregate;

import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.jilt.Builder;

/**
 * Represents a request to add or update a cart item in detail,
 * including the product and its quantity.
 *
 * <p>This is a record class that encapsulates the details of a
 * cart item request. It includes the product's public identifier
 * and the desired quantity.</p>
 *
 * @param productId the unique identifier of the product.
 * @param quantity the quantity of the product being requested.
 */
@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) {
}
