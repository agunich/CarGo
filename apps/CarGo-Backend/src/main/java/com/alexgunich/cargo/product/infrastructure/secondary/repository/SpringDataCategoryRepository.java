package com.alexgunich.cargo.product.infrastructure.secondary.repository;

import com.alexgunich.cargo.product.domain.aggregate.Category;
import com.alexgunich.cargo.product.domain.repository.CategoryRepository;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import com.alexgunich.cargo.product.infrastructure.secondary.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the {@link CategoryRepository} interface using Spring Data JPA.
 * <p>
 * This class serves as a repository for managing {@link Category} entities,
 * delegating the actual data access to the {@link JpaCategoryRepository}.
 * </p>
 */
@Repository
public class SpringDataCategoryRepository implements CategoryRepository {

  private final JpaCategoryRepository jpaCategoryRepository;

  /**
   * Constructs a new SpringDataCategoryRepository with the specified JPA repository.
   *
   * @param jpaCategoryRepository the JPA category repository to delegate calls to
   */
  public SpringDataCategoryRepository(JpaCategoryRepository jpaCategoryRepository) {
    this.jpaCategoryRepository = jpaCategoryRepository;
  }

  /**
   * Retrieves a paginated list of all categories.
   *
   * @param pageable pagination information
   * @return a page of {@link Category} entities
   */
  @Override
  public Page<Category> findAll(Pageable pageable) {
    return jpaCategoryRepository.findAll(pageable).map(CategoryEntity::to);
  }

  /**
   * Deletes a category by its public ID.
   *
   * @param publicId the unique public ID of the category to delete
   * @return the number of entities deleted (should be 0 or 1)
   */
  @Override
  public int delete(PublicId publicId) {
    return jpaCategoryRepository.deleteByPublicId(publicId.value());
  }

  /**
   * Saves a new category or updates an existing one.
   *
   * @param categoryToCreate the category to create or update
   * @return the saved {@link Category} entity
   */
  @Override
  public Category save(Category categoryToCreate) {
    CategoryEntity categoryToSave = CategoryEntity.from(categoryToCreate);
    CategoryEntity categorySaved = jpaCategoryRepository.save(categoryToSave);
    return CategoryEntity.to(categorySaved);
  }
}
