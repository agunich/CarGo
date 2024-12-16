package com.alexgunich.cargo.shared.authentication.application;

/**
 * Exception thrown when a user is not authenticated.
 * <p>
 * This class extends {@link AuthenticationException} and is used to indicate
 * that an operation requiring authentication was attempted by a user who
 * has not been authenticated.
 * </p>
 */
public class NotAuthenticatedUserException extends AuthenticationException {
}
