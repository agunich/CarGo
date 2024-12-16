package com.alexgunich.cargo.product.domain.service;

import com.alexgunich.cargo.product.domain.aggregate.FilterQuery;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.repository.ProductRepository;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service class for handling product-related operations in the shop context.
 */
public class ProductShop {

  private final ProductRepository productRepository;

  /**
   * Constructs a ProductShop service with the specified ProductRepository.
   *
   * @param productRepository the repository to be used for product operations
   */
  public ProductShop(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * Retrieves a paginated list of featured products.
   *
   * @param pageable the pagination information
   * @return a Page containing the list of featured Product entities
   */
  public Page<Product> getFeaturedProducts(Pageable pageable) {
    return productRepository.findAllFeaturedProduct(pageable);
  }

  /**
   * Finds related products based on the category of the specified product.
   * Excludes the product itself from the results.
   *
   * @param pageable        the pagination information
   * @param productPublicId the PublicId of the product for which related products are sought
   * @return a Page of related Product entities
   * @throws EntityNotFoundException if no product is found with the specified ID
   */
  public Page<Product> findRelated(Pageable pageable, PublicId productPublicId) {
    Optional<Product> productOpt = productRepository.findOne(productPublicId);
    if (productOpt.isPresent()) {
      Product product = productOpt.get();
      return productRepository.findByCategoryExcludingOne(pageable,
        product.getCategory().getPublicId(),
        productPublicId);
    } else {
      throw new EntityNotFoundException(String.format("No product found with id %s", productPublicId));
    }
  }

  /**
   * Filters products based on the provided FilterQuery criteria.
   *
   * @param pageable the pagination information
   * @param query    the FilterQuery containing filtering criteria
   * @return a Page containing the filtered list of Product entities
   */
  public Page<Product> filter(Pageable pageable, FilterQuery query) {
    return productRepository.findByCategoryAndSize(pageable, query);
  }
}
