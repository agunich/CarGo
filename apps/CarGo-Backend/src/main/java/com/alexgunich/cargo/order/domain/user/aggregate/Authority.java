package com.alexgunich.cargo.order.domain.user.aggregate;

import com.alexgunich.cargo.order.domain.user.vo.AuthorityName;
import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

/**
 * Represents an authority granted to a user.
 *
 * <p>The {@link Authority} class encapsulates the name of an authority (such as a role or permission)
 * assigned to a user, ensuring that the authority name is not null upon creation.</p>
 */
@Builder
public class Authority {

  private AuthorityName name;

  /**
   * Constructs a new {@link Authority} with the specified authority name.
   *
   * @param authorityName the name of the authority to be assigned to the user.
   * @throws IllegalArgumentException if the authority name is null.
   */
  public Authority(AuthorityName authorityName) {
    Assert.notNull("name", authorityName);
    this.name = authorityName;
  }

  /**
   * Returns the name of the authority.
   *
   * @return the name of the authority.
   */
  public AuthorityName getName() {
    return name;
  }
}
