package com.alexgunich.cargo.order.infrastructure.secondary.entity;

import com.alexgunich.cargo.order.domain.user.aggregate.User;
import com.alexgunich.cargo.order.domain.user.aggregate.UserBuilder;
import com.alexgunich.cargo.order.domain.user.vo.*;
import com.alexgunich.cargo.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Entity representing a User in the database.
 * This entity is mapped to the "ecommerce_user" table and contains details such as
 * the user's name, email, address, image URL, last seen time, and associated authorities.
 * It also extends AbstractAuditingEntity for automatic auditing (createdAt, updatedAt).
 */
@Entity
@Table(name = "ecommerce_user")
@Builder
public class UserEntity extends AbstractAuditingEntity<Long> {

  /**
   * The primary key identifier for the User entity.
   * This ID is auto-generated using a sequence generator.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequenceGenerator")
  @SequenceGenerator(name = "userSequenceGenerator", sequenceName = "user_sequence", allocationSize = 1)
  @Column(name = "id")
  private Long id;

  /**
   * The last name of the user.
   */
  @Column(name = "last_name")
  private String lastName;

  /**
   * The first name of the user.
   */
  @Column(name = "first_name")
  private String firstName;

  /**
   * The email address of the user.
   */
  @Column(name = "email")
  private String email;

  /**
   * The URL of the user's image (e.g., profile picture).
   */
  @Column(name = "image_url")
  private String imageURL;

  /**
   * The public unique identifier for the user.
   * This is a UUID value that is unique for each user.
   */
  @Column(name = "public_id")
  private UUID publicId;

  /**
   * The street address of the user.
   */
  @Column(name = "address_street")
  private String addressStreet;

  /**
   * The city of the user's address.
   */
  @Column(name = "address_city")
  private String addressCity;

  /**
   * The zip code of the user's address.
   */
  @Column(name = "address_zip_code")
  private String addressZipCode;

  /**
   * The country of the user's address.
   */
  @Column(name = "address_country")
  private String addressCountry;

  /**
   * The last time the user was seen (e.g., last login time).
   */
  @Column(name = "last_seen")
  private Instant lastSeen;

  /**
   * The set of authorities assigned to the user.
   * This is a many-to-many relationship with the AuthorityEntity.
   */
  @ManyToMany(cascade = CascadeType.REMOVE)
  @JoinTable(
    name = "user_authority",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
  )
  private Set<AuthorityEntity> authorities = new HashSet<>();

  /**
   * Default constructor required by JPA.
   */
  public UserEntity() {
  }

  /**
   * Constructor to initialize the UserEntity with specific values.
   *
   * @param id              the unique ID of the user
   * @param lastName        the user's last name
   * @param firstName       the user's first name
   * @param email           the user's email
   * @param imageURL        the URL of the user's profile image
   * @param publicId        the public unique ID of the user
   * @param addressStreet   the street of the user's address
   * @param addressCity     the city of the user's address
   * @param addressZipCode  the zip code of the user's address
   * @param addressCountry  the country of the user's address
   * @param lastSeen        the last time the user was seen
   * @param authorities     the set of authorities assigned to the user
   */
  public UserEntity(Long id, String lastName, String firstName, String email, String imageURL, UUID publicId, String addressStreet, String addressCity, String addressZipCode, String addressCountry, Instant lastSeen, Set<AuthorityEntity> authorities) {
    this.id = id;
    this.lastName = lastName;
    this.firstName = firstName;
    this.email = email;
    this.imageURL = imageURL;
    this.publicId = publicId;
    this.addressStreet = addressStreet;
    this.addressCity = addressCity;
    this.addressZipCode = addressZipCode;
    this.addressCountry = addressCountry;
    this.lastSeen = lastSeen;
    this.authorities = authorities;
  }

  /**
   * Updates the UserEntity with values from the domain User object.
   * This method is useful for updating an existing UserEntity in the database.
   *
   * @param user the domain User object to update from
   */
  public void updateFromUser(User user) {
    this.email = user.getEmail().value();
    this.lastName = user.getLastname().value();
    this.firstName = user.getFirstname().value();
    this.imageURL = user.getImageUrl().value();
    this.lastSeen = user.getLastSeen();
  }

  /**
   * Converts a domain User object into its corresponding UserEntity representation.
   *
   * @param user the domain User object
   * @return the corresponding UserEntity
   */
  public static UserEntity from(User user) {
    UserEntityBuilder userEntityBuilder = UserEntityBuilder.userEntity();

    if (user.getImageUrl() != null) {
      userEntityBuilder.imageURL(user.getImageUrl().value());
    }

    if (user.getUserPublicId() != null) {
      userEntityBuilder.publicId(user.getUserPublicId().value());
    }

    if (user.getUserAddress() != null) {
      userEntityBuilder.addressCity(user.getUserAddress().city());
      userEntityBuilder.addressCountry(user.getUserAddress().country());
      userEntityBuilder.addressZipCode(user.getUserAddress().zipCode());
      userEntityBuilder.addressStreet(user.getUserAddress().street());
    }

    return userEntityBuilder
      .authorities(AuthorityEntity.from(user.getAuthorities()))
      .email(user.getEmail().value())
      .firstName(user.getFirstname().value())
      .lastName(user.getLastname().value())
      .lastSeen(user.getLastSeen())
      .id(user.getDbId())
      .build();
  }

  /**
   * Converts a UserEntity into its corresponding domain User object.
   *
   * @param userEntity the UserEntity to convert
   * @return the corresponding domain User
   */
  public static User toDomain(UserEntity userEntity) {
    UserBuilder userBuilder = UserBuilder.user();

    if(userEntity.getImageURL() != null) {
      userBuilder.imageUrl(new UserImageUrl(userEntity.getImageURL()));
    }

    if(userEntity.getAddressStreet() != null) {
      userBuilder.userAddress(
        UserAddressBuilder.userAddress()
          .city(userEntity.getAddressCity())
          .country(userEntity.getAddressCountry())
          .zipCode(userEntity.getAddressZipCode())
          .street(userEntity.getAddressStreet())
          .build());
    }

    return userBuilder
      .email(new UserEmail(userEntity.getEmail()))
      .lastname(new UserLastname(userEntity.getLastName()))
      .firstname(new UserFirstname(userEntity.getFirstName()))
      .authorities(AuthorityEntity.toDomain(userEntity.getAuthorities()))
      .userPublicId(new UserPublicId(userEntity.getPublicId()))
      .lastModifiedDate(userEntity.getLastModifiedDate())
      .createdDate(userEntity.getCreatedDate())
      .dbId(userEntity.getId())
      .build();
  }

  /**
   * Converts a list of domain User objects into a set of UserEntity objects.
   *
   * @param users the list of domain User objects
   * @return the corresponding set of UserEntities
   */
  public static Set<UserEntity> from(List<User> users) {
    return users.stream().map(UserEntity::from).collect(Collectors.toSet());
  }

  /**
   * Converts a list of UserEntity objects into a set of domain User objects.
   *
   * @param users the list of UserEntities
   * @return the corresponding set of domain Users
   */
  public static Set<User> toDomain(List<UserEntity> users) {
    return users.stream().map(UserEntity::toDomain).collect(Collectors.toSet());
  }

  // Getters and Setters

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }

  public String getAddressStreet() {
    return addressStreet;
  }

  public void setAddressStreet(String addressStreet) {
    this.addressStreet = addressStreet;
  }

  public String getAddressCity() {
    return addressCity;
  }

  public void setAddressCity(String addressCity) {
    this.addressCity = addressCity;
  }

  public String getAddressZipCode() {
    return addressZipCode;
  }

  public void setAddressZipCode(String addressZipCode) {
    this.addressZipCode = addressZipCode;
  }

  public String getAddressCountry() {
    return addressCountry;
  }

  public void setAddressCountry(String addressCountry) {
    this.addressCountry = addressCountry;
  }

  public Instant getLastSeen() {
    return lastSeen;
  }

  public void setLastSeen(Instant lastSeen) {
    this.lastSeen = lastSeen;
  }

  public Set<AuthorityEntity> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<AuthorityEntity> authorities) {
    this.authorities = authorities;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserEntity that)) return false;
    return Objects.equals(publicId, that.publicId);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(publicId);
  }
}
