package com.example.eco.Reportes.Services;

import com.example.eco.Model.ReportesModel;
import com.example.eco.Reportes.Repository.ReportesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReportesServices {

    private final ReportesRepository reportesRepository;

    // Obtener todos los reportes
    public List<ReportesModel> getAllReportes() {
        return reportesRepository.findAll();
    }

    // Obtener un reporte por ID
    public Optional<ReportesModel> getReporteById(Integer id) {
        return reportesRepository.findById(id);
    }

    // Guardar un nuevo reporte
    public ReportesModel saveReporte(ReportesModel reporte) {
        return reportesRepository.save(reporte);
    }

    // Actualizar un reporte existente
    public ReportesModel updateReporte(Integer id, ReportesModel reporteDetails) {
        return reportesRepository.findById(id)
                .map(reporte -> {
                    reporte.setNombre(reporteDetails.getNombre());
                    reporte.setDescripcion(reporteDetails.getDescripcion());
                    reporte.setFecha(reporteDetails.getFecha());
                    return reportesRepository.save(reporte);
                })
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con id: " + id));
    }

    // Eliminar un reporte por ID
    public void deleteReporte(Integer id) {
        reportesRepository.deleteById(id);
    }

    // Buscar reportes por nombre
    public List<ReportesModel> getReportesByNombre(String nombre) {
        return reportesRepository.findByNombre(nombre);
    }

    // Buscar reportes por fecha (rango)
    public List<ReportesModel> getReportesByFecha(Date fechaInicio, Date fechaFin) {
        return reportesRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}
