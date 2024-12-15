package com.alexgunich.cargo.product.infrastructure.primary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.alexgunich.cargo.product.application.ProductsApplicationService;
import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import com.alexgunich.cargo.product.infrastructure.primary.exceptions.EntityCreationFailed;
import com.alexgunich.cargo.product.infrastructure.primary.exceptions.MultipartPictureException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * REST controller for managing product administration tasks.
 * <p>
 * This controller provides endpoints for creating, deleting, and retrieving
 * products. It requires admin role authorization for all operations.
 * </p>
 */
@RestController
@RequestMapping("/api/products")
public class ProductsAdminResource {

  private static final Logger log = LoggerFactory.getLogger(ProductsAdminResource.class);
  private final ProductsApplicationService productsApplicationService;

  public static final String ROLE_ADMIN = "ROLE_ADMIN";

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Constructs a new ProductsAdminResource with the given application service.
   *
   * @param productsApplicationService the service for handling product operations
   */
  public ProductsAdminResource(ProductsApplicationService productsApplicationService) {
    this.productsApplicationService = productsApplicationService;
  }

  /**
   * Saves a new product with associated pictures.
   *
   * @param request      the multipart request containing product data and files
   * @param productRaw   the raw JSON representation of the product
   * @return ResponseEntity containing the created product
   * @throws JsonProcessingException if the product JSON cannot be processed
   */
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RestProduct> save(MultipartHttpServletRequest request,
                                          @RequestPart("dto") String productRaw) throws JsonProcessingException {
    List<RestPicture> pictures = request.getFileMap()
      .values()
      .stream()
      .map(mapMultipartFileToRestPicture())
      .toList();

    RestProduct restProduct = objectMapper.readValue(productRaw, RestProduct.class);
    restProduct.addPictureAttachment(pictures);
    Product newProduct = RestProduct.toDomain(restProduct);
    Product product = productsApplicationService.createProduct(newProduct);
    return ResponseEntity.ok(RestProduct.fromDomain(product));
  }

  private Function<MultipartFile, RestPicture> mapMultipartFileToRestPicture() {
    return multipartFile -> {
      try {
        return new RestPicture(multipartFile.getBytes(), multipartFile.getContentType());
      } catch (IOException ieo) {
        throw new MultipartPictureException(String.format("Cannot parse multipart file : %s", multipartFile.getOriginalFilename()));
      }
    };
  }

  /**
   * Deletes a product by its public ID.
   *
   * @param id the UUID of the product to be deleted
   * @return ResponseEntity containing the public ID of the deleted product
   * @throws EntityNotFoundException if the product with the given ID does not exist
   */
  @DeleteMapping
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<UUID> delete(@RequestParam("publicId") UUID id) {
    try {
      PublicId deletedProductUUID = productsApplicationService.deleteProduct(new PublicId(id));
      return ResponseEntity.ok(deletedProductUUID.value());
    } catch (EntityNotFoundException enfee) {
      log.error("Could not delete category with id {}", id, enfee);
      ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, enfee.getMessage());
      return ResponseEntity.of(problemDetail).build();
    }
  }

  /**
   * Retrieves all products with pagination.
   *
   * @param pageable the pagination information
   * @return ResponseEntity containing a page of products
   */
  @GetMapping
  @PreAuthorize("hasAnyRole('" + ROLE_ADMIN + "')")
  public ResponseEntity<Page<RestProduct>> getAll(Pageable pageable) {
    Page<Product> products = productsApplicationService.findAllProduct(pageable);

    Page<RestProduct> restProducts = new PageImpl<>(
      products.getContent().stream().map(RestProduct::fromDomain).collect(Collectors.toList()),
      pageable,
      products.getTotalElements()
    );
    return ResponseEntity.ok(restProducts);
  }
}
