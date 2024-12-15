package com.alexgunich.cargo.product.infrastructure.secondary.entity;

import com.alexgunich.cargo.product.domain.aggregate.Picture;
import com.alexgunich.cargo.product.domain.aggregate.PictureBuilder;
import com.alexgunich.cargo.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a picture entity in the database.
 * <p>
 * This entity maps to the "product_picture" table and contains information about a
 * product's pictures, including the image file and its MIME type. It also maintains a
 * relationship with the associated product.
 * </p>
 */
@Entity
@Table(name = "product_picture")
@Builder
public class PictureEntity extends AbstractAuditingEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pictureSequence")
  @SequenceGenerator(name = "pictureSequence", sequenceName = "product_picture_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  @Lob
  @Column(name = "file", nullable = false)
  private byte[] file;

  @Column(name = "file_content_type", nullable = false)
  private String mimeType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_fk", nullable = false)
  private ProductEntity product;

  /**
   * Default constructor.
   */
  public PictureEntity() {
  }

  /**
   * Constructs a new PictureEntity with the specified attributes.
   *
   * @param id       the unique identifier of the picture
   * @param file     the byte array representing the picture file
   * @param mimeType the MIME type of the picture
   * @param product  the associated product entity
   */
  public PictureEntity(Long id, byte[] file, String mimeType, ProductEntity product) {
    this.id = id;
    this.file = file;
    this.mimeType = mimeType;
    this.product = product;
  }

  /**
   * Converts a domain picture to a PictureEntity.
   *
   * @param picture the domain picture to convert
   * @return the corresponding PictureEntity
   */
  public static PictureEntity from(Picture picture) {
    return PictureEntityBuilder.pictureEntity()
      .file(picture.file())
      .mimeType(picture.mimeType())
      .build();
  }

  /**
   * Converts a PictureEntity to a domain picture.
   *
   * @param pictureEntity the PictureEntity to convert
   * @return the corresponding domain picture
   */
  public static Picture to(PictureEntity pictureEntity) {
    return PictureBuilder.picture()
      .file(pictureEntity.getFile())
      .mimeType(pictureEntity.getMimeType())
      .build();
  }

  /**
   * Converts a list of domain pictures to a set of PictureEntities.
   *
   * @param pictures the list of domain pictures to convert
   * @return a set of corresponding PictureEntities
   */
  public static Set<PictureEntity> from(List<Picture> pictures) {
    return pictures.stream().map(PictureEntity::from).collect(Collectors.toSet());
  }

  /**
   * Converts a set of PictureEntities to a list of domain pictures.
   *
   * @param pictureEntities the set of PictureEntities to convert
   * @return a list of corresponding domain pictures
   */
  public static List<Picture> to(Set<PictureEntity> pictureEntities) {
    return pictureEntities.stream().map(PictureEntity::to).collect(Collectors.toList());
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getFile() {
    return file;
  }

  public void setFile(byte[] file) {
    this.file = file;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public ProductEntity getProduct() {
    return product;
  }

  public void setProduct(ProductEntity product) {
    this.product = product;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PictureEntity that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
