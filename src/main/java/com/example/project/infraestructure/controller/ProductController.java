package com.example.project.infraestructure.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.project.application.port.in.ProductUseCase;
import com.example.project.infraestructure.dto.ProductRequestDto;
import com.example.project.infraestructure.dto.ProductResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Controlador REST para gestión de productos
 * 
 * Endpoints disponibles:
 * - GET /api/product/{id} - Buscar producto por ID
 * - GET /api/product/title/{title} - Buscar producto por título
 * - GET /api/product/search?keyword={keyword} - Buscar productos por palabra clave
 * - GET /api/product - Listar todos los productos
 * - POST /api/product - Crear nuevo producto
 * - PUT /api/product/{id} - Actualizar producto existente
 * 
 * Documentación disponible en: http://localhost:8080/swagger-ui.html
 */
@RestController
@RequestMapping("/api/product")
@Tag(name = "Product Management", description = "API para gestión de productos - Operaciones CRUD y búsquedas")
public class ProductController {

    private final ProductUseCase productUseCase;

    public ProductController(ProductUseCase productUseCase){
        this.productUseCase = productUseCase;
    }

    /**
     * Listar todos los productos disponibles
     * 
     * @return Lista de todos los productos
     */
    @GetMapping
    @Operation(
        summary = "Listar todos los productos",
        description = "Obtiene una lista de todos los productos disponibles en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de productos obtenida exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDto.class)
            )
        )
    })
    public ResponseEntity<List<ProductResponseDto>> listAll() {
        System.out.println("Listando todos los productos");
        List<ProductResponseDto> product = productUseCase.listAll();
        return ResponseEntity.ok(product);
    }

    /**
     * Buscar producto por ID único (UUID)
     * 
     * @param id ID único del producto
     * @return Producto encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar producto por ID",
        description = "Obtiene un producto específico utilizando su identificador único (UUID)"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    public ResponseEntity<ProductResponseDto> findProductById(
        @Parameter(description = "ID único del producto (UUID)", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        @PathVariable String id
    ){
        Optional<ProductResponseDto> product = productUseCase.filterById(id);
        return product.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Buscar producto por título exacto
     * 
     * @param title Título exacto del producto
     * @return Producto encontrado o 404 si no existe
     */
    @GetMapping("/title/{title}")
    @Operation(
        summary = "Buscar producto por título exacto",
        description = "Busca un producto específico utilizando su título exacto"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    public ResponseEntity<ProductResponseDto> findProductByTitle(
        @Parameter(description = "Título exacto del producto", example = "iPhone 15 Pro Max")
        @PathVariable String title
    ){
        Optional<ProductResponseDto> product = productUseCase.filterByTitle(title);
        return product.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Buscar productos por palabra clave
     * 
     * Busca en los siguientes campos:
     * - Título
     * - Categoría
     * - Subcategoría
     * - Marca
     * - Descripción
     * - Tags
     * - Modelo
     * - SKU
     * 
     * @param keyword Palabra clave para buscar
     * @return Lista de productos encontrados o 404 si no hay resultados
     */
    @GetMapping("/search")
    @Operation(
        summary = "Buscar productos por palabra clave",
        description = "Busca productos utilizando una palabra clave que puede coincidir con título, categoría, marca, descripción, tags, modelo o SKU"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Productos encontrados exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraron productos con la palabra clave especificada"
        )
    })
    public ResponseEntity<List<ProductResponseDto>> findProductByKeyword(
        @Parameter(description = "Palabra clave para buscar en título, categoría, marca, descripción, etc.", example = "iPhone")
        @RequestParam("keyword") String keyword
    ){
        Optional<List<ProductResponseDto>> product = productUseCase.filterByKeyword(keyword);
        return product.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo producto
     * 
     * @param productDto Datos del producto a crear
     * @return Producto creado con ID asignado
     */
    @PostMapping
    @Operation(
        summary = "Crear nuevo producto",
        description = "Crea un nuevo producto en el sistema con los datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto creado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos"
        )
    })
    public ResponseEntity<ProductResponseDto> createProduct(
        @Parameter(description = "Datos del producto a crear")
        @Valid @RequestBody ProductRequestDto productDto
    ) {
        ProductResponseDto created = productUseCase.createProduct(productDto);
        return ResponseEntity.ok(created);
    }

    /**
     * Actualizar un producto existente
     * 
     * @param id ID del producto a actualizar
     * @param productDto Datos actualizados del producto
     * @return Producto actualizado o 404 si no existe
     */
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar producto existente",
        description = "Actualiza un producto existente con los nuevos datos proporcionados"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ProductResponseDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos de entrada inválidos"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado"
        )
    })
    public ResponseEntity<ProductResponseDto> updateProduct(
        @Parameter(description = "ID único del producto a actualizar (UUID)", example = "f47ac10b-58cc-4372-a567-0e02b2c3d479")
        @PathVariable String id, 
        @Parameter(description = "Datos actualizados del producto")
        @Valid @RequestBody ProductRequestDto productDto
    ) {
        ProductResponseDto updated = productUseCase.updateProduct(id, productDto);
        return ResponseEntity.ok(updated);
    }
}
