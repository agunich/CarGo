package com.alexgunich.cargo.product.domain.repository;

import com.alexgunich.cargo.product.domain.aggregate.Category;
import com.alexgunich.cargo.product.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repository interface for accessing and managing {@link Category} entities in the data store.
 * <p>
 * The {@link CategoryRepository} interface defines methods to handle basic CRUD operations
 * (create, read, update, delete) for {@link Category} objects. This interface extends the functionality
 * provided by Spring Data, allowing for easy integration with the persistence layer.
 * </p>
 */
public interface CategoryRepository {

  /**
   * Retrieves all {@link Category} entities, paginated by the given {@link Pageable}.
   *
   * @param pageable pagination details such as page number and page size
   * @return a {@link Page} containing the list of categories
   */
  Page<Category> findAll(Pageable pageable);

  /**
   * Deletes a {@link Category} by its public identifier.
   *
   * @param publicId the public identifier of the category to delete
   * @return the number of categories deleted (0 or 1)
   */
  int delete(PublicId publicId);

  /**
   * Saves a new {@link Category} to the data store.
   *
   * @param categoryToCreate the category to be saved
   * @return the saved {@link Category} object, including its generated identifier
   */
  Category save(Category categoryToCreate);
}
