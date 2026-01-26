// src/main/java/com/alberto/laesquina/config/RepositorioConfig.java
package com.alberto.laesquina.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.alberto.laesquina.repositorio",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class RepositorioConfig {
    // Esta clase fuerza a Spring a escanear los repositorios
}