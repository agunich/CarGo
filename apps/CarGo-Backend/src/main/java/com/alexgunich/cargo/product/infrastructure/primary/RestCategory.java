package com.alexgunich.cargo.product.infrastructure.primary;

import com.alexgunich.cargo.product.domain.aggregate.Category;
import com.alexgunich.cargo.product.domain.aggregate.CategoryBuilder;
import com.alexgunich.cargo.product.domain.vo.CategoryName;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

import java.util.UUID;

/**
 * Represents a RESTful category object.
 * <p>
 * This record encapsulates the data required to represent a category in the API,
 * including its public ID and name. It provides methods to convert to and from
 * the domain model.
 * </p>
 *
 * @param publicId the unique identifier of the category; must not be null
 * @param name     the name of the category; must not be null
 * @throws IllegalArgumentException if the name is null
 */
@Builder
public record RestCategory(UUID publicId,
                           String name) {

  public RestCategory {
    Assert.notNull("name", name);
  }

  /**
   * Converts this REST category to a domain category.
   *
   * @param restCategory the REST category to convert
   * @return the corresponding domain category
   */
  public static Category toDomain(RestCategory restCategory) {
    CategoryBuilder categoryBuilder = CategoryBuilder.category();

    if (restCategory.name != null) {
      categoryBuilder.name(new CategoryName(restCategory.name));
    }

    if (restCategory.publicId != null) {
      categoryBuilder.publicId(new PublicId(restCategory.publicId));
    }

    return categoryBuilder.build();
  }

  /**
   * Converts a domain category to a REST category.
   *
   * @param category the domain category to convert
   * @return the corresponding REST category
   */
  public static RestCategory fromDomain(Category category) {
    RestCategoryBuilder restCategoryBuilder = RestCategoryBuilder.restCategory();

    if (category.getName() != null) {
      restCategoryBuilder.name(category.getName().value());
    }

    return restCategoryBuilder
      .publicId(category.getPublicId().value())
      .build();
  }
}
