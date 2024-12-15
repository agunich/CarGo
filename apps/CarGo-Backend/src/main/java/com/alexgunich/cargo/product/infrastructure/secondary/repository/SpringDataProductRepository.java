package com.alexgunich.cargo.product.infrastructure.secondary.repository;

import com.alexgunich.cargo.order.domain.order.vo.ProductPublicId;
import com.alexgunich.cargo.product.domain.aggregate.FilterQuery;
import com.alexgunich.cargo.product.domain.aggregate.Picture;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.repository.ProductRepository;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import com.alexgunich.cargo.product.infrastructure.secondary.entity.CategoryEntity;
import com.alexgunich.cargo.product.infrastructure.secondary.entity.PictureEntity;
import com.alexgunich.cargo.product.infrastructure.secondary.entity.ProductEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of the {@link ProductRepository} interface using Spring Data JPA.
 * <p>
 * This class serves as a repository for managing {@link Product} entities,
 * delegating the actual data access to the appropriate JPA repositories.
 * </p>
 */
@Repository
public class SpringDataProductRepository implements ProductRepository {

  private final JpaCategoryRepository jpaCategoryRepository;
  private final JpaProductRepository jpaProductRepository;
  private final JpaProductPictureRepository jpaProductPictureRepository;

  /**
   * Constructs a new SpringDataProductRepository with the specified JPA repositories.
   *
   * @param jpaCategoryRepository the JPA category repository
   * @param jpaProductRepository the JPA product repository
   * @param jpaProductPictureRepository the JPA product picture repository
   */
  public SpringDataProductRepository(JpaCategoryRepository jpaCategoryRepository, JpaProductRepository jpaProductRepository, JpaProductPictureRepository jpaProductPictureRepository) {
    this.jpaCategoryRepository = jpaCategoryRepository;
    this.jpaProductRepository = jpaProductRepository;
    this.jpaProductPictureRepository = jpaProductPictureRepository;
  }

  /**
   * Saves a new product or updates an existing one.
   *
   * @param productToCreate the product to create or update
   * @return the saved {@link Product} entity
   * @throws EntityNotFoundException if the associated category is not found
   */
  @Override
  public Product save(Product productToCreate) {
    ProductEntity newProductEntity = ProductEntity.from(productToCreate);
    Optional<CategoryEntity> categoryEntityOpt = jpaCategoryRepository.findByPublicId(newProductEntity.getCategory().getPublicId());
    CategoryEntity categoryEntity = categoryEntityOpt.orElseThrow(() -> new EntityNotFoundException(String.format("No category found with Id %s", productToCreate.getCategory().getPublicId())));
    newProductEntity.setCategory(categoryEntity);
    ProductEntity savedProductEntity = jpaProductRepository.save(newProductEntity);

    saveAllPictures(productToCreate.getPictures(), savedProductEntity);

    return ProductEntity.to(savedProductEntity);
  }

  /**
   * Saves all pictures associated with a product.
   *
   * @param pictures the list of pictures to save
   * @param newProductEntity the product entity to associate the pictures with
   */
  private void saveAllPictures(List<Picture> pictures, ProductEntity newProductEntity) {
    Set<PictureEntity> picturesEntities = PictureEntity.from(pictures);

    for (PictureEntity picturesEntity : picturesEntities) {
      picturesEntity.setProduct(newProductEntity);
    }

    jpaProductPictureRepository.saveAll(picturesEntities);
  }

  /**
   * Retrieves a paginated list of all products.
   *
   * @param pageable pagination information
   * @return a page of {@link Product} entities
   */
  @Override
  public Page<Product> findAll(Pageable pageable) {
    return jpaProductRepository.findAll(pageable).map(ProductEntity::to);
  }

  /**
   * Deletes a product by its public ID.
   *
   * @param publicId the unique public ID of the product to delete
   * @return the number of entities deleted (should be 0 or 1)
   */
  @Override
  public int delete(PublicId publicId) {
    return jpaProductRepository.deleteByPublicId(publicId.value());
  }

  /**
   * Retrieves a paginated list of featured products.
   *
   * @param pageable pagination information
   * @return a page of featured {@link Product} entities
   */
  @Override
  public Page<Product> findAllFeaturedProduct(Pageable pageable) {
    return jpaProductRepository.findAllByFeaturedTrue(pageable).map(ProductEntity::to);
  }

  /**
   * Retrieves a product by its public ID.
   *
   * @param publicId the unique public ID of the product
   * @return an {@link Optional} containing the found product, or empty if not found
   */
  @Override
  public Optional<Product> findOne(PublicId publicId) {
    return jpaProductRepository.findByPublicId(publicId.value()).map(ProductEntity::to);
  }

  /**
   * Retrieves a paginated list of products by category, excluding a specific product.
   *
   * @param pageable          pagination information
   * @param categoryPublicId  the public ID of the category
   * @param productPublicId   the public ID of the product to exclude
   * @return a page of {@link Product} entities
   */
  @Override
  public Page<Product> findByCategoryExcludingOne(Pageable pageable, PublicId categoryPublicId, PublicId productPublicId) {
    return jpaProductRepository.findByCategoryPublicIdAndPublicIdNot(pageable, categoryPublicId.value(), productPublicId.value())
      .map(ProductEntity::to);
  }

  /**
   * Retrieves a paginated list of products by category and size.
   *
   * @param pageable     pagination information
   * @param filterQuery  the filter query containing category and sizes
   * @return a page of {@link Product} entities
   */
  @Override
  public Page<Product> findByCategoryAndSize(Pageable pageable, FilterQuery filterQuery) {
    return jpaProductRepository.findByCategoryPublicIdAndSizesIn(
      pageable, filterQuery.categoryId().value(), filterQuery.sizes()
    ).map(ProductEntity::to);
  }

  /**
   * Retrieves a list of products by their public IDs.
   *
   * @param publicIds the list of public IDs to look for
   * @return a list of {@link Product} entities
   */
  @Override
  public List<Product> findByPublicIds(List<PublicId> publicIds) {
    List<UUID> publicIdsUUID = publicIds.stream().map(PublicId::value).toList();
    return jpaProductRepository.findAllByPublicIdIn(publicIdsUUID)
      .stream().map(ProductEntity::to).toList();
  }

  /**
   * Updates the stock quantity of a product.
   *
   * @param productPublicId the public ID of the product
   * @param quantity        the quantity to update
   */
  @Override
  public void updateQuantity(ProductPublicId productPublicId, long quantity) {
    jpaProductRepository.updateQuantity(productPublicId.value(), quantity);
  }
}
