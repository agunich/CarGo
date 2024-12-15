package com.alexgunich.cargo.product.domain.aggregate;

import com.alexgunich.cargo.product.domain.vo.ProductBrand;
import com.alexgunich.cargo.product.domain.vo.ProductName;
import com.alexgunich.cargo.product.domain.vo.ProductPrice;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

/**
 * Represents a product in the shopping cart with essential details such as the product's name, price,
 * brand, picture, and public ID.
 * <p>
 * The {@link ProductCart} class is used to encapsulate the minimal data required for a product in a cart,
 * such as its name, price, brand, image, and identifier. It provides a simplified version of a product
 * suitable for use in the cart context, without requiring all the details of a full product.
 * </p>
 */
@Builder
public class ProductCart {

  private ProductName name;
  private ProductPrice price;
  private ProductBrand brand;
  private Picture picture;
  private PublicId publicId;

  /**
   * Default constructor for {@link ProductCart}.
   * <p>
   * This constructor is used by the builder to create an empty {@link ProductCart} object, which can be
   * populated later.
   * </p>
   */
  public ProductCart() {
  }

  /**
   * Constructs a new {@link ProductCart} with the provided values.
   *
   * @param name the name of the product
   * @param price the price of the product
   * @param brand the brand of the product
   * @param picture the picture of the product
   * @param publicId the public identifier for the product
   * @throws IllegalArgumentException if any of the fields are null
   */
  public ProductCart(ProductName name, ProductPrice price, ProductBrand brand,
                     Picture picture, PublicId publicId) {
    assertFields(name, price, brand, picture, publicId);
    this.name = name;
    this.price = price;
    this.brand = brand;
    this.picture = picture;
    this.publicId = publicId;
  }

  /**
   * Asserts that the fields of the product are not null.
   *
   * @param name the name of the product
   * @param price the price of the product
   * @param brand the brand of the product
   * @param picture the picture of the product
   * @param publicId the public identifier of the product
   * @throws IllegalArgumentException if any of the fields are null
   */
  private void assertFields(ProductName name, ProductPrice price, ProductBrand brand,
                            Picture picture, PublicId publicId) {
    Assert.notNull("brand", brand);
    Assert.notNull("name", name);
    Assert.notNull("price", price);
    Assert.notNull("picture", picture);
    Assert.notNull("publicId", publicId);
  }

  /**
   * Converts a full {@link Product} object into a {@link ProductCart} object.
   * <p>
   * This method extracts the necessary information from a {@link Product} instance to create a
   * {@link ProductCart}, which is a simplified representation used in the cart.
   * </p>
   *
   * @param product the full product object to convert
   * @return a {@link ProductCart} instance
   * @throws IllegalArgumentException if no pictures are available in the product
   */
  public static ProductCart from(Product product) {
    return ProductCartBuilder.productCart()
      .name(product.getName())
      .price(product.getPrice())
      .brand(product.getProductBrand())
      .picture(product.getPictures().stream().findFirst().orElseThrow())
      .publicId(product.getPublicId())
      .build();
  }

  /**
   * Retrieves the name of the product in the cart.
   *
   * @return the name of the product
   */
  public ProductName getName() {
    return name;
  }

  /**
   * Retrieves the price of the product in the cart.
   *
   * @return the price of the product
   */
  public ProductPrice getPrice() {
    return price;
  }

  /**
   * Retrieves the brand of the product in the cart.
   *
   * @return the brand of the product
   */
  public ProductBrand getBrand() {
    return brand;
  }

  /**
   * Retrieves the picture of the product in the cart.
   *
   * @return the picture of the product
   */
  public Picture getPicture() {
    return picture;
  }

  /**
   * Retrieves the public identifier of the product in the cart.
   *
   * @return the public identifier of the product
   */
  public PublicId getPublicId() {
    return publicId;
  }
}
