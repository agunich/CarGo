package com.alexgunich.cargo.product.domain.aggregate;

import com.alexgunich.cargo.product.domain.vo.ProductSize;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.jilt.Builder;

import java.util.List;

/**
 * Represents a filter query for searching products.
 * <p>
 * The query includes a category ID to filter by product category and a list of product sizes
 * to filter products by their size.
 * </p>
 */
@Builder
public record FilterQuery(PublicId categoryId, List<ProductSize> sizes) {

  /**
   * Constructs a new {@link FilterQuery} instance with the specified category ID and product sizes.
   *
   * @param categoryId the ID of the category to filter products by
   * @param sizes the list of product sizes to filter products by
   */
}
