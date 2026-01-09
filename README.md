# TATA E-Commerce Backend

Backend REST API para el proyecto e-commerce TATA, implementado con Spring Boot.

## Tecnologías

- Java 21
- Spring Boot 3.2.0
- Maven
- PostgreSQL
- JPA/Hibernate
- Lombok

## Arquitectura

El proyecto sigue una arquitectura en capas:

- **entity**: Entidades JPA (sin herencia)
- **repository**: Repositorios Spring Data JPA
- **service**: Lógica de negocio
- **controller**: Controladores REST (sin lógica, solo llamadas a servicios)

## Modelo de Datos

### Usuarios
- **Usuario**: Tabla principal con rol (CLIENTE, VENDEDOR, ADMIN)
- **Cliente**: Información específica de clientes (FK a Usuario)
- **Vendedor**: Información específica de vendedores (FK a Usuario)
- **Administrador**: Información específica de administradores (FK a Usuario)

### Productos y Artículos
- **Producto**: Concepto único del producto con estados (PENDIENTE, APROBADO, RECHAZADO)
- **Articulo**: Publicación específica de un vendedor con estados (DISPONIBLE, RESERVADO, VENDIDO)

### Auditoría
- **Auditoria**: Registro de todas las acciones administrativas

## Flujo de Negocio

1. Un vendedor selecciona un Producto (si no existe, se crea en estado PENDIENTE)
2. El ADMIN revisa y aprueba/rechaza el Producto
3. Solo productos APROBADOS pueden usarse para publicar Artículos
4. Los Artículos tienen su propio ciclo de vida independiente

## Configuración

Edita `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tata_ecommerce
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
