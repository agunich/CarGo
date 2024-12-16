package com.alexgunich.cargo.product.infrastructure.primary;

import com.alexgunich.cargo.product.application.ProductsApplicationService;
import com.alexgunich.cargo.product.domain.aggregate.Category;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

import static com.alexgunich.cargo.product.infrastructure.primary.ProductsAdminResource.ROLE_ADMIN;

/**
 * REST controller for managing product categories.
 * <p>
 * This controller provides endpoints to create, delete, and retrieve
 * product categories. It requires admin role authorization for
 * creating and deleting categories.
 * </p>
 */
@RestController
@RequestMapping("/api/categories")
public class CategoriesResource {

  private static final Logger log = LoggerFactory.getLogger(CategoriesResource.class);
  private final ProductsApplicationService productsApplicationService;

  /**
   * Constructs a new CategoriesResource with the given application service.
   *
   * @param productsApplicationService the service for handling category operations
   */
  public CategoriesResource(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
  }

  /**
   * Saves a new product category.
   *
   * @param restCategory the category to be created
   * @return ResponseEntity containing the created category
   */
  @PostMapping
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<RestCategory> save(@RequestBody RestCategory restCategory) {
    Category categoryDomain = RestCategory.toDomain(restCategory);
    Category categorySaved = productsApplicationService.createCategory(categoryDomain);
    return ResponseEntity.ok(RestCategory.fromDomain(categorySaved));
  }

  /**
   * Deletes a product category by its public ID.
   *
   * @param publicId the UUID of the category to be deleted
   * @return ResponseEntity containing the public ID of the deleted category
   * @throws EntityNotFoundException if the category with the given ID does not exist
   */
  @DeleteMapping
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<UUID> delete(UUID publicId) {
    try {
      PublicId deletedCategoryPublicId = productsApplicationService.deleteCategory(new PublicId(publicId));
      return ResponseEntity.ok(deletedCategoryPublicId.value());
    } catch (EntityNotFoundException enff) {
      log.error("Could not delete category with id {}", publicId, enff);
      ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, enff.getMessage());
      return ResponseEntity.of(problemDetail).build();
    }
  }

  /**
   * Retrieves all product categories with pagination.
   *
   * @param pageable the pagination information
   * @return ResponseEntity containing a page of categories
   */
  @GetMapping
  public ResponseEntity<Page<RestCategory>> findAll(Pageable pageable) {
    Page<Category> categories = productsApplicationService.findAllCategory(pageable);
    PageImpl<RestCategory> restCategories = new PageImpl<>(
      categories.getContent().stream().map(RestCategory::fromDomain).toList(),
      pageable,
      categories.getTotalElements()
    );
    return ResponseEntity.ok(restCategories);
  }

}
