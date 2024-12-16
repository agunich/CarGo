package com.alexgunich.cargo.wire.security.primary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Configuration class for loading CORS (Cross-Origin Resource Sharing) properties.
 * <p>
 * This class defines a Spring configuration that reads CORS-related properties
 * from the application's configuration files (e.g., application.yml or application.properties).
 * It creates a {@link CorsConfiguration} bean that can be used to define CORS settings
 * for the application.
 * </p>
 */
@Configuration
public class CorsProperties {

  /**
   * Creates a {@link CorsConfiguration} bean populated with properties
   * prefixed by "application.cors".
   *
   * @return a configured instance of {@link CorsConfiguration}
   */
  @Bean
  @ConfigurationProperties(prefix = "application.cors", ignoreUnknownFields = false)
  public CorsConfiguration corsConfiguration() {
    return new CorsConfiguration();
  }
}
