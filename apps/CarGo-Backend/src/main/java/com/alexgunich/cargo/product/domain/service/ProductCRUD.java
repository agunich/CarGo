package com.alexgunich.cargo.product.domain.service;

import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.repository.ProductRepository;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service class for performing CRUD operations on Product entities.
 */
public class ProductCRUD {

  private final ProductRepository productRepository;

  /**
   * Constructs a ProductCRUD service with the specified ProductRepository.
   *
   * @param productRepository the repository to be used for product operations
   */
  public ProductCRUD(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  /**
   * Saves a new Product entity to the repository.
   * Initializes default fields of the product before saving.
   *
   * @param newProduct the Product entity to be saved
   * @return the saved Product entity
   */
  public Product save(Product newProduct) {
    newProduct.initDefaultFields();
    return productRepository.save(newProduct);
  }

  /**
   * Retrieves a paginated list of all Product entities.
   *
   * @param pageable the pagination information
   * @return a Page containing the list of Product entities
   */
  public Page<Product> findAll(Pageable pageable) {
    return productRepository.findAll(pageable);
  }

  /**
   * Deletes a Product entity with the specified public ID.
   *
   * @param id the PublicId of the Product to be deleted
   * @return the PublicId of the deleted Product
   * @throws EntityNotFoundException if no Product is found with the specified ID
   */
  public PublicId delete(PublicId id) {
    int nbOfRowsDeleted = productRepository.delete(id);
    if (nbOfRowsDeleted != 1) {
      throw new EntityNotFoundException(String.format("No Product deleted with id %s", id));
    }
    return id;
  }

  /**
   * Retrieves a Product entity by its public ID.
   *
   * @param publicId the PublicId of the Product to be retrieved
   * @return an Optional containing the found Product, or empty if none found
   */
  public Optional<Product> findOne(PublicId publicId) {
    return productRepository.findOne(publicId);
  }

  /**
   * Retrieves a list of Product entities by their public IDs.
   *
   * @param publicIds the list of PublicIds to search for
   * @return a list of Product entities matching the provided PublicIds
   */
  public List<Product> findAllByPublicIdIn(List<PublicId> publicIds) {
    return productRepository.findByPublicIds(publicIds);
  }
}
