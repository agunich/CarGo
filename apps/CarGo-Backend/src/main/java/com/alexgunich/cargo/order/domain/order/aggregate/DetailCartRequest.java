package com.alexgunich.cargo.order.domain.order.aggregate;

import org.jilt.Builder;

import java.util.List;

/**
 * Represents a detailed request to manage or update a shopping cart,
 * containing a list of individual cart item requests.
 *
 * <p>This is a record class that encapsulates a collection of
 * {@link DetailCartItemRequest} objects, each representing the details
 * of a single item in the cart.</p>
 *
 * @param items a list of {@link DetailCartItemRequest} representing the items in the cart.
 */
@Builder
public record DetailCartRequest(List<DetailCartItemRequest> items) {
}
