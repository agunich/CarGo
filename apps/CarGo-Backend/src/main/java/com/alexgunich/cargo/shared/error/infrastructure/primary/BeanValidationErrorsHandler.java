package com.alexgunich.cargo.shared.error.infrastructure.primary;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Global exception handler for handling bean validation errors in a Spring application.
 * <p>
 * This class uses {@link ControllerAdvice} to intercept and handle exceptions related to
 * bean validation, specifically {@link MethodArgumentNotValidException} and
 * {@link ConstraintViolationException}. It formats the errors into a structured response
 * using {@link ProblemDetail} and logs the exceptions for further analysis.
 * </p>
 */
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1000)
class BeanValidationErrorsHandler {

  private static final String ERRORS = "errors"; // Key for error details
  private static final Logger log = LoggerFactory.getLogger(BeanValidationErrorsHandler.class); // Logger instance

  /**
   * Handles {@link MethodArgumentNotValidException} thrown when validation on an argument
   * annotated with {@code @Valid} fails.
   *
   * @param exception the exception containing validation errors
   * @return a {@link ProblemDetail} containing the error details
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
    ProblemDetail problem = buildProblemDetail();
    problem.setProperty(ERRORS, buildErrors(exception));

    log.info(exception.getMessage(), exception);

    return problem;
  }

  /**
   * Builds a map of field errors from the provided
   * {@link MethodArgumentNotValidException}.
   *
   * @param exception the exception containing field errors
   * @return a map of field names and their corresponding error messages
   */
  private Map<String, String> buildErrors(MethodArgumentNotValidException exception) {
    return exception
      .getBindingResult()
      .getFieldErrors()
      .stream()
      .collect(Collectors.toUnmodifiableMap(FieldError::getField, FieldError::getDefaultMessage));
  }

  /**
   * Handles {@link ConstraintViolationException} thrown when a bean validation constraint is violated.
   *
   * @param exception the exception containing validation violations
   * @return a {@link ProblemDetail} containing the error details
   */
  @ExceptionHandler(ConstraintViolationException.class)
  ProblemDetail handleConstraintViolationException(ConstraintViolationException exception) {
    ProblemDetail problem = buildProblemDetail();
    problem.setProperty(ERRORS, buildErrors(exception));

    log.info(exception.getMessage(), exception);

    return problem;
  }

  /**
   * Builds a {@link ProblemDetail} with a standard error message and HTTP status.
   *
   * @return a {@link ProblemDetail} with BAD_REQUEST status
   */
  private ProblemDetail buildProblemDetail() {
    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
      HttpStatus.BAD_REQUEST,
      "One or more fields were invalid. See 'errors' for details."
    );

    problem.setTitle("Bean validation error");
    return problem;
  }

  /**
   * Builds a map of constraint violations from the provided
   * {@link ConstraintViolationException}.
   *
   * @param exception the exception containing constraint violations
   * @return a map of field names and their corresponding violation messages
   */
  private Map<String, String> buildErrors(ConstraintViolationException exception) {
    return exception
      .getConstraintViolations()
      .stream()
      .collect(Collectors.toUnmodifiableMap(toFieldName(), ConstraintViolation::getMessage));
  }

  /**
   * Converts a {@link ConstraintViolation} to its corresponding field name.
   *
   * @return a function that extracts the field name from a constraint violation
   */
  private Function<ConstraintViolation<?>, String> toFieldName() {
    return error -> {
      String propertyPath = error.getPropertyPath().toString();
      return propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
    };
  }
}
