# 🚀 Guía de Ejecución - Product Management API

## 📋 Prerrequisitos

### Software Requerido
- **Java 21** o superior
- **Gradle 8.14.3** o superior
- **Git** (para clonar el repositorio)
    https://github.com/daniel1795/project

### Verificar Instalaciones
```bash
# Verificar Java
java -version
# Debe mostrar: openjdk version "21.x.x"

# Verificar Gradle
./gradlew --version
# Debe mostrar: Gradle 8.14.3

# Verificar Git
git --version
```

## 📥 Instalación

### 1. Clonar el Repositorio
```bash
git clone <https://github.com/daniel1795/project.git>
cd project
```

### 2. Verificar Estructura del Proyecto
- Verificar que existe build.gradle
- Verificar estructura de directorios

### 3. Compilar el Proyecto
```bash
# Compilar sin ejecutar tests
./gradlew build -x test

# O compilar con tests
./gradlew build
```

## 🏃‍♂️ Ejecución

### 1. Ejecutar la Aplicación
```bash
# Usando Gradle
./gradlew bootRun

### 2. Verificar que la Aplicación Está Ejecutándose
```bash
# Verificar que el puerto 8080 está en uso
netstat -an | grep 8080

# O hacer una petición de prueba
curl http://localhost:8080
```

### 3. Acceder a la Documentación
- **Swagger UI**: http://localhost:8080/swagger-ui.html

## 🧪 Testing

### 1. Ejecutar Todos los Tests
```bash
# Ejecutar todos los tests
./gradlew test

# Ejecutar con reporte detallado
./gradlew test --info

# Ejecutar tests específicos
./gradlew test --tests "*ProductControllerTest*"
./gradlew test --tests "*ProductServiceTest*"
```

### 2. Ver Reportes de Tests
```bash
# Abrir reporte HTML de tests
open build/reports/tests/test/index.html

# O en Windows
start build/reports/tests/test/index.html
```

### 3. Ejecutar Tests con Cobertura
```bash
# Si tienes plugin de cobertura configurado
./gradlew test jacocoTestReport

# Ver reporte de cobertura
open build/reports/jacoco/test/html/index.html
```

## 🔧 Configuración

### 1. Archivos de Configuración
```bash
# Configuración principal
cat src/main/resources/application.properties
```

### 2. Variables de Entorno
```bash
# Configurar puerto personalizado
export SERVER_PORT=9090
./gradlew bootRun

# Configurar perfil activo
export SPRING_PROFILES_ACTIVE=dev
./gradlew bootRun
```

### 3. Debugging con IDE
```bash
# Ejecutar en modo debug
./gradlew bootRun --debug-jvm

# Conectar debugger en puerto 5005
```

## 🌐 Pruebas de la API

### 1. Crear un Producto
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

### 2. Obtener Producto por ID
```bash
# Reemplazar {id} con el ID real del producto
curl -X GET http://localhost:8080/api/product/{id}
```

### 3. Listar Todos los Productos
```bash
curl -X GET http://localhost:8080/api/product
```

### 4. Buscar Productos
```bash
# Por título
curl -X GET "http://localhost:8080/api/product/search?title=iPhone"

# Por palabra clave
curl -X GET "http://localhost:8080/api/product/search?keyword=Apple"
```

### 5. Actualizar Producto
```bash
curl -X PUT http://localhost:8080/api/product/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "title": "iPhone 15 Pro Max - Actualizado",
    "price": 1199.99,
    "stock": 30
  }'
```

## 🐛 Solución de Problemas

### 1. Puerto en Uso
```bash
# Error: Port 8080 was already in use
# Solución: Cambiar puerto
./gradlew bootRun --args='--server.port=9090'
```

### 2. Tests Fallando
```bash
# Ver detalles del error
./gradlew test --info

# Ejecutar test específico con debug
./gradlew test --tests "*ProductControllerTest*" --debug
```

### 3. Problemas de Compilación
```bash
# Limpiar y recompilar
./gradlew clean build

# Verificar dependencias
./gradlew dependencies
```

### 4. Problemas de Memoria
```bash
# Aumentar memoria para Gradle
export GRADLE_OPTS="-Xmx2g -XX:MaxMetaspaceSize=512m"
./gradlew bootRun
```

## 📁 Estructura de Archivos Importantes

```
project/
├── build.gradle                 # Configuración de Gradle
├── src/
│   ├── main/
│   │   ├── java/               # Código fuente
│   │   └── resources/
│   │       ├── application.properties  # Configuración
│   │       └── static/         # Archivos estáticos
│   └── test/                   # Tests unitarios
├── data/                       # Datos de prueba
│   ├── products.json          # Repositorio JSON
│   └── products.csv           # Repositorio CSV
└── build/                     # Archivos compilados
    ├── libs/                  # JARs generados
    └── reports/               # Reportes de tests
```

## 🔄 Comandos Útiles

### Desarrollo
```bash
# Compilar y ejecutar
./gradlew bootRun

# Ejecutar tests
./gradlew test

# Limpiar proyecto
./gradlew clean

# Ver tareas disponibles
./gradlew tasks
```
---

**¡La aplicación está lista para ejecutarse! 🎉**

Para más información, consulta el [README.md](README.md) o la [documentación de la API](http://localhost:8080/swagger-ui.html).
