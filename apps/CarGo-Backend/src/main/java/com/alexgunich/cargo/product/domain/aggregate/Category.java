package com.alexgunich.cargo.product.domain.aggregate;

import com.alexgunich.cargo.product.domain.vo.CategoryName;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

/**
 * Represents a product category in the catalog.
 * <p>
 * A category is defined by a name and has a unique identifier (`PublicId`) and an optional database identifier (`dbId`).
 * </p>
 */
@Builder
public class Category {

  private final CategoryName name;

  private Long dbId;
  private PublicId publicId;

  /**
   * Constructs a new Category with the specified name, database ID, and public ID.
   *
   * @param name the name of the category
   * @param dbId the database ID of the category (nullable)
   * @param publicId the unique public ID of the category
   */
  public Category(CategoryName name, Long dbId, PublicId publicId) {
    assertMandatoryFields(name);
    this.name = name;
    this.dbId = dbId;
    this.publicId = publicId;
  }

  /**
   * Asserts that the required fields for a Category (such as the name) are provided and not null.
   *
   * @param categoryName the name of the category to be validated
   * @throws IllegalArgumentException if the name is null
   */
  private void assertMandatoryFields(CategoryName categoryName) {
    Assert.notNull("name", categoryName);
  }

  /**
   * Initializes the default fields for the category, particularly generating a random public ID.
   * This method is typically called when creating a new category without an existing public ID.
   */
  public void initDefaultFields() {
    this.publicId = new PublicId(UUID.randomUUID());
  }

  /**
   * Retrieves the name of the category.
   *
   * @return the name of the category
   */
  public CategoryName getName() {
    return name;
  }

  /**
   * Retrieves the database ID of the category.
   *
   * @return the database ID of the category, or null if not set
   */
  public Long getDbId() {
    return dbId;
  }

  /**
   * Retrieves the unique public ID of the category.
   *
   * @return the public ID of the category
   */
  public PublicId getPublicId() {
    return publicId;
  }
}
