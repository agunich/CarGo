package com.alexgunich.cargo.shared.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/**
 * Abstract base class for entities that require auditing information.
 * <p>
 * This class serves as a base for JPA entities that need to maintain
 * auditing fields: the creation date and last modified date. It uses
 * Spring Data JPA's auditing feature to automatically populate these fields.
 * </p>
 *
 * @param <T> the type of the entity identifier
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity<T> implements Serializable {

  /**
   * Gets the unique identifier of the entity.
   *
   * @return the unique identifier
   */
  public abstract T getId();

  @CreatedDate
  @Column(updatable = false, name = "created_date")
  private Instant createdDate; // The date the entity was created

  @LastModifiedDate
  @Column(name = "last_modified_date")
  private Instant lastModifiedDate; // The date the entity was last modified

  /**
   * Gets the creation date of the entity.
   *
   * @return the creation date
   */
  public Instant getCreatedDate() {
    return createdDate;
  }

  /**
   * Sets the creation date of the entity.
   *
   * @param createdDate the creation date to set
   */
  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * Gets the last modified date of the entity.
   *
   * @return the last modified date
   */
  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  /**
   * Sets the last modified date of the entity.
   *
   * @param lastModifiedDate the last modified date to set
   */
  public void setLastModifiedDate(Instant lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }
}
