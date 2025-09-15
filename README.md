# 🛍️ Product Management API

Una API REST moderna para gestión de productos implementada con **Spring Boot 3.3.5** y **Clean Architecture**, diseñada para ser escalable, mantenible y fácil de probar.

Github link: https://github.com/daniel1795/project

## 🏗️ Arquitectura del Sistema

### Clean Architecture Implementation

El proyecto implementa **Clean Architecture** siguiendo los principios de **Domain-Driven Design (DDD)**:

```
┌─────────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                      │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   Controllers   │  │   DTOs          │  │   Config    │ │
│  │   (REST API)    │  │   (Data Trans.) │  │   (OpenAPI) │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                   APPLICATION LAYER                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │    Services     │  │   Use Cases     │  │   Mappers   │ │
│  │   (Business)    │  │   (Ports In)    │  │   (DTO↔DO)  │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                     DOMAIN LAYER                           │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │     Models      │  │  Value Objects  │  │  Exceptions │ │
│  │   (Entities)    │  │   (Stock/Rating)│  │   (Domain)  │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                 INFRASTRUCTURE LAYER                       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────┐ │
│  │   Repositories  │  │   Adapters      │  │   External  │ │
│  │   (Ports Out)   │  │   (JSON/CSV)    │  │   Services  │ │
│  └─────────────────┘  └─────────────────┘  └─────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### Principios de Diseño

- **Dependency Inversion**: Las capas superiores no dependen de las inferiores
- **Single Responsibility**: Cada clase tiene una responsabilidad específica
- **Open/Closed**: Abierto para extensión, cerrado para modificación
- **Interface Segregation**: Interfaces específicas y cohesivas

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Endpoints Disponibles

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| `GET` | `/product/{id}` | Obtener producto por ID | `id` (UUID) |
| `GET` | `/product` | Listar todos los productos | Query params opcionales |
| `GET` | `/product/search` | Buscar productos por título | `title` (String) |
| `GET` | `/product/search` | Buscar productos por palabra clave | `keyword` (String) |
| `POST` | `/product` | Crear nuevo producto | Body: `ProductRequestDto` |
| `PUT` | `/product/{id}` | Actualizar producto existente | `id` (UUID) + Body: `ProductRequestDto` |

### Ejemplos de Uso

#### 1. Crear Producto
```bash
curl -X POST http://localhost:8080/api/product \
  -H "Content-Type: application/json" \
  -d '{
    "title": "iPhone 15 Pro Max",
    "description": "Smartphone Apple con las últimas características",
    "price": 1299.99,
    "currency": "USD",
    "availableQuantity": 50,
    "status": "ACTIVE",
    "category": "Electrónicos",
    "subcategory": "Smartphones",
    "sellerId": "550e8400-e29b-41d4-a716-446655440000",
    "sellerName": "TechStore Pro",
    "listingType": "MERCADO_LIBRE",
    "freeShipping": true,
    "shippingCost": 0.00,
    "weight": 0.5,
    "width": 15.0,
    "height": 20.0,
    "length": 8.0,
    "images": ["https://example.com/image1.jpg"],
    "attributes": ["Color: Azul", "Memoria: 256GB"],
    "condition": "NEW",
    "brand": "Apple",
    "model": "iPhone 15 Pro Max",
    "stock": 25,
    "available": true,
    "sku": "IPH15PM-256-NT",
    "barcode": "1234567890123",
    "tags": ["smartphone", "apple", "premium"],
    "warranty": "1 año de garantía del fabricante",
    "returnPolicy": "30 días para devolución"
  }'
```

#### 2. Obtener Producto por ID
```bash
curl -X GET http://localhost:8080/api/product/f47ac10b-58cc-4372-a567-0e02b2c3d479
```

#### 3. Buscar Productos por Palabra Clave
```bash
curl -X GET "http://localhost:8080/api/product/search?keyword=iPhone"
```

## 🏛️ Patrones de Diseño Implementados

### 1. Builder Pattern
Implementado en el modelo `Product` para facilitar la construcción de objetos complejos:

```java
Product product = Product.newBuilder("product-id")
    .basicInfo("iPhone 15", "Smartphone Apple", new BigDecimal("999.99"), "USD")
    .categorization("Electrónicos", "Smartphones")
    .seller("seller-id", "TechStore")
    .listing(ListingType.MERCADO_LIBRE, true)
    .condition(ProductCondition.NEW)
    .stock(50)
    .status(ProductStatus.ACTIVE)
    .shipping(new BigDecimal("0.00"))
    .dimensions(0.5, 15.0, 20.0, 8.0)
    .media(Arrays.asList("image1.jpg"), Arrays.asList("Color: Azul"))
    .identification("SKU-001", "123456789", "Apple", "iPhone 15")
    .policies("1 año", "30 días", Arrays.asList("smartphone"))
    .views(100)
    .sales(10)
    .rating(Rating.of(4.5, 50))
    .lastSoldAt(LocalDateTime.now())
    .build();
```

**Beneficios:**
- ✅ Construcción flexible y legible
- ✅ Validación en tiempo de construcción
- ✅ Inmutabilidad del objeto final
- ✅ Fluent interface para mejor experiencia de desarrollo

### 2. Repository Pattern con Adapters Intercambiables

El sistema implementa dos adaptadores de repositorio que pueden intercambiarse mediante **Dependency Injection**:

#### JSON Repository Adapter
```java
@Repository()
@Profile("json")
public class ProductJsonRepositoryAdapter implements ProductRepository {
    // Implementación usando archivos JSON
    // Ideal para desarrollo y testing
}
```

#### CSV Repository Adapter
```java
@Repository() 
@Profile("csv")
public class ProductCsvRepositoryAdapter implements ProductRepository {
    // Implementación usando archivos CSV
    // Ideal para integración con sistemas legacy
}
```

#### Configuración de Inyección
El adapter inyectado por Spring depende del profile configurado en application.properties.
ejemplo: spring.profiles.active=json

**Beneficios:**
- ✅ **Flexibilidad**: Cambio de implementación sin modificar código
- ✅ **Testabilidad**: Fácil mockeo para tests unitarios
- ✅ **Escalabilidad**: Migración gradual entre sistemas de persistencia
- ✅ **Mantenibilidad**: Separación clara de responsabilidades

### 3. Value Objects
Implementados para encapsular lógica de dominio:

#### Stock Value Object
```java
public class Stock {
    public static Stock of(int quantity) { /* ... */ }
    public Stock add(int quantity) { /* ... */ }
    public Stock reduce(int quantity) { /* ... */ }
    public boolean isEmpty() { /* ... */ }
}
```

#### Rating Value Object
```java
public class Rating {
    public static Rating of(double value, int count) { /* ... */ }
    public Rating addRating(double rating) { /* ... */ }
    public double getValue() { /* ... */ }
    public int getCount() { /* ... */ }
}
```

## 🤖 Integración con Cursor GenAI

### Desarrollo Asistido por IA

Este proyecto fue desarrollado utilizando **Cursor** con su **GenAI** para maximizar la eficiencia.
Se comparte el archivo prompts.md en este mismo proyecto.

#### 1. Generación de Código Inteligente
- **Patrones de diseño**: Implementación del Builder pattern con base a diferentes peticiones.
- **Tests unitarios**: Generación de 115+ tests con cobertura completa
- **Documentación**: Creación automática de documentación OpenAPI

#### 2. Refactoring Inteligente
- **Exception Handling**: Implementación de GlobalExceptionHandler

#### 3. Debugging y Optimización
- **Error Resolution**: Identificación y corrección automática de bugs
- **Testing**: Corrección automática de tests fallidos

#### 4. Beneficios Obtenidos
- ⚡ **Velocidad**: Desarrollo 3x más rápido
- 🎯 **Precisión**: Código más preciso y sin errores
- 📚 **Aprendizaje**: Mejores prácticas implementadas automáticamente
- 🔄 **Iteración**: Ciclos de desarrollo más cortos

## 📊 Características Técnicas

### Stack Tecnológico
- **Java 21** - Lenguaje de programación
- **Spring Boot 3.3.5** - Framework principal
- **Spring Web** - API REST
- **Springdoc OpenAPI 2.3.0** - Documentación de API
- **Jackson** - Serialización JSON
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework
- **Gradle** - Build tool

### Validaciones Implementadas
- **Bean Validation** - Validaciones de entrada
- **Custom Validators** - Validaciones de dominio
- **Exception Handling** - Manejo global de errores
- **Input Sanitization** - Sanitización de datos

### Testing Strategy
- **115 Tests Unitarios** - Cobertura completa
- **AAA Pattern** - Arrange, Act, Assert
- **Mock Objects** - Aislamiento de dependencias
- **Parameterized Tests** - Múltiples escenarios

## 📚 Documentación de API

### Swagger UI
Accede a la documentación interactiva en:
```
http://localhost:8080/swagger-ui.html
```

### OpenAPI JSON
Especificación completa en:
```
http://localhost:8080/v3/api-docs
```

## 🚀 Próximos Pasos

### Funcionalidades Planificadas
- [ ] **Autenticación JWT** - Seguridad de endpoints de creacion y actualizacion de productos.
- [ ] **Base de Datos** - Migración a PostgreSQL/MySQL
- [ ] **Caché Redis** - Optimización de rendimiento
- [ ] **Logging Avanzado** - Monitoreo y observabilidad
- [ ] **Métricas** - Prometheus + Grafana
- [ ] **Docker** - Containerización
- [ ] **CI/CD** - Pipeline de despliegue automático



**Desarrollado con ❤️ usando Cursor GenAI para maximizar la eficiencia y calidad del código.**
