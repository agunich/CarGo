package com.alexgunich.cargo.product.domain.repository;

import com.alexgunich.cargo.order.domain.order.vo.ProductPublicId;
import com.alexgunich.cargo.product.domain.aggregate.FilterQuery;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing and managing {@link Product} entities in the data store.
 * <p>
 * The {@link ProductRepository} interface provides methods for performing CRUD operations and querying
 * {@link Product} entities, including support for filtering, pagination, and managing product quantities.
 * </p>
 */
public interface ProductRepository {

  /**
   * Saves a new {@link Product} to the data store.
   *
   * @param productToCreate the product to be saved
   * @return the saved {@link Product} object, including its generated identifier
   */
  Product save(Product productToCreate);

  /**
   * Retrieves all {@link Product} entities, paginated by the given {@link Pageable}.
   *
   * @param pageable pagination details such as page number and page size
   * @return a {@link Page} containing the list of products
   */
  Page<Product> findAll(Pageable pageable);

  /**
   * Deletes a {@link Product} by its public identifier.
   *
   * @param publicId the public identifier of the product to delete
   * @return the number of products deleted (0 or 1)
   */
  int delete(PublicId publicId);

  /**
   * Retrieves all featured {@link Product} entities, paginated by the given {@link Pageable}.
   *
   * @param pageable pagination details such as page number and page size
   * @return a {@link Page} containing the list of featured products
   */
  Page<Product> findAllFeaturedProduct(Pageable pageable);

  /**
   * Retrieves a {@link Product} by its public identifier.
   *
   * @param publicId the public identifier of the product
   * @return an {@link Optional} containing the product if found, or an empty {@link Optional} if not found
   */
  Optional<Product> findOne(PublicId publicId);

  /**
   * Retrieves products that belong to a specific category, excluding one product, paginated by the given {@link Pageable}.
   *
   * @param pageable pagination details such as page number and page size
   * @param categoryPublicId the public identifier of the category
   * @param productPublicId the public identifier of the product to exclude from the results
   * @return a {@link Page} containing the list of products matching the category, excluding the specified product
   */
  Page<Product> findByCategoryExcludingOne(Pageable pageable, PublicId categoryPublicId, PublicId productPublicId);

  /**
   * Retrieves products that match the specified category and size, paginated by the given {@link Pageable}.
   *
   * @param pageable pagination details such as page number and page size
   * @param filterQuery the filter query containing the category and size to filter products by
   * @return a {@link Page} containing the list of products matching the specified category and size
   */
  Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery);

  /**
   * Retrieves a list of {@link Product} entities based on their public identifiers.
   *
   * @param publicIds the list of public identifiers for the products to retrieve
   * @return a list of {@link Product} entities matching the given public identifiers
   */
  List<Product> findByPublicIds(List<PublicId> publicIds);

  /**
   * Updates the quantity of a {@link Product} in stock.
   *
   * @param productPublicId the public identifier of the product
   * @param quantity the new quantity to set for the product
   */
  void updateQuantity(ProductPublicId productPublicId, long quantity);
}
