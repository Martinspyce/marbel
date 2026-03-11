package com.example.eco.Orden.Services;

import com.example.eco.Model.OrdenModel;
import com.example.eco.Orden.Repository.OrdenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrdenServices {

    private final OrdenRepository ordenRepository;

    // Obtener todas las órdenes
    public List<OrdenModel> getAllOrdenes() {
        return ordenRepository.findAll();
    }

    // Obtener una orden por ID
    public Optional<OrdenModel> getOrdenById(Integer id) {
        return ordenRepository.findById(id);
    }

    // Guardar una nueva orden
    public OrdenModel saveOrden(OrdenModel orden) {
        return ordenRepository.save(orden);
    }

    // Actualizar una orden existente
    public OrdenModel updateOrden(Integer id, OrdenModel ordenDetails) {
        return ordenRepository.findById(id)
                .map(orden -> {
                    orden.setNombre(ordenDetails.getNombre());
                    orden.setFecha(ordenDetails.getFecha());
                    orden.setDescripcion(ordenDetails.getDescripcion());
                    return ordenRepository.save(orden);
                })
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con id: " + id));
    }

    // Eliminar una orden por ID
    public void deleteOrden(Integer id) {
        ordenRepository.deleteById(id);
    }

    // Buscar órdenes por nombre
    public List<OrdenModel> getOrdenesByNombre(String nombre) {
        return ordenRepository.findByNombre(nombre);
    }

    // Buscar órdenes entre dos fechas
    public List<OrdenModel> getOrdenesByFecha(Date fechaInicio, Date fechaFin) {
        return ordenRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}


