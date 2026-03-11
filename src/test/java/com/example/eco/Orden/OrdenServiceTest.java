package com.example.eco.Orden;

import com.example.eco.Model.OrdenModel;
import com.example.eco.Orden.Repository.OrdenRepository;
import com.example.eco.Orden.Services.OrdenServices;
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

public class OrdenServiceTest {

    @Mock
    private OrdenRepository ordenRepository;

    @InjectMocks
    private OrdenServices ordenServices;

    private OrdenModel ordenEjemplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ordenEjemplo = new OrdenModel();
        ordenEjemplo.setId(1);
        ordenEjemplo.setNombre("Juan");
        ordenEjemplo.setFecha(new Date());
        ordenEjemplo.setDescripcion("Pedido de frutas");
    }

    @Test
    void testGetAllOrdenes() {
        when(ordenRepository.findAll()).thenReturn(Arrays.asList(ordenEjemplo));
        List<OrdenModel> ordenes = ordenServices.getAllOrdenes();

        assertEquals(1, ordenes.size());
        verify(ordenRepository, times(1)).findAll();
    }

    @Test
    void testGetOrdenById() {
        when(ordenRepository.findById(1)).thenReturn(Optional.of(ordenEjemplo));
        Optional<OrdenModel> resultado = ordenServices.getOrdenById(1);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
        verify(ordenRepository).findById(1);
    }

    @Test
    void testSaveOrden() {
        when(ordenRepository.save(any(OrdenModel.class))).thenReturn(ordenEjemplo);
        OrdenModel guardada = ordenServices.saveOrden(ordenEjemplo);

        assertNotNull(guardada);
        verify(ordenRepository).save(ordenEjemplo);
    }

    @Test
    void testUpdateOrden() {
        OrdenModel cambios = new OrdenModel();
        cambios.setNombre("Pedro");
        cambios.setDescripcion("Actualización de pedido");
        cambios.setFecha(new Date());

        when(ordenRepository.findById(1)).thenReturn(Optional.of(ordenEjemplo));
        when(ordenRepository.save(any(OrdenModel.class))).thenReturn(ordenEjemplo);

        OrdenModel actualizada = ordenServices.updateOrden(1, cambios);

        assertEquals("Pedro", actualizada.getNombre());
        assertEquals("Actualización de pedido", actualizada.getDescripcion());
        verify(ordenRepository).findById(1);
        verify(ordenRepository).save(any(OrdenModel.class));
    }

    @Test
    void testDeleteOrden() {
        doNothing().when(ordenRepository).deleteById(1);
        ordenServices.deleteOrden(1);

        verify(ordenRepository).deleteById(1);
    }
}
