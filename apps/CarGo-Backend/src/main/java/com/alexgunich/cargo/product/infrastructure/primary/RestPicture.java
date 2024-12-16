package com.alexgunich.cargo.product.infrastructure.primary;

import com.alexgunich.cargo.product.domain.aggregate.Picture;
import com.alexgunich.cargo.product.domain.aggregate.PictureBuilder;
import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a RESTful picture object.
 * <p>
 * This record encapsulates the data required to represent a picture in the API,
 * including the file data and its MIME type. It provides methods for converting
 * to and from the domain model.
 * </p>
 *
 * @param file     the byte array representing the picture file; must not be null
 * @param mimeType the MIME type of the picture; must not be null
 * @throws IllegalArgumentException if the file or mimeType is null
 */
@Builder
public record RestPicture(byte[] file,
                          String mimeType) {

  public RestPicture {
    Assert.notNull("file", file);
    Assert.notNull("mimeType", mimeType);
  }

  /**
   * Converts this REST picture to a domain picture.
   *
   * @param restPicture the REST picture to convert
   * @return the corresponding domain picture
   */
  public static Picture toDomain(RestPicture restPicture) {
    return PictureBuilder
      .picture()
      .file(restPicture.file())
      .mimeType(restPicture.mimeType())
      .build();
  }

  /**
   * Converts a domain picture to a REST picture.
   *
   * @param picture the domain picture to convert
   * @return the corresponding REST picture
   */
  public static RestPicture fromDomain(Picture picture) {
    return RestPictureBuilder.restPicture()
      .file(picture.file())
      .mimeType(picture.mimeType())
      .build();
  }

  /**
   * Converts a list of REST pictures to a list of domain pictures.
   *
   * @param restPictures the list of REST pictures to convert
   * @return the corresponding list of domain pictures
   */
  public static List<Picture> toDomain(List<RestPicture> restPictures) {
    return restPictures.stream().map(RestPicture::toDomain).collect(Collectors.toList());
  }

  /**
   * Converts a list of domain pictures to a list of REST pictures.
   *
   * @param pictures the list of domain pictures to convert
   * @return the corresponding list of REST pictures
   */
  public static List<RestPicture> fromDomain(List<Picture> pictures) {
    return pictures.stream().map(RestPicture::fromDomain).collect(Collectors.toList());
  }
}
