package com.alexgunich.cargo.product.domain.aggregate;

import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

/**
 * Represents a picture associated with a product.
 * <p>
 * The picture consists of the raw file data in the form of a byte array and the MIME type
 * that describes the file's format (e.g., image/jpeg, image/png).
 * </p>
 */
@Builder
public record Picture(byte[] file, String mimeType) {

  /**
   * Constructs a new {@link Picture} instance.
   * <p>
   * The constructor ensures that both the file and the MIME type are non-null.
   * </p>
   *
   * @param file the raw byte array representing the picture file
   * @param mimeType the MIME type of the picture file (e.g., "image/jpeg", "image/png")
   * @throws IllegalArgumentException if either file or mimeType is null
   */
  public Picture {
    Assert.notNull("file", file);
    Assert.notNull("mimeType", mimeType);
  }
}
