// src/main/java/com/alberto/laesquina/TataBackendApplication.java
package com.alberto.laesquina;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.alberto.laesquina.entidad")
@EnableJpaRepositories(basePackages = "com.alberto.laesquina.repositorio")
@ComponentScan(basePackages = {
        "com.alberto.laesquina",
        "com.alberto.laesquina.config",
        "com.alberto.laesquina.servicio",
        "com.alberto.laesquina.controlador",
        "com.alberto.laesquina.repositorio"
})
public class LaEsquinaApplication {
    public static void main(String[] args) {
        SpringApplication.run(LaEsquinaApplication.class, args);
    }
}