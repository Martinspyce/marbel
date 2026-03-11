package com.example.eco.Orden.Controller;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.eco.Model.OrdenModel;
import com.example.eco.Orden.Services.OrdenServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Órdenes", description = "Operaciones relacionadas con órdenes")
@RestController
@RequestMapping("/api/ordenes")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class OrdenController {

    private final OrdenServices ordenService;

    @Operation(summary = "Obtener todas las órdenes", description = "Lista completa de todas las órdenes registradas.")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OrdenModel>>> getAllOrdenes() {
        List<EntityModel<OrdenModel>> ordenes = ordenService.getAllOrdenes().stream()
                .map(orden -> EntityModel.of(orden,
                        linkTo(methodOn(OrdenController.class).getOrdenById(orden.getId())).withSelfRel(),
                        linkTo(methodOn(OrdenController.class).getAllOrdenes()).withRel("ordenes")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(ordenes,
                linkTo(methodOn(OrdenController.class).getAllOrdenes()).withSelfRel()));
    }

    @Operation(summary = "Obtener orden por ID", description = "Devuelve una orden específica usando su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrdenModel>> getOrdenById(@PathVariable Integer id) {
        return ordenService.getOrdenById(id)
                .map(orden -> EntityModel.of(orden,
                        linkTo(methodOn(OrdenController.class).getOrdenById(id)).withSelfRel(),
                        linkTo(methodOn(OrdenController.class).getAllOrdenes()).withRel("ordenes")))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear nueva orden", description = "Registra una nueva orden en la base de datos.")
    @PostMapping
    public ResponseEntity<EntityModel<OrdenModel>> createOrden(@RequestBody OrdenModel orden) {
        OrdenModel nueva = ordenService.saveOrden(orden);
        return ResponseEntity.ok(EntityModel.of(nueva,
                linkTo(methodOn(OrdenController.class).getOrdenById(nueva.getId())).withSelfRel(),
                linkTo(methodOn(OrdenController.class).getAllOrdenes()).withRel("ordenes")));
    }

    @Operation(summary = "Actualizar orden", description = "Actualiza los datos de una orden existente.")
    @PutMapping("/{id}")
    public ResponseEntity<OrdenModel> updateOrden(@PathVariable Integer id, @RequestBody OrdenModel ordenDetails) {
        try {
            return ResponseEntity.ok(ordenService.updateOrden(id, ordenDetails));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar orden", description = "Elimina una orden del sistema usando su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrden(@PathVariable Integer id) {
        ordenService.deleteOrden(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar órdenes por nombre", description = "Devuelve una lista de órdenes que coinciden con el nombre dado.")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<OrdenModel>> getOrdenesByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(ordenService.getOrdenesByNombre(nombre));
    }

    @Operation(summary = "Buscar órdenes por fecha", description = "Devuelve las órdenes entre dos fechas específicas.")
    @GetMapping("/fecha")
    public ResponseEntity<List<OrdenModel>> getOrdenesByFecha(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin) {
        return ResponseEntity.ok(ordenService.getOrdenesByFecha(fechaInicio, fechaFin));
    }
}

