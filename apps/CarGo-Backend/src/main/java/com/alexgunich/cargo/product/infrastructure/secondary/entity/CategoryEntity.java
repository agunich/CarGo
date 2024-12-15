package com.alexgunich.cargo.product.infrastructure.secondary.entity;

import com.alexgunich.cargo.product.domain.aggregate.Category;
import com.alexgunich.cargo.product.domain.aggregate.CategoryBuilder;
import com.alexgunich.cargo.product.domain.vo.CategoryName;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import com.alexgunich.cargo.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a category entity in the database.
 * <p>
 * This entity maps to the "product_category" table and contains information about a product
 * category, including its name and unique public ID. It also maintains a relationship with
 * associated products.
 * </p>
 */
@Entity
@Table(name = "product_category")
@Builder
public class CategoryEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorySequence")
  @SequenceGenerator(name = "categorySequence", sequenceName = "product_category_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "public_id", unique = true, nullable = false)
  private UUID publicId;

  @OneToMany(mappedBy = "category")
  private Set<ProductEntity> products;

  /**
   * Default constructor.
   */
  public CategoryEntity() {
  }

  /**
   * Constructs a new CategoryEntity with the specified attributes.
   *
   * @param id       the unique identifier of the category
   * @param name     the name of the category
   * @param publicId the unique public ID of the category
   * @param products the set of products associated with this category
   */
  public CategoryEntity(Long id, String name, UUID publicId, Set<ProductEntity> products) {
    this.id = id;
    this.name = name;
    this.publicId = publicId;
    this.products = products;
  }

  /**
   * Converts a domain category to a CategoryEntity.
   *
   * @param category the domain category to convert
   * @return the corresponding CategoryEntity
   */
  public static CategoryEntity from(Category category) {
    CategoryEntityBuilder categoryEntityBuilder = CategoryEntityBuilder.categoryEntity();

    if (category.getDbId() != null) {
      categoryEntityBuilder.id(category.getDbId());
    }

    return categoryEntityBuilder
      .name(category.getName().value())
      .publicId(category.getPublicId().value())
      .build();
  }

  /**
   * Converts a CategoryEntity to a domain category.
   *
   * @param categoryEntity the CategoryEntity to convert
   * @return the corresponding domain category
   */
  public static Category to(CategoryEntity categoryEntity) {
    return CategoryBuilder.category()
      .dbId(categoryEntity.getId())
      .name(new CategoryName(categoryEntity.getName()))
      .publicId(new PublicId(categoryEntity.getPublicId()))
      .build();
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  public Set<ProductEntity> getProducts() {
    return products;
  }

  public void setProducts(Set<ProductEntity> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof CategoryEntity that)) return false;
    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }
}
