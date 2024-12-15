package com.alexgunich.cargo.product.application;

import com.alexgunich.cargo.order.domain.order.aggregate.OrderProductQuantity;
import com.alexgunich.cargo.product.domain.aggregate.Category;
import com.alexgunich.cargo.product.domain.aggregate.FilterQuery;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.repository.CategoryRepository;
import com.alexgunich.cargo.product.domain.repository.ProductRepository;
import com.alexgunich.cargo.product.domain.service.CategoryCRUD;
import com.alexgunich.cargo.product.domain.service.ProductCRUD;
import com.alexgunich.cargo.product.domain.service.ProductShop;
import com.alexgunich.cargo.product.domain.service.ProductUpdater;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Application service for managing products and categories in the product catalog.
 * <p>
 * This service provides operations for creating, updating, deleting, and retrieving products and categories,
 * as well as fetching featured products, related products, and applying filters to the product catalog.
 * </p>
 */
@Service
public class ProductsApplicationService {

  private ProductCRUD productCRUD;
  private CategoryCRUD categoryCRUD;
  private ProductShop productShop;
  private ProductUpdater productUpdater;

  /**
   * Constructs a new instance of {@link ProductsApplicationService}.
   *
   * @param productRepository the repository used for product operations
   * @param categoryRepository the repository used for category operations
   */
  public ProductsApplicationService(ProductRepository productRepository, CategoryRepository categoryRepository) {
    this.productCRUD = new ProductCRUD(productRepository);
    this.categoryCRUD = new CategoryCRUD(categoryRepository);
    this.productShop = new ProductShop(productRepository);
    this.productUpdater = new ProductUpdater(productRepository);
  }

  /**
   * Creates a new product in the catalog.
   *
   * @param newProduct the product to be created
   * @return the created product
   */
  @Transactional
  public Product createProduct(Product newProduct) {
    return productCRUD.save(newProduct);
  }

  /**
   * Retrieves all products, paginated.
   *
   * @param pageable the pagination information
   * @return a page of products
   */
  @Transactional(readOnly = true)
  public Page<Product> findAllProduct(Pageable pageable) {
    return productCRUD.findAll(pageable);
  }

  /**
   * Deletes a product by its public ID.
   *
   * @param publicId the public ID of the product to delete
   * @return the public ID of the deleted product
   */
  @Transactional
  public PublicId deleteProduct(PublicId publicId) {
    return productCRUD.delete(publicId);
  }

  /**
   * Creates a new category in the catalog.
   *
   * @param category the category to be created
   * @return the created category
   */
  @Transactional
  public Category createCategory(Category category) {
    return categoryCRUD.save(category);
  }

  /**
   * Deletes a category by its public ID.
   *
   * @param publicId the public ID of the category to delete
   * @return the public ID of the deleted category
   */
  @Transactional
  public PublicId deleteCategory(PublicId publicId) {
    return categoryCRUD.delete(publicId);
  }

  /**
   * Retrieves all categories, paginated.
   *
   * @param pageable the pagination information
   * @return a page of categories
   */
  @Transactional(readOnly = true)
  public Page<Category> findAllCategory(Pageable pageable) {
    return categoryCRUD.findAll(pageable);
  }

  /**
   * Retrieves featured products, paginated.
   *
   * @param pageable the pagination information
   * @return a page of featured products
   */
  @Transactional(readOnly = true)
  public Page<Product> getFeaturedProducts(Pageable pageable) {
    return productShop.getFeaturedProducts(pageable);
  }

  /**
   * Retrieves a product by its public ID.
   *
   * @param id the public ID of the product
   * @return an optional containing the product if found, or empty if not
   */
  @Transactional(readOnly = true)
  public Optional<Product> findOne(PublicId id) {
    return productCRUD.findOne(id);
  }

  /**
   * Retrieves related products, paginated.
   *
   * @param pageable the pagination information
   * @param productPublicId the public ID of the product to find related products for
   * @return a page of related products
   */
  @Transactional(readOnly = true)
  public Page<Product> findRelated(Pageable pageable, PublicId productPublicId) {
    return productShop.findRelated(pageable, productPublicId);
  }

  /**
   * Filters products based on a filter query, paginated.
   *
   * @param pageable the pagination information
   * @param query the filter query
   * @return a page of filtered products
   */
  @Transactional(readOnly = true)
  public Page<Product> filter(Pageable pageable, FilterQuery query) {
    return productShop.filter(pageable, query);
  }

  /**
   * Retrieves products by a list of public IDs.
   *
   * @param publicIds the list of public IDs of the products
   * @return a list of products
   */
  @Transactional(readOnly = true)
  public List<Product> getProductsByPublicIdsIn(List<PublicId> publicIds) {
    return productCRUD.findAllByPublicIdIn(publicIds);
  }

  /**
   * Updates the quantities of products based on the order details.
   *
   * @param orderProductQuantities the list of order product quantities to update
   */
  @Transactional
  public void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities) {
    productUpdater.updateProductQuantity(orderProductQuantities);
  }
}
