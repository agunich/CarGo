package com.alexgunich.cargo.product.infrastructure.secondary.repository;

import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.vo.ProductSize;
import com.alexgunich.cargo.product.infrastructure.secondary.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing {@link ProductEntity} instances.
 * <p>
 * This interface extends {@link JpaRepository} to provide CRUD operations and
 * custom query methods for product entities stored in the database.
 * </p>
 */
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {

  /**
   * Deletes a product entity by its public ID.
   *
   * @param publicId the unique public ID of the product to delete
   * @return the number of entities deleted (should be 0 or 1)
   */
  int deleteByPublicId(UUID publicId);

  /**
   * Retrieves a product entity by its public ID.
   *
   * @param publicId the unique public ID of the product
   * @return an {@link Optional} containing the found product entity, or empty if not found
   */
  Optional<ProductEntity> findByPublicId(UUID publicId);

  /**
   * Retrieves a paginated list of featured products.
   *
   * @param pageable pagination information
   * @return a page of featured product entities
   */
  Page<ProductEntity> findAllByFeaturedTrue(Pageable pageable);

  /**
   * Retrieves a paginated list of products by category public ID, excluding a specific product.
   *
   * @param pageable             pagination information
   * @param categoryPublicId     the public ID of the category
   * @param excludedProductPublicId the public ID of the product to exclude
   * @return a page of product entities
   */
  Page<ProductEntity> findByCategoryPublicIdAndPublicIdNot(Pageable pageable, UUID categoryPublicId, UUID excludedProductPublicId);

  /**
   * Retrieves a paginated list of products by category public ID and a list of sizes.
   *
   * @param pageable         pagination information
   * @param categoryPublicId the public ID of the category
   * @param sizes           the list of product sizes to filter by
   * @return a page of product entities
   */
  @Query("SELECT product FROM ProductEntity product WHERE (:sizes is null or product.size IN (:sizes)) AND " +
    "product.category.publicId = :categoryPublicId")
  Page<ProductEntity> findByCategoryPublicIdAndSizesIn(Pageable pageable, UUID categoryPublicId, List<ProductSize> sizes);

  /**
   * Retrieves a list of products by their public IDs.
   *
   * @param publicIds the list of public IDs to look for
   * @return a list of product entities
   */
  List<ProductEntity> findAllByPublicIdIn(List<UUID> publicIds);

  /**
   * Updates the stock quantity of a product by its public ID.
   *
   * @param productPublicId the public ID of the product
   * @param quantity        the quantity to subtract from the stock
   */
  @Modifying
  @Query("UPDATE ProductEntity product " +
    "SET product.nbInStock = product.nbInStock - :quantity " +
    "WHERE product.publicId = :productPublicId")
  void updateQuantity(UUID productPublicId, long quantity);
}
