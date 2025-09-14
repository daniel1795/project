# 🚨 Sistema de Manejo de Errores - Documentación

## 📋 **Resumen del Sistema**

Hemos implementado un sistema completo de manejo de errores que proporciona respuestas estructuradas, amigables y seguras para el usuario, siguiendo las mejores prácticas de APIs REST.

---

## 🏗️ **Arquitectura del Sistema**

### **Componentes Implementados:**

1. **ErrorResponse.java** - DTO principal para respuestas de error
2. **ValidationError.java** - DTO para errores de validación específicos
3. **ProductNotFoundException.java** - Excepción personalizada para productos no encontrados
4. **BusinessException.java** - Excepción base para errores de negocio
5. **GlobalExceptionHandler.java** - Manejador centralizado de excepciones

---

## 📝 **Tipos de Errores Manejados**

### **1. Errores de Validación (400 Bad Request)**

**Cuando ocurre:** Validación de DTOs fallida (campos requeridos, formatos incorrectos, etc.)

**Ejemplo de Request con errores:**
```json
{
  "title": "",
  "price": -10,
  "freeShipping": null,
  "condition": null
}
```

**Respuesta del Sistema:**
```json
{
  "error": "VALIDATION_ERROR",
  "message": "Los datos enviados no son válidos",
  "details": [
    {
      "field": "title",
      "message": "El título del producto es obligatorio",
      "code": "VALIDATION_ERROR",
      "rejectedValue": ""
    },
    {
      "field": "price",
      "message": "El precio debe ser mayor a 0",
      "code": "VALIDATION_ERROR",
      "rejectedValue": -10
    },
    {
      "field": "freeShipping",
      "message": "El envío es obligatorio",
      "code": "VALIDATION_ERROR",
      "rejectedValue": null
    },
    {
      "field": "condition",
      "message": "La condición del producto es obligatoria",
      "code": "VALIDATION_ERROR",
      "rejectedValue": null
    }
  ],
  "timestamp": "2024-01-15T10:30:45.123",
  "path": "/api/products",
  "traceId": "a1b2c3d4"
}
```

### **2. Producto No Encontrado (404 Not Found)**

**Cuando ocurre:** Intentar actualizar un producto que no existe

**Ejemplo de Request:**
```http
PUT /api/products/producto-inexistente-123
```

**Respuesta del Sistema:**
```json
{
  "error": "PRODUCT_NOT_FOUND",
  "message": "Producto no encontrado con ID: producto-inexistente-123",
  "timestamp": "2024-01-15T10:30:45.123",
  "path": "/api/products/producto-inexistente-123",
  "traceId": "e5f6g7h8"
}
```

### **3. Errores de Negocio (400 Bad Request)**

**Cuando ocurre:** Violaciones de reglas de negocio

**Ejemplo de Request:**
```json
{
  "title": "Producto Test",
  "price": 100.00,
  "availableQuantity": -5
}
```

**Respuesta del Sistema:**
```json
{
  "error": "BUSINESS_ERROR",
  "message": "La cantidad disponible no puede ser negativa",
  "timestamp": "2024-01-15T10:30:45.123",
  "path": "/api/products",
  "traceId": "i9j0k1l2"
}
```

### **4. Errores Internos (500 Internal Server Error)**

**Cuando ocurre:** Errores no controlados del sistema

**Respuesta del Sistema:**
```json
{
  "error": "INTERNAL_ERROR",
  "message": "Ha ocurrido un error interno. Por favor, inténtalo de nuevo.",
  "timestamp": "2024-01-15T10:30:45.123",
  "path": "/api/products",
  "traceId": "m3n4o5p6"
}
```

---

## 🔧 **Características del Sistema**

### **✅ Beneficios para el Usuario:**
- **Mensajes claros** en español
- **Campos específicos** que necesitan corrección
- **Códigos de error** para manejo programático
- **Información útil** para solucionar problemas

### **✅ Beneficios para el Frontend:**
- **Estructura consistente** de errores
- **Fácil mapeo** de errores a campos del formulario
- **Códigos de error** para lógica condicional
- **TraceId** para debugging y soporte

### **✅ Beneficios para Seguridad:**
- **No expone detalles internos** del sistema
- **No revela estructura** de la base de datos
- **Información controlada** y filtrada
- **Logging seguro** con traceId

### **✅ Beneficios para Desarrollo:**
- **Manejo centralizado** de errores
- **Logging estructurado** para debugging
- **Fácil extensión** para nuevos tipos de error
- **Consistencia** en toda la aplicación

---

## 📊 **Códigos de Error Disponibles**

| Código | Descripción | HTTP Status |
|--------|-------------|-------------|
| `VALIDATION_ERROR` | Errores de validación de campos | 400 |
| `PRODUCT_NOT_FOUND` | Producto no encontrado | 404 |
| `BUSINESS_ERROR` | Errores de reglas de negocio | 400 |
| `INTERNAL_ERROR` | Errores internos del sistema | 500 |
| `UNKNOWN_ERROR` | Errores no controlados | 500 |

---

## 🎯 **Ejemplos de Uso en Frontend**

### **Manejo de Errores de Validación:**
```javascript
try {
  const response = await fetch('/api/products', {
    method: 'POST',
    body: JSON.stringify(productData)
  });
  
  if (!response.ok) {
    const error = await response.json();
    
    if (error.error === 'VALIDATION_ERROR') {
      // Mostrar errores específicos por campo
      error.details.forEach(detail => {
        showFieldError(detail.field, detail.message);
      });
    }
  }
} catch (error) {
  console.error('Error:', error);
}
```

### **Manejo de Producto No Encontrado:**
```javascript
if (error.error === 'PRODUCT_NOT_FOUND') {
  showNotification('El producto que buscas no existe', 'error');
  redirectToProductList();
}
```

### **Manejo de Errores de Negocio:**
```javascript
if (error.error === 'BUSINESS_ERROR') {
  showNotification(error.message, 'warning');
}
```

---

## 🔍 **Logging y Debugging**

### **Logs Generados:**
```
2024-01-15 10:30:45.123 WARN  [a1b2c3d4] Validation error on path /api/products: [field: title, message: El título del producto es obligatorio]
2024-01-15 10:30:45.124 WARN  [e5f6g7h8] Product not found on path /api/products/producto-inexistente-123: Producto no encontrado con ID: producto-inexistente-123
2024-01-15 10:30:45.125 ERROR [m3n4o5p6] Runtime error on path /api/products: Database connection failed
```

### **Uso del TraceId:**
- **Frontend**: Incluir traceId en reportes de error
- **Soporte**: Usar traceId para buscar logs específicos
- **Debugging**: Rastrear errores a través de múltiples capas

---

## 🚀 **Próximos Pasos Recomendados**

1. **Rate Limiting**: Implementar límites de requests
2. **Métricas**: Agregar métricas de errores
3. **Alertas**: Configurar alertas para errores críticos
4. **Documentación**: Generar documentación automática de errores
5. **Testing**: Crear tests para todos los escenarios de error

---

## 📚 **Referencias**

- [Spring Boot Exception Handling](https://spring.io/guides/gs/rest-service/)
- [REST API Error Handling Best Practices](https://restfulapi.net/error-handling/)
- [Jakarta Validation](https://jakarta.ee/specifications/bean-validation/)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
