package com.alexgunich.cargo.order.infrastructure.secondary.entity;

import com.alexgunich.cargo.order.domain.user.aggregate.Authority;
import com.alexgunich.cargo.order.domain.user.aggregate.AuthorityBuilder;
import com.alexgunich.cargo.order.domain.user.vo.AuthorityName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jilt.Builder;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entity class representing an Authority in the system.
 * This class is mapped to the "authority" table in the database.
 */
@Entity
@Table(name = "authority")
@Builder
public class AuthorityEntity implements Serializable {

  /**
   * The name of the authority. It serves as the primary key in the database.
   */
  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  private String name;

  /**
   * Default constructor for JPA.
   */
  public AuthorityEntity() {
  }

  /**
   * Constructor to create an AuthorityEntity with a specific name.
   *
   * @param name the name of the authority
   */
  public AuthorityEntity(String name) {
    this.name = name;
  }

  /**
   * Converts a set of Authority domain objects to a set of AuthorityEntity objects.
   *
   * @param authorities the set of Authority domain objects
   * @return a set of corresponding AuthorityEntity objects
   */
  public static Set<AuthorityEntity> from(Set<Authority> authorities) {
    return authorities.stream()
      .map(authority -> AuthorityEntityBuilder.authorityEntity()
        .name(authority.getName().name()).build()).collect(Collectors.toSet());
  }

  /**
   * Converts a set of AuthorityEntity objects to a set of Authority domain objects.
   *
   * @param authorityEntities the set of AuthorityEntity objects
   * @return a set of corresponding Authority domain objects
   */
  public static Set<Authority> toDomain(Set<AuthorityEntity> authorityEntities) {
    return authorityEntities.stream()
      .map(authorityEntity -> AuthorityBuilder.authority().name(new AuthorityName(authorityEntity.name)).build())
      .collect(Collectors.toSet());
  }

  /**
   * Gets the name of the authority.
   *
   * @return the name of the authority
   */
  public @NotNull @Size(max = 50) String getName() {
    return name;
  }

  /**
   * Sets the name of the authority.
   *
   * @param name the new name of the authority
   */
  public void setName(@NotNull @Size(max = 50) String name) {
    this.name = name;
  }

  /**
   * Checks whether two AuthorityEntity objects are equal.
   * Two entities are equal if their name is the same.
   *
   * @param o the object to compare
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AuthorityEntity that)) return false;
    return Objects.equals(name, that.name);
  }

  /**
   * Returns a hash code for the AuthorityEntity object.
   *
   * @return the hash code of the entity
   */
  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
