package com.alexgunich.cargo.product.domain.aggregate;

import com.alexgunich.cargo.product.domain.vo.*;
import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

/**
 * Represents a product in the system with various attributes, including brand, color, description, name,
 * price, size, category, pictures, stock information, and more.
 * <p>
 * The {@link Product} class encapsulates all the necessary data related to a product, including its details
 * such as brand, size, and price, as well as the stock status and associated images.
 * </p>
 */
@Builder
public class Product {

  private final ProductBrand productBrand;
  private final ProductColor color;
  private final ProductDescription description;
  private final ProductName name;
  private final ProductPrice price;
  private final ProductSize size;
  private final Category category;
  private final List<Picture> pictures;

  private Long dbId;
  private boolean featured;
  private PublicId publicId;
  private int nbInStock;

  /**
   * Constructs a new {@link Product} instance.
   *
   * @param productBrand the brand of the product
   * @param color the color of the product
   * @param description the description of the product
   * @param name the name of the product
   * @param price the price of the product
   * @param size the size of the product
   * @param category the category to which the product belongs
   * @param pictures the pictures associated with the product
   * @param dbId the database identifier of the product (nullable)
   * @param featured a boolean flag indicating whether the product is featured
   * @param publicId the public unique identifier for the product
   * @param nbInStock the number of items in stock for the product
   * @throws IllegalArgumentException if any mandatory fields are null
   */
  public Product(ProductBrand productBrand, ProductColor color, ProductDescription description,
                 ProductName name, ProductPrice price, ProductSize size, Category category,
                 List<Picture> pictures, Long dbId, boolean featured, PublicId publicId,
                 int nbInStock) {
    this.productBrand = productBrand;
    this.color = color;
    this.description = description;
    this.name = name;
    this.price = price;
    this.size = size;
    this.category = category;
    this.pictures = pictures;
    this.dbId = dbId;
    this.featured = featured;
    this.publicId = publicId;
    this.nbInStock = nbInStock;
    assertMandatoryFields(productBrand, color, description, name, price, size, category, pictures, featured, nbInStock);
  }

  /**
   * Validates that the mandatory fields are non-null.
   *
   * @param brand the brand of the product
   * @param color the color of the product
   * @param description the description of the product
   * @param name the name of the product
   * @param price the price of the product
   * @param size the size of the product
   * @param category the category of the product
   * @param pictures the pictures associated with the product
   * @param featured the featured status of the product
   * @param nbInStock the stock quantity of the product
   * @throws IllegalArgumentException if any field is null
   */
  private void assertMandatoryFields(ProductBrand brand, ProductColor color, ProductDescription description,
                                     ProductName name, ProductPrice price, ProductSize size, Category category,
                                     List<Picture> pictures, boolean featured, int nbInStock) {
    Assert.notNull("brand", brand);
    Assert.notNull("color", color);
    Assert.notNull("description", description);
    Assert.notNull("name", name);
    Assert.notNull("price", price);
    Assert.notNull("size", size);
    Assert.notNull("category", category);
    Assert.notNull("pictures", pictures);
    Assert.notNull("featured", featured);
    Assert.notNull("nbInStock", nbInStock);
  }

  /**
   * Initializes the default fields, particularly the publicId.
   * This method is typically called to ensure that the product has a unique identifier.
   */
  public void initDefaultFields() {
    this.publicId = new PublicId(UUID.randomUUID());
  }

  /**
   * Retrieves the brand of the product.
   *
   * @return the product's brand
   */
  public ProductBrand getProductBrand() {
    return productBrand;
  }

  /**
   * Retrieves the color of the product.
   *
   * @return the product's color
   */
  public ProductColor getColor() {
    return color;
  }

  /**
   * Retrieves the description of the product.
   *
   * @return the product's description
   */
  public ProductDescription getDescription() {
    return description;
  }

  /**
   * Retrieves the name of the product.
   *
   * @return the product's name
   */
  public ProductName getName() {
    return name;
  }

  /**
   * Retrieves the price of the product.
   *
   * @return the product's price
   */
  public ProductPrice getPrice() {
    return price;
  }

  /**
   * Retrieves the size of the product.
   *
   * @return the product's size
   */
  public ProductSize getSize() {
    return size;
  }

  /**
   * Retrieves the category to which the product belongs.
   *
   * @return the product's category
   */
  public Category getCategory() {
    return category;
  }

  /**
   * Retrieves the pictures associated with the product.
   *
   * @return a list of pictures of the product
   */
  public List<Picture> getPictures() {
    return pictures;
  }

  /**
   * Retrieves the database identifier of the product.
   *
   * @return the product's database ID
   */
  public Long getDbId() {
    return dbId;
  }

  /**
   * Indicates whether the product is featured.
   *
   * @return {@code true} if the product is featured, {@code false} otherwise
   */
  public boolean getFeatured() {
    return featured;
  }

  /**
   * Retrieves the public unique identifier of the product.
   *
   * @return the product's public identifier
   */
  public PublicId getPublicId() {
    return publicId;
  }

  /**
   * Retrieves the number of items of this product in stock.
   *
   * @return the number of items in stock
   */
  public int getNbInStock() {
    return nbInStock;
  }
}
