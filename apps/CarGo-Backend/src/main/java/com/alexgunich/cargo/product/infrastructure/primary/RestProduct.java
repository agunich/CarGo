package com.alexgunich.cargo.product.infrastructure.primary;

import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.aggregate.ProductBuilder;
import com.alexgunich.cargo.product.domain.vo.*;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

/**
 * Represents a RESTful product object.
 * <p>
 * This class encapsulates the data required to represent a product in the API,
 * including its attributes such as brand, color, description, price, and associated
 * pictures. It provides methods to convert to and from the domain model.
 * </p>
 */
@Builder
public class RestProduct {

  private String brand;
  private String color;
  private String description;
  private String name;
  private double price;
  private ProductSize size;
  private RestCategory category;
  private boolean featured;
  private List<RestPicture> pictures;
  private UUID publicId;
  private int nbInStock;

  /**
   * Default constructor.
   */
  public RestProduct() {
  }

  /**
   * Constructs a new RestProduct with the specified attributes.
   *
   * @param brand       the brand of the product
   * @param color       the color of the product
   * @param description a description of the product
   * @param name        the name of the product
   * @param price       the price of the product
   * @param size        the size of the product
   * @param category    the category of the product
   * @param featured    whether the product is featured
   * @param pictures    the list of pictures associated with the product
   * @param publicId    the unique identifier of the product
   * @param nbInStock   the number of items in stock
   */
  public RestProduct(String brand, String color, String description,
                     String name, double price, ProductSize size,
                     RestCategory category, boolean featured,
                     List<RestPicture> pictures, UUID publicId, int nbInStock) {
    this.brand = brand;
    this.color = color;
    this.description = description;
    this.name = name;
    this.price = price;
    this.size = size;
    this.category = category;
    this.featured = featured;
    this.pictures = pictures;
    this.publicId = publicId;
    this.nbInStock = nbInStock;
  }

  /**
   * Adds picture attachments to the product.
   *
   * @param pictures the list of pictures to be added
   */
  public void addPictureAttachment(List<RestPicture> pictures) {
    this.pictures.addAll(pictures);
  }

  /**
   * Converts this REST product to a domain product.
   *
   * @param restProduct the REST product to convert
   * @return the corresponding domain product
   */
  public static Product toDomain(RestProduct restProduct) {
    ProductBuilder productBuilder = ProductBuilder.product()
      .productBrand(new ProductBrand(restProduct.getBrand()))
      .color(new ProductColor(restProduct.getColor()))
      .description(new ProductDescription(restProduct.getDescription()))
      .name(new ProductName(restProduct.getName()))
      .price(new ProductPrice(restProduct.getPrice()))
      .size(restProduct.getSize())
      .category(RestCategory.toDomain(restProduct.getCategory()))
      .featured(restProduct.isFeatured())
      .nbInStock(restProduct.getNbInStock());

    if (restProduct.publicId != null) {
      productBuilder.publicId(new PublicId(restProduct.publicId));
    }

    if (restProduct.pictures != null && !restProduct.pictures.isEmpty()) {
      productBuilder.pictures(RestPicture.toDomain(restProduct.getPictures()));
    }

    return productBuilder.build();
  }

  /**
   * Converts a domain product to a REST product.
   *
   * @param product the domain product to convert
   * @return the corresponding REST product
   */
  public static RestProduct fromDomain(Product product) {
    return RestProductBuilder.restProduct()
      .brand(product.getProductBrand().value())
      .color(product.getColor().value())
      .description(product.getDescription().value())
      .name(product.getName().value())
      .price(product.getPrice().value())
      .featured(product.getFeatured())
      .category(RestCategory.fromDomain(product.getCategory()))
      .size(product.getSize())
      .pictures(RestPicture.fromDomain(product.getPictures()))
      .publicId(product.getPublicId().value())
      .nbInStock(product.getNbInStock())
      .build();
  }

  // Getters and Setters

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public ProductSize getSize() {
    return size;
  }

  public void setSize(ProductSize size) {
    this.size = size;
  }

  public RestCategory getCategory() {
    return category;
  }

  public void setCategory(RestCategory category) {
    this.category = category;
  }

  public boolean isFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public List<RestPicture> getPictures() {
    return pictures;
  }

  public void setPictures(List<RestPicture> pictures) {
    this.pictures = pictures;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  public int getNbInStock() {
    return nbInStock;
  }

  public void setNbInStock(int nbInStock) {
    this.nbInStock = nbInStock;
  }
}
