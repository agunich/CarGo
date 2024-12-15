package com.alexgunich.cargo.product.infrastructure.primary.exceptions;

/**
 * Exception thrown when the creation of an entity fails.
 * <p>
 * This exception extends {@link RuntimeException} and is used to indicate
 * that an operation to create an entity was unsuccessful, typically due
 * to validation errors or other issues encountered during the creation process.
 * </p>
 *
 * @param message the detail message explaining the reason for the failure
 */
public class EntityCreationFailed extends RuntimeException {
  public EntityCreationFailed(String message) {
    super(message);
  }
}
