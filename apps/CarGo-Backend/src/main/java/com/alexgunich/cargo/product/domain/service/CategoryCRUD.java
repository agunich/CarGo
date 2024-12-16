package com.alexgunich.cargo.product.domain.service;

import com.alexgunich.cargo.product.domain.aggregate.Category;
import com.alexgunich.cargo.product.domain.repository.CategoryRepository;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service class for performing CRUD operations on {@link Category} entities.
 * <p>
 * The {@link CategoryCRUD} class provides methods for saving, retrieving, and deleting {@link Category} entities.
 * It interacts with the {@link CategoryRepository} for data access and manages the lifecycle of categories.
 * </p>
 */
public class CategoryCRUD {

  private final CategoryRepository categoryRepository;

  /**
   * Constructs a new {@link CategoryCRUD} service with the specified {@link CategoryRepository}.
   *
   * @param categoryRepository the repository used for accessing and manipulating categories
   */
  public CategoryCRUD(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  /**
   * Saves a new or existing {@link Category} entity.
   * <p>
   * This method initializes the default fields for the category before saving it to the repository.
   * </p>
   *
   * @param category the {@link Category} entity to save
   * @return the saved {@link Category} entity, including any changes made during the save operation
   */
  public Category save(Category category) {
    category.initDefaultFields();
    return categoryRepository.save(category);
  }

  /**
   * Retrieves all {@link Category} entities, paginated according to the given {@link Pageable}.
   *
   * @param pageable pagination details such as page number and page size
   * @return a {@link Page} containing the list of {@link Category} entities
   */
  public Page<Category> findAll(Pageable pageable) {
    return categoryRepository.findAll(pageable);
  }

  /**
   * Deletes a {@link Category} by its public identifier.
   * <p>
   * If no category is found with the provided identifier, an {@link EntityNotFoundException} will be thrown.
   * </p>
   *
   * @param categoryId the public identifier of the category to delete
   * @return the public identifier of the deleted category
   * @throws EntityNotFoundException if no category is found with the provided identifier
   */
  public PublicId delete(PublicId categoryId) {
    int nbOfRowsDeleted = categoryRepository.delete(categoryId);
    if(nbOfRowsDeleted != 1) {
      throw new EntityNotFoundException(String.format("No category deleted with id %s", categoryId));
    }
    return categoryId;
  }
}
