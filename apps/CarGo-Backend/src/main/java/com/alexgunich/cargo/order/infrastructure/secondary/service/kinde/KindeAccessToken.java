package com.alexgunich.cargo.order.infrastructure.secondary.service.kinde;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A record that represents an access token used for authentication with the Kinde API.
 * <p>
 * This class holds the access token and the type of the token (e.g., "Bearer").
 * It is typically used in API requests to authenticate and authorize the user with the Kinde service.
 * </p>
 *
 * @param accessToken the access token value used for authentication
 * @param tokenType   the type of the token (e.g., "Bearer")
 */
public record KindeAccessToken(@JsonProperty("access_token") String accessToken,
                               @JsonProperty("token_type") String tokenType) {
}
