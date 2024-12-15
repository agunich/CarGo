package com.alexgunich.cargo.product.infrastructure.secondary.entity;

import com.alexgunich.cargo.product.domain.aggregate.Product;
import com.alexgunich.cargo.product.domain.aggregate.ProductBuilder;
import com.alexgunich.cargo.product.domain.vo.*;
import com.alexgunich.cargo.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a product entity in the database.
 * <p>
 * This entity maps to the "product" table and contains information about a product,
 * including its brand, color, description, price, and associated pictures. It also
 * maintains a relationship with the product's category.
 * </p>
 */
@Entity
@Table(name = "product")
@Builder
public class ProductEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSequence")
  @SequenceGenerator(name = "productSequence", sequenceName = "product_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Column(name = "brand")
  private String brand;

  @Column(name = "color")
  private String color;

  @Column(name = "description")
  private String description;

  @Column(name = "name")
  private String name;

  @Column(name = "price")
  private double price;

  @Column(name = "featured")
  private boolean featured;

  @Enumerated(EnumType.STRING)
  @Column(name = "size")
  private ProductSize size;

  @Column(name = "publicId", unique = true)
  private UUID publicId;

  @Column(name = "nb_in_stock")
  private int nbInStock;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
  private Set<PictureEntity> pictures = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "category_fk", referencedColumnName = "id")
  private CategoryEntity category;

  /**
   * Default constructor.
   */
  public ProductEntity() {
  }

  /**
   * Constructs a new ProductEntity with the specified attributes.
   *
   * @param id          the unique identifier of the product
   * @param brand       the brand of the product
   * @param color       the color of the product
   * @param description a description of the product
   * @param name        the name of the product
   * @param price       the price of the product
   * @param featured    whether the product is featured
   * @param size        the size of the product
   * @param publicId    the unique public ID of the product
   * @param nbInStock   the number of items in stock
   * @param pictures    the set of pictures associated with the product
   * @param category    the associated category entity
   */
  public ProductEntity(Long id, String brand, String color, String description, String name, double price, boolean featured, ProductSize size, UUID publicId, int nbInStock, Set<PictureEntity> pictures, CategoryEntity category) {
    this.id = id;
    this.brand = brand;
    this.color = color;
    this.description = description;
    this.name = name;
    this.price = price;
    this.featured = featured;
    this.size = size;
    this.publicId = publicId;
    this.nbInStock = nbInStock;
    this.pictures = pictures;
    this.category = category;
  }

  /**
   * Converts a domain product to a ProductEntity.
   *
   * @param product the domain product to convert
   * @return the corresponding ProductEntity
   */
  public static ProductEntity from(Product product) {
    ProductEntityBuilder productEntityBuilder = ProductEntityBuilder.productEntity();

    if (product.getDbId() != null) {
      productEntityBuilder.id(product.getDbId());
    }

    return productEntityBuilder
      .brand(product.getProductBrand().value())
      .color(product.getColor().value())
      .description(product.getDescription().value())
      .name(product.getName().value())
      .price(product.getPrice().value())
      .size(product.getSize())
      .publicId(product.getPublicId().value())
      .category(CategoryEntity.from(product.getCategory()))
      .pictures(PictureEntity.from(product.getPictures()))
      .featured(product.getFeatured())
      .nbInStock(product.getNbInStock())
      .build();
  }

  /**
   * Converts a ProductEntity to a domain product.
   *
   * @param productEntity the ProductEntity to convert
   * @return the corresponding domain product
   */
  public static Product to(ProductEntity productEntity) {
    return ProductBuilder.product()
      .productBrand(new ProductBrand(productEntity.getBrand()))
      .color(new ProductColor(productEntity.getColor()))
      .description(new ProductDescription(productEntity.getDescription()))
      .name(new ProductName(productEntity.getName()))
      .price(new ProductPrice(productEntity.getPrice()))
      .size(productEntity.getSize())
      .publicId(new PublicId(productEntity.getPublicId()))
      .category(CategoryEntity.to(productEntity.getCategory()))
      .pictures(PictureEntity.to(productEntity.getPictures()))
      .featured(productEntity.getFeatured())
      .nbInStock(productEntity.getNbInStock())
      .build();
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public boolean getFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
  }

  public ProductSize getSize() {
    return size;
  }

  public void setSize(ProductSize size) {
    this.size = size;
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

  public Set<PictureEntity> getPictures() {
    return pictures;
  }

  public void setPictures(Set<PictureEntity> pictures) {
    this.pictures = pictures;
  }

  public CategoryEntity getCategory() {
    return category;
  }

  public void setCategory(CategoryEntity category) {
    this.category = category;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProductEntity that)) return false;
    return featured == that.featured &&
      Double.compare(that.price, price) == 0 &&
      Objects.equals(brand, that.brand) &&
      Objects.equals(color, that.color) &&
      Objects.equals(description, that.description) &&
      Objects.equals(name, that.name) &&
      size == that.size &&
      Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, color, description, name, price, featured, size, publicId);
  }
}
