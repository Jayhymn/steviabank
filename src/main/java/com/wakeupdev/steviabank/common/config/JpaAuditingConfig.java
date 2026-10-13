package com.wakeupdev.steviabank.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Turns on Spring Data JPA auditing, which is what actually populates the
 * @CreatedDate/@LastModifiedDate fields declared in BaseEntity.
 */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig {
}