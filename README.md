# Franchise Management API

API REST para administrar franquicias, locations y productos con su stock.

El proyecto está desarrollado con Java 17, Spring Boot, MySQL, JPA y Flyway.

## Ejecutar con Docker

Se necesita Docker y tener disponibles los puertos `8080` y `3307`.

```bash
docker compose up --build
```

La API queda disponible en `http://localhost:8080`.

Para comprobar que está funcionando:

```text
http://localhost:8080/actuator/health
```

Para detener los contenedores:

```bash
docker compose down
```

Los datos de MySQL se conservan en un volumen de Docker.

## Ejecutar localmente

Se necesita Java 17. Primero se inicia MySQL con Docker:

```bash
docker compose up -d mysql
```

Después se ejecuta la aplicación con Maven Wrapper:

```bash
./mvnw spring-boot:run
```

También se puede ejecutar la clase `FranchiseManagementApiApplication` directamente desde IntelliJ.

La conexión local de MySQL usa el puerto `3307`. Flyway crea las tablas al iniciar la aplicación por primera vez.

## Endpoints

Se incluye una colección sencilla de Postman en `postman/franchise-management-api.postman_collection.json`.

| Método | Ruta | Descripción |
| --- | --- | --- |
| POST | `/api/franchises` | Crear una franquicia |
| POST | `/api/franchises/{franchiseId}/locations` | Agregar una location |
| POST | `/api/locations/{locationId}/products` | Agregar un producto |
| PATCH | `/api/products/{productId}/stock` | Actualizar el stock |
| DELETE | `/api/products/{productId}` | Eliminar un producto |
| GET | `/api/franchises/{franchiseId}/products/top-stock` | Consultar los productos con mayor stock por location |
| PATCH | `/api/franchises/{franchiseId}/name` | Cambiar el nombre de una franquicia |
| PATCH | `/api/locations/{locationId}/name` | Cambiar el nombre de una location |
| PATCH | `/api/products/{productId}/name` | Cambiar el nombre de un producto |

Las peticiones `POST` y `PATCH` reciben contenido JSON. Estos son los cuerpos principales:

```json
{ "name": "Nequi" }
```

```json
{ "name": "Cafe", "stock": 20 }
```

```json
{ "stock": 35 }
```

Ejemplo de respuesta del reporte de stock:

```json
[
  {
    "locationId": 1,
    "locationName": "Medellin",
    "productId": 3,
    "productName": "Cafe",
    "stock": 35
  }
]
```

Si varios productos tienen el mismo stock máximo en una location, el reporte devuelve todos los productos empatados. Las locations que no tienen productos no aparecen en el resultado.

## Pruebas

```bash
./mvnw test
```

La configuración se puede cambiar con las variables `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` y `PORT`.
