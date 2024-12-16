package com.alexgunich.cargo.product.domain.service;

import com.alexgunich.cargo.order.domain.order.aggregate.OrderProductQuantity;
import com.alexgunich.cargo.product.domain.repository.ProductRepository;

import java.util.List;

/**
 * Service class for updating product quantities in the repository.
 */
public class ProductUpdater {

  private final ProductRepository productRepository;

  /**
   * Constructs a ProductUpdater service with the specified ProductRepository.
   *
   * @param productRepository the repository to be used for product quantity updates
   */
  public ProductUpdater(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * Updates the quantities of products based on the provided list of order product quantities.
   *
   * @param orderProductQuantities a list of OrderProductQuantity instances representing the products and their new quantities
   */
  public void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities) {
    for (OrderProductQuantity orderProductQuantity : orderProductQuantities) {
      productRepository.updateQuantity(orderProductQuantity.productPublicId(),
        orderProductQuantity.quantity().value());
    }
  }
}
