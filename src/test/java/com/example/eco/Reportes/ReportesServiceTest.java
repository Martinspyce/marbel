package com.example.eco.Reportes;

import com.example.eco.Model.ReportesModel;
import com.example.eco.Reportes.Repository.ReportesRepository;
import com.example.eco.Reportes.Services.ReportesServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportesServiceTest {

    @Mock
    private ReportesRepository reportesRepository;

    @InjectMocks
    private ReportesServices reportesServices;

    private ReportesModel reporteEjemplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reporteEjemplo = new ReportesModel();
        reporteEjemplo.setId(1);
        reporteEjemplo.setNombre("Informe de ventas");
        reporteEjemplo.setDescripcion("Reporte mensual");
        reporteEjemplo.setFecha(new Date());
    }

    @Test
    void testGetAllReportes() {
        when(reportesRepository.findAll()).thenReturn(Arrays.asList(reporteEjemplo));
        List<ReportesModel> reportes = reportesServices.getAllReportes();

        assertEquals(1, reportes.size());
        assertEquals("Informe de ventas", reportes.get(0).getNombre());
        verify(reportesRepository, times(1)).findAll();
    }

    @Test
    void testGetReporteById() {
        when(reportesRepository.findById(1)).thenReturn(Optional.of(reporteEjemplo));

        Optional<ReportesModel> result = reportesServices.getReporteById(1);

        assertTrue(result.isPresent());
        assertEquals("Informe de ventas", result.get().getNombre());
        verify(reportesRepository).findById(1);
    }

    @Test
    void testSaveReporte() {
        when(reportesRepository.save(any(ReportesModel.class))).thenReturn(reporteEjemplo);

        ReportesModel saved = reportesServices.saveReporte(reporteEjemplo);

        assertNotNull(saved);
        assertEquals("Informe de ventas", saved.getNombre());
        verify(reportesRepository).save(reporteEjemplo);
    }

    @Test
    void testUpdateReporteSuccess() {
        ReportesModel actualizado = new ReportesModel();
        actualizado.setNombre("Nuevo nombre");
        actualizado.setDescripcion("Actualizado");
        actualizado.setFecha(new Date());

        when(reportesRepository.findById(1)).thenReturn(Optional.of(reporteEjemplo));
        when(reportesRepository.save(any(ReportesModel.class))).thenReturn(actualizado);

        ReportesModel result = reportesServices.updateReporte(1, actualizado);

        assertEquals("Nuevo nombre", result.getNombre());
        assertEquals("Actualizado", result.getDescripcion());
        verify(reportesRepository).findById(1);
        verify(reportesRepository).save(any(ReportesModel.class));
    }

    @Test
    void testDeleteReporte() {
        doNothing().when(reportesRepository).deleteById(1);

        reportesServices.deleteReporte(1);

        verify(reportesRepository).deleteById(1);
    }
}
