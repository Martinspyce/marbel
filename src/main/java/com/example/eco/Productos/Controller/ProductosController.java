package com.example.eco.Productos.Controller;

import com.example.eco.Model.ProductosModel;
import com.example.eco.Productos.Services.ProductosServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ProductosController {

    private final ProductosServices productosService;

    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista completa de productos.")
    @GetMapping
    public ResponseEntity<List<EntityModel<ProductosModel>>> getAllProductos() {
        List<EntityModel<ProductosModel>> productosConLinks = productosService.getAllProductos()
                .stream()
                .map(producto -> EntityModel.of(producto,
                        linkTo(methodOn(ProductosController.class).getProductoById(producto.getId())).withSelfRel(),
                        linkTo(methodOn(ProductosController.class).getAllProductos()).withRel("all")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productosConLinks);
    }

    @Operation(summary = "Obtener producto por ID", description = "Busca un producto específico mediante su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProductosModel>> getProductoById(@PathVariable Integer id) {
        return productosService.getProductoById(id)
                .map(producto -> EntityModel.of(producto,
                        linkTo(methodOn(ProductosController.class).getProductoById(id)).withSelfRel(),
                        linkTo(methodOn(ProductosController.class).getAllProductos()).withRel("all")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nuevo producto", description = "Registra un nuevo producto en el sistema.")
    @PostMapping
    public ResponseEntity<EntityModel<ProductosModel>> createProducto(@RequestBody ProductosModel producto) {
        ProductosModel nuevoProducto = productosService.saveProducto(producto);
        EntityModel<ProductosModel> productoModel = EntityModel.of(nuevoProducto,
                linkTo(methodOn(ProductosController.class).getProductoById(nuevoProducto.getId())).withSelfRel(),
                linkTo(methodOn(ProductosController.class).getAllProductos()).withRel("all"));

        return ResponseEntity.created(
                        linkTo(methodOn(ProductosController.class).getProductoById(nuevoProducto.getId())).toUri())
                .body(productoModel);
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente con nuevos datos.")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProductosModel>> updateProducto(@PathVariable Integer id, @RequestBody ProductosModel productoDetails) {
        try {
            ProductosModel actualizado = productosService.updateProducto(id, productoDetails);
            EntityModel<ProductosModel> productoModel = EntityModel.of(actualizado,
                    linkTo(methodOn(ProductosController.class).getProductoById(actualizado.getId())).withSelfRel(),
                    linkTo(methodOn(ProductosController.class).getAllProductos()).withRel("all"));
            return ResponseEntity.ok(productoModel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        productosService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar productos por nombre", description = "Busca productos que coincidan con el nombre especificado.")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<EntityModel<ProductosModel>>> getProductosByNombre(@PathVariable String nombre) {
        List<EntityModel<ProductosModel>> productos = productosService.getProductosByNombre(nombre).stream()
                .map(p -> EntityModel.of(p,
                        linkTo(methodOn(ProductosController.class).getProductoById(p.getId())).withSelfRel(),
                        linkTo(methodOn(ProductosController.class).getAllProductos()).withRel("all")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Buscar productos por precio", description = "Busca productos en un rango entre precio mínimo y máximo.")
    @GetMapping("/precio")
    public ResponseEntity<List<EntityModel<ProductosModel>>> getProductosByPrecio(
            @RequestParam Integer precioMin,
            @RequestParam Integer precioMax) {
        List<EntityModel<ProductosModel>> productos = productosService.getProductosByPrecio(precioMin, precioMax).stream()
                .map(p -> EntityModel.of(p,
                        linkTo(methodOn(ProductosController.class).getProductoById(p.getId())).withSelfRel(),
                        linkTo(methodOn(ProductosController.class).getAllProductos()).withRel("all")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productos);
    }
}
