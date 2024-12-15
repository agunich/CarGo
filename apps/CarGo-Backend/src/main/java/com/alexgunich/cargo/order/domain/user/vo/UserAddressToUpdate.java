package com.alexgunich.cargo.order.domain.user.vo;

import com.alexgunich.cargo.shared.error.domain.Assert;
import com.alexgunich.cargo.shared.error.domain.MissingMandatoryValueException;
import org.jilt.Builder;

/**
 * Represents a request to update a user's address.
 *
 * <p>This class is used to encapsulate the public ID of a user and the updated address information.
 * It validates that neither the user public ID nor the address are null upon creation.</p>
 */
@Builder
public record UserAddressToUpdate(UserPublicId userPublicId, UserAddress userAddress) {

  /**
   * Validates that both the user public ID and user address are not null.
   *
   * @param userPublicId the unique public ID of the user whose address is being updated
   * @param userAddress the new address to be associated with the user
   * @throws IllegalArgumentException if either the user public ID or user address is null
   */
  public UserAddressToUpdate {
    Assert.notNull("userPublicId", userPublicId); // Ensures the user public ID is not null.
    Assert.notNull("userAddress", userAddress);   // Ensures the user address is not null.
  }
}
