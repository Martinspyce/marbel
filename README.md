# Marbel-Microservices
Este proyecto fue desarrollado como parte de una evaluación académica. EcoMarket es una tienda que permite administrar usuarios, productos, órdenes y generar reportes, todo utilizando una arquitectura basada en microservicios con Spring Boot.

Microservicios Implementados,
Usuarios: Registro, búsqueda, edición y eliminación de usuarios.,
Productos: Administración de productos con nombre, descripción, precio y fecha.,
Órdenes: Gestión de pedidos vinculados a usuarios y productos.,
Reportes: Reportes filtrables por nombre y fechas.,

Cada microservicio tiene su propia capa de modelo, repositorio, servicio y controlador. Se implementaron operaciones CRUD y filtros personalizados usando JPA.

Seguridad,
Se aplicó autenticación básica con Spring Security. El usuario por defecto (admin) es creado al iniciar la aplicación y puede autenticarse desde Postman o Swagger UI.

Documentación con Swagger,
Swagger UI está integrado y permite visualizar y probar todos los endpoints REST desde el navegador.

Pruebas Unitarias,
Los servicios principales fueron testeados con JUnit 5 y Mockito, validando las operaciones más importantes como búsqueda, creación y eliminación de entidades.

Tecnologías Usadas,
Java 21,
Spring Boot,
Spring Data JPA + Hibernate,
Spring Security,
Maven,
H2 como base de datos en memoria (para pruebas),
Swagger/OpenAPI,
JUnit 5 + Mockito,

Cómo ejecutar,
Clona el repositorio.,
Ejecuta el proyecto con tu IDE favorito.,
Accede a Swagger UI en: http://localhost:8080/swagger-ui/index.html,
Autentícate con el usuario admin y la contraseña por defecto messi en la pestaña web de Swagger.

