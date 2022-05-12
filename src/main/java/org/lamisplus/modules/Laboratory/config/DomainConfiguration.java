package org.lamisplus.modules.Laboratory.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"org.lamisplus.modules.Laboratory.repository"})
public class DomainConfiguration {
}
