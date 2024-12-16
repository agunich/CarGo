package com.alexgunich.cargo.product.domain.vo;

import com.alexgunich.cargo.shared.error.domain.NotAColorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the color of a product with validation for valid color formats.
 */
public record ProductColor(String value) {

  /**
   * Constructs a ProductColor instance.
   * Validates that the provided value is a valid color in hexadecimal format.
   * Accepts 3, 6, or 8 hexadecimal characters prefixed by a '#'.
   *
   * @param value the color code of the product
   * @throws NotAColorException if the value is not a valid color format
   */
  public ProductColor {
    Pattern colorPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
    Matcher matcher = colorPattern.matcher(value);
    boolean isColor = matcher.matches();
    if (!isColor) {
      throw new NotAColorException(value, "Invalid color");
    }
  }
}
