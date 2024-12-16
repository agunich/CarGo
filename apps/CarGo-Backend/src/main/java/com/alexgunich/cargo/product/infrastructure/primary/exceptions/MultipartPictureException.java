package com.alexgunich.cargo.product.infrastructure.primary.exceptions;

/**
 * Exception thrown when there is an issue with handling multipart picture data.
 * <p>
 * This exception extends {@link RuntimeException} and is used to indicate
 * errors that occur during the processing of multipart picture uploads,
 * such as validation failures or format issues.
 * </p>
 *
 * @param message the detail message explaining the reason for the exception
 */
public class MultipartPictureException extends RuntimeException {
  public MultipartPictureException(String message) {
    super(message);
  }
}
