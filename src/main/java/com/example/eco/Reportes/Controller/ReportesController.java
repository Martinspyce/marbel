package com.example.eco.Reportes.Controller;

import com.example.eco.Model.ReportesModel;
import com.example.eco.Reportes.Services.ReportesServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Tag(name = "Reportes", description = "Operaciones relacionadas con reportes")
@RestController
@RequestMapping("/api/reportes")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class ReportesController {

    private final ReportesServices reportesService;

    @Operation(summary = "Obtener todos los reportes", description = "Devuelve una lista con todos los reportes existentes.")
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ReportesModel>>> getAllReportes() {
        List<EntityModel<ReportesModel>> reportes = reportesService.getAllReportes()
                .stream()
                .map(reporte -> EntityModel.of(reporte,
                        linkTo(methodOn(ReportesController.class).getReporteById(reporte.getId())).withSelfRel(),
                        linkTo(methodOn(ReportesController.class).deleteReporte(reporte.getId())).withRel("delete"),
                        linkTo(methodOn(ReportesController.class).updateReporte(reporte.getId(), reporte)).withRel("update")
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(reportes, linkTo(methodOn(ReportesController.class).getAllReportes()).withSelfRel())
        );
    }

    @Operation(summary = "Obtener reporte por ID", description = "Busca y devuelve un reporte específico por su ID, con enlaces HATEOAS.")
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ReportesModel>> getReporteById(@PathVariable Integer id) {
        return reportesService.getReporteById(id)
                .map(reporte -> EntityModel.of(reporte,
                        linkTo(methodOn(ReportesController.class).getReporteById(id)).withSelfRel(),
                        linkTo(methodOn(ReportesController.class).getAllReportes()).withRel("all-reportes"),
                        linkTo(methodOn(ReportesController.class).deleteReporte(id)).withRel("delete"),
                        linkTo(methodOn(ReportesController.class).updateReporte(id, reporte)).withRel("update")
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear un nuevo reporte", description = "Registra un nuevo reporte en la base de datos.")
    @PostMapping
    public ResponseEntity<EntityModel<ReportesModel>> createReporte(@RequestBody ReportesModel reporte) {
        ReportesModel saved = reportesService.saveReporte(reporte);
        EntityModel<ReportesModel> resource = EntityModel.of(saved,
                linkTo(methodOn(ReportesController.class).getReporteById(saved.getId())).withSelfRel(),
                linkTo(methodOn(ReportesController.class).getAllReportes()).withRel("all-reportes")
        );
        return ResponseEntity.ok(resource);
    }

    @Operation(summary = "Actualizar un reporte", description = "Actualiza los datos de un reporte existente.")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ReportesModel>> updateReporte(@PathVariable Integer id, @RequestBody ReportesModel reporteDetails) {
        try {
            ReportesModel updated = reportesService.updateReporte(id, reporteDetails);
            EntityModel<ReportesModel> resource = EntityModel.of(updated,
                    linkTo(methodOn(ReportesController.class).getReporteById(id)).withSelfRel(),
                    linkTo(methodOn(ReportesController.class).getAllReportes()).withRel("all-reportes")
            );
            return ResponseEntity.ok(resource);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un reporte", description = "Elimina un reporte específico por ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Integer id) {
        reportesService.deleteReporte(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar reportes por nombre", description = "Obtiene una lista de reportes con el nombre dado.")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<ReportesModel>> getReportesByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(reportesService.getReportesByNombre(nombre));
    }

    @Operation(summary = "Buscar reportes por fecha", description = "Obtiene reportes entre dos fechas específicas.")
    @GetMapping("/fecha")
    public ResponseEntity<List<ReportesModel>> getReportesByFecha(
            @RequestParam Date fechaInicio,
            @RequestParam Date fechaFin) {
        return ResponseEntity.ok(reportesService.getReportesByFecha(fechaInicio, fechaFin));
    }
}

