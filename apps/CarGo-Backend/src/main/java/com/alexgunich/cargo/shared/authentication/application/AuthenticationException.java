package com.alexgunich.cargo.shared.authentication.application;

/**
 * Abstract base class for authentication-related exceptions.
 * <p>
 * This class extends {@link RuntimeException} and serves as a foundation
 * for specific authentication exceptions in the application.
 * It can be used to handle various authentication errors consistently.
 * </p>
 */
abstract class AuthenticationException extends RuntimeException {
}
