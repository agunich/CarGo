package com.alexgunich.cargo.order.infrastructure.secondary.service.kinde;

import org.apache.hc.core5.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Service to interact with the Kinde API for user authentication and information retrieval.
 * <p>
 * This service allows retrieving an access token using client credentials and making requests
 * to Kinde API endpoints, such as fetching user information.
 * </p>
 */
@Service
public class KindeService {

  private static final Logger log = LoggerFactory.getLogger(KindeService.class);

  @Value("${application.kinde.api}")
  private String apiUrl;

  @Value("${application.kinde.client-id}")
  private String clientId;

  @Value("${application.kinde.client-secret}")
  private String clientSecret;

  @Value("${application.kinde.audience}")
  private String audience;

  private final RestClient restClient = RestClient.builder()
    .requestFactory(new HttpComponentsClientHttpRequestFactory())
    .baseUrl(apiUrl)
    .build();

  /**
   * Retrieves an access token from the Kinde API using client credentials.
   * <p>
   * This method constructs the request to get an access token using the client ID and secret,
   * and returns it as an {@link Optional}. If an error occurs during the token retrieval process,
   * an empty {@link Optional} is returned.
   * </p>
   *
   * @return an {@link Optional} containing the access token if successful, or empty if an error occurred
   */
  private Optional<String> getToken() {
    try {
      ResponseEntity<KindeAccessToken> accessToken = restClient.post()
        .uri(apiUrl + "/oauth/token")
        .body("grant_type=client_credentials&audience=" + URLEncoder.encode(audience, StandardCharsets.UTF_8))
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .header("Authorization",
          "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes(StandardCharsets.UTF_8)))
        .header("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.getMimeType())
        .retrieve()
        .toEntity(KindeAccessToken.class);
      return Optional.of(Objects.requireNonNull(accessToken.getBody()).accessToken());
    } catch (Exception e) {
      log.error("Error while getting token", e);
      return Optional.empty();
    }
  }

  /**
   * Retrieves user information from the Kinde API based on the user ID.
   * <p>
   * This method uses the access token obtained from the {@link #getToken()} method to make an authorized
   * API request to fetch user details.
   * </p>
   *
   * @param userId the ID of the user whose information is to be fetched
   * @return a {@link Map} containing the user information returned by the Kinde API
   * @throws IllegalStateException if the access token cannot be retrieved
   */
  public Map<String, Object> getUserInfo(String userId) {
    String token = getToken().orElseThrow(() -> new IllegalStateException("No token found"));

    var typeRef = new ParameterizedTypeReference<Map<String, Object>>() {};

    ResponseEntity<Map<String, Object>> authorization = restClient.get()
      .uri(apiUrl + "/api/v1/user?id={id}", userId)
      .header("Authorization", "Bearer " + token)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .toEntity(typeRef);

    return authorization.getBody();
  }

}
