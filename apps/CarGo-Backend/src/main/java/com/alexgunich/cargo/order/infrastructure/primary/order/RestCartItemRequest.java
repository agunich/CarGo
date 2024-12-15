package com.alexgunich.cargo.order.infrastructure.primary.order;

import com.alexgunich.cargo.order.domain.order.aggregate.DetailCartItemRequest;
import com.alexgunich.cargo.order.domain.order.aggregate.DetailCartItemRequestBuilder;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

/**
 * A record class representing a cart item request in the REST API.
 * It contains the public ID of the product and the quantity of the item in the cart.
 * Provides methods to convert between the REST cart item representation and the domain's DetailCartItemRequest.
 */
@Builder
public record RestCartItemRequest(UUID publicId, long quantity) {

  /**
   * Converts a {@link RestCartItemRequest} to a {@link DetailCartItemRequest}.
   * This method is used to transform the REST API cart item request into the domain model.
   *
   * @param item the {@link RestCartItemRequest} to convert
   * @return a corresponding {@link DetailCartItemRequest} that is used within the domain
   */
  public static DetailCartItemRequest to(RestCartItemRequest item) {
    return DetailCartItemRequestBuilder.detailCartItemRequest()
      .productId(new PublicId(item.publicId()))
      .quantity(item.quantity())
      .build();
  }

  /**
   * Converts a {@link DetailCartItemRequest} to a {@link RestCartItemRequest}.
   * This method is used to transform the domain model cart item request into the REST API representation.
   *
   * @param detailCartItemRequest the {@link DetailCartItemRequest} to convert
   * @return a corresponding {@link RestCartItemRequest} that can be used in the REST API
   */
  public static RestCartItemRequest from(DetailCartItemRequest detailCartItemRequest) {
    return RestCartItemRequestBuilder.restCartItemRequest()
      .publicId(detailCartItemRequest.productId().value())
      .quantity(detailCartItemRequest.quantity())
      .build();
  }

  /**
   * Converts a list of {@link RestCartItemRequest} objects to a list of {@link DetailCartItemRequest} objects.
   * This method is useful for bulk transformations from the REST API request body to domain objects.
   *
   * @param items the list of {@link RestCartItemRequest} objects to convert
   * @return a list of corresponding {@link DetailCartItemRequest} objects
   */
  public static List<DetailCartItemRequest> to(List<RestCartItemRequest> items) {
    return items.stream().map(RestCartItemRequest::to).toList();
  }

  /**
   * Converts a list of {@link DetailCartItemRequest} objects to a list of {@link RestCartItemRequest} objects.
   * This method is useful for transforming domain models back into the REST API response format.
   *
   * @param items the list of {@link DetailCartItemRequest} objects to convert
   * @return a list of corresponding {@link RestCartItemRequest} objects
   */
  public static List<RestCartItemRequest> from(List<DetailCartItemRequest> items) {
    return items.stream().map(RestCartItemRequest::from).toList();
  }
}
