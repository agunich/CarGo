package com.alexgunich.cargo.product.infrastructure.primary;

import com.alexgunich.cargo.product.application.ProductsApplicationService;
import com.alexgunich.cargo.product.domain.aggregate.FilterQueryBuilder;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.vo.ProductSize;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing product shop operations.
 * <p>
 * This controller provides endpoints to retrieve featured products, find individual products,
 * find related products, and filter products based on categories and sizes.
 * </p>
 */
@RestController
@RequestMapping("/api/products-shop")
public class ProductsShopResource {

  private final ProductsApplicationService productsApplicationService;

  /**
   * Constructs a new ProductsShopResource with the given application service.
   *
   * @param productsApplicationService the service for handling product operations
   */
  public ProductsShopResource(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
  }

  /**
   * Retrieves all featured products with pagination.
   *
   * @param pageable the pagination information
   * @return ResponseEntity containing a page of featured products
   */
  @GetMapping("/featured")
  public ResponseEntity<Page<RestProduct>> getAllFeatured(Pageable pageable) {
    Page<Product> products = productsApplicationService.getFeaturedProducts(pageable);

    PageImpl<RestProduct> restProducts = new PageImpl<>(
      products.getContent().stream().map(RestProduct::fromDomain).toList(),
      pageable,
      products.getTotalElements()
    );
    return ResponseEntity.ok(restProducts);
  }

  /**
   * Retrieves a single product by its public ID.
   *
   * @param id the UUID of the product to be retrieved
   * @return ResponseEntity containing the requested product or a bad request response if not found
   */
  @GetMapping("/find-one")
  public ResponseEntity<RestProduct> getOne(@RequestParam("publicId") UUID id) {
    Optional<Product> productOpt = productsApplicationService.findOne(new PublicId(id));

    return productOpt.map(product -> ResponseEntity.ok(RestProduct.fromDomain(product)))
      .orElseGet(() -> ResponseEntity.badRequest().build());
  }

  /**
   * Retrieves related products based on the given product's public ID.
   *
   * @param pageable the pagination information
   * @param id      the UUID of the product for which related products are to be found
   * @return ResponseEntity containing a page of related products or a bad request response if not found
   */
  @GetMapping("/related")
  public ResponseEntity<Page<RestProduct>> findRelated(Pageable pageable,
                                                       @RequestParam("publicId") UUID id) {
    try {
      Page<Product> products = productsApplicationService.findRelated(pageable, new PublicId(id));
      PageImpl<RestProduct> restProducts = new PageImpl<>(
        products.getContent().stream().map(RestProduct::fromDomain).toList(),
        pageable,
        products.getTotalElements()
      );
      return ResponseEntity.ok(restProducts);
    } catch (EntityNotFoundException enfe) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Filters products based on category and optional size criteria.
   *
   * @param pageable   the pagination information
   * @param categoryId the UUID of the category to filter by
   * @param productSizes optional list of product sizes to filter by
   * @return ResponseEntity containing a page of filtered products
   */
  @GetMapping("/filter")
  public ResponseEntity<Page<RestProduct>> filter(Pageable pageable,
                                                  @RequestParam("categoryId") UUID categoryId,
                                                  @RequestParam(value = "productSizes", required = false) List<ProductSize> productSizes) {
    FilterQueryBuilder filterQueryBuilder = FilterQueryBuilder.filterQuery().categoryId(new PublicId(categoryId));

    if (productSizes != null) {
      filterQueryBuilder.sizes(productSizes);
    }

    Page<Product> products = productsApplicationService.filter(pageable, filterQueryBuilder.build());
    PageImpl<RestProduct> restProducts = new PageImpl<>(
      products.getContent().stream().map(RestProduct::fromDomain).toList(),
      pageable,
      products.getTotalElements()
    );
    return ResponseEntity.ok(restProducts);
  }
}
