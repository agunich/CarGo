package com.alexgunich.cargo.order.domain.user.aggregate;

import com.alexgunich.cargo.order.domain.user.vo.*;
import com.alexgunich.cargo.shared.error.domain.Assert;
import org.jilt.Builder;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Represents a user within the system, containing personal details, authentication information,
 * and roles.
 *
 * <p>The {@link User} class encapsulates the user’s profile information, including their name,
 * email, authorities (roles), and address, as well as timestamps for when the user was created
 * and last modified. It provides methods for updating user details, creating a user from token attributes,
 * and managing mandatory fields.</p>
 */
@Builder
public class User {

  private UserLastname lastname;

  private UserFirstname firstname;

  private UserEmail email;

  private UserPublicId userPublicId;

  private UserImageUrl imageUrl;

  private Instant lastModifiedDate;

  private Instant createdDate;

  private Set<Authority> authorities;

  private Long dbId;

  private UserAddress userAddress;

  private Instant lastSeen;

  /**
   * Constructs a new {@link User} with the specified parameters.
   *
   * @param lastname the user's last name.
   * @param firstname the user's first name.
   * @param email the user's email address.
   * @param userPublicId the user's public identifier.
   * @param imageUrl the URL to the user's image.
   * @param lastModifiedDate the timestamp when the user was last modified.
   * @param createdDate the timestamp when the user was created.
   * @param authorities a set of authorities (roles/permissions) assigned to the user.
   * @param dbId the user's database ID.
   * @param userAddress the user's address information.
   * @param lastSeen the timestamp when the user was last seen.
   */
  public User(UserLastname lastname, UserFirstname firstname, UserEmail email, UserPublicId userPublicId, UserImageUrl imageUrl, Instant lastModifiedDate, Instant createdDate, Set<Authority> authorities, Long dbId, UserAddress userAddress, Instant lastSeen) {
    this.lastname = lastname;
    this.firstname = firstname;
    this.email = email;
    this.userPublicId = userPublicId;
    this.imageUrl = imageUrl;
    this.lastModifiedDate = lastModifiedDate;
    this.createdDate = createdDate;
    this.authorities = authorities;
    this.dbId = dbId;
    this.userAddress = userAddress;
    this.lastSeen = lastSeen;
  }

  /**
   * Ensures that all mandatory fields are non-null.
   * This method is used to validate that the core user fields are provided when necessary.
   *
   * @throws IllegalArgumentException if any of the mandatory fields are null.
   */
  private void assertMandatoryFields() {
    Assert.notNull("lastname", lastname);
    Assert.notNull("firstname", firstname);
    Assert.notNull("email", email);
    Assert.notNull("authorities", authorities);
  }

  /**
   * Updates the current user's details with the data from another user object.
   *
   * @param user the user object whose details will be copied to this user.
   */
  public void updateFromUser(User user) {
    this.email = user.email;
    this.imageUrl = user.imageUrl;
    this.firstname = user.firstname;
    this.lastname = user.lastname;
  }

  /**
   * Initializes the user for signup by generating a unique public ID.
   * This method is called when setting up a new user account.
   */
  public void initFieldForSignup() {
    this.userPublicId = new UserPublicId(UUID.randomUUID());
  }

  /**
   * Creates a new {@link User} from the attributes of an authentication token and a list of roles.
   *
   * @param attributes a map of attributes from the authentication token.
   * @param rolesFromAccessToken a list of roles obtained from the access token.
   * @return a newly created user with the given token attributes and roles.
   */
  public static User fromTokenAttributes(Map<String, Object> attributes, List<String> rolesFromAccessToken) {
    UserBuilder userBuilder = UserBuilder.user();

    if(attributes.containsKey("preferred_email")) {
      userBuilder.email(new UserEmail(attributes.get("preferred_email").toString()));
    }

    if(attributes.containsKey("last_name")) {
      userBuilder.lastname(new UserLastname(attributes.get("last_name").toString()));
    }

    if(attributes.containsKey("first_name")) {
      userBuilder.firstname(new UserFirstname(attributes.get("first_name").toString()));
    }

    if(attributes.containsKey("picture")) {
      userBuilder.imageUrl(new UserImageUrl(attributes.get("picture").toString()));
    }

    if(attributes.containsKey("last_signed_in")) {
      userBuilder.lastSeen(Instant.parse(attributes.get("last_signed_in").toString()));
    }

    Set<Authority> authorities = rolesFromAccessToken
      .stream()
      .map(authority -> AuthorityBuilder.authority().name(new AuthorityName(authority)).build())
      .collect(Collectors.toSet());

    userBuilder.authorities(authorities);

    return userBuilder.build();
  }

  // Getters for user attributes

  /**
   * Returns the user's last name.
   *
   * @return the user's last name.
   */
  public UserLastname getLastname() {
    return lastname;
  }

  /**
   * Returns the user's first name.
   *
   * @return the user's first name.
   */
  public UserFirstname getFirstname() {
    return firstname;
  }

  /**
   * Returns the user's email address.
   *
   * @return the user's email.
   */
  public UserEmail getEmail() {
    return email;
  }

  /**
   * Returns the user's public ID.
   *
   * @return the user's public ID.
   */
  public UserPublicId getUserPublicId() {
    return userPublicId;
  }

  /**
   * Returns the URL of the user's image.
   *
   * @return the user's image URL.
   */
  public UserImageUrl getImageUrl() {
    return imageUrl;
  }

  /**
   * Returns the timestamp of when the user was last modified.
   *
   * @return the timestamp of the last modification.
   */
  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  /**
   * Returns the timestamp of when the user was created.
   *
   * @return the timestamp of the user creation.
   */
  public Instant getCreatedDate() {
    return createdDate;
  }

  /**
   * Returns the set of authorities (roles/permissions) assigned to the user.
   *
   * @return the set of user authorities.
   */
  public Set<Authority> getAuthorities() {
    return authorities;
  }

  /**
   * Returns the user's database ID.
   *
   * @return the user's database ID.
   */
  public Long getDbId() {
    return dbId;
  }

  /**
   * Returns the user's address.
   *
   * @return the user's address.
   */
  public UserAddress getUserAddress() {
    return userAddress;
  }

  /**
   * Returns the timestamp of when the user was last seen.
   *
   * @return the timestamp of the last time the user was seen.
   */
  public Instant getLastSeen() {
    return lastSeen;
  }
}
