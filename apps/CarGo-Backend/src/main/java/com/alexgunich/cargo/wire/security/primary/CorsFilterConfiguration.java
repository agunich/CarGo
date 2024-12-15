package com.alexgunich.cargo.wire.security.primary;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing) support.
 * <p>
 * This class defines a Spring configuration that registers a CORS filter, allowing
 * cross-origin requests to the application. It uses a {@link CorsConfiguration}
 * instance to specify the CORS settings and registers the filter with the highest
 * precedence to ensure it processes requests early in the filter chain.
 * </p>
 */
@Configuration
public class CorsFilterConfiguration {

  private CorsConfiguration corsConfiguration; // Configuration for CORS settings

  /**
   * Constructor that initializes the CORS configuration.
   *
   * @param corsConfiguration the CORS configuration to apply
   */
  public CorsFilterConfiguration(CorsConfiguration corsConfiguration) {
    this.corsConfiguration = corsConfiguration;
  }

  /**
   * Creates and registers a CORS filter bean.
   *
   * @return a {@link FilterRegistrationBean} containing the configured CORS filter
   */
  @Bean
  public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }
}
