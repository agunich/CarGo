package com.alexgunich.cargo.wire.postgresql.infrastructure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for setting up database-related features in the application.
 * <p>
 * This class is responsible for enabling JPA auditing, transaction management,
 * and Spring Data JPA repositories. It also configures support for Spring Data
 * web features, including DTO-based pagination and serialization.
 * </p>
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.alexgunich.cargo"})
@EnableJpaAuditing
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class DatabaseConfiguration {
}
