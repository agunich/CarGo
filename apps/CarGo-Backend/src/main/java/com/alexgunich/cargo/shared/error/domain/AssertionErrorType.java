package com.alexgunich.cargo.shared.error.domain;

/**
 * Enum representing various types of assertion errors.
 * <p>
 * This enum defines specific error types that may occur during validation
 * and assertion checks within the application. These error types can be
 * used to categorize and handle different kinds of assertion failures.
 * </p>
 */
public enum AssertionErrorType {
  MISSING_MANDATORY_VALUE,      // Error indicating a required value is missing
  NOT_AFTER_TIME,               // Error indicating a date/time is not after a specified time
  NOT_BEFORE_TIME,              // Error indicating a date/time is not before a specified time
  NULL_ELEMENT_IN_COLLECTION,    // Error indicating a null element exists in a collection
  NUMBER_VALUE_TOO_HIGH,        // Error indicating a number exceeds the allowed maximum
  NUMBER_VALUE_TOO_LOW,         // Error indicating a number is below the allowed minimum
  STRING_TOO_LONG,              // Error indicating a string exceeds the maximum length
  STRING_TOO_SHORT,             // Error indicating a string is below the minimum length
  TOO_MANY_ELEMENTS,            // Error indicating a collection has more elements than allowed
  NOT_A_COLOR                   // Error indicating a value is not a valid color representation
}
