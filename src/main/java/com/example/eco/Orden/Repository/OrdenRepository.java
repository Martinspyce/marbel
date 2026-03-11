package com.example.eco.Orden.Repository; // Asegúrate de que este paquete sea correcto

import com.example.eco.Model.OrdenModel; // Importa tu modelo de OrdenModel
import org.springframework.data.jpa.repository.JpaRepository;
// No necesitas @Query ni @Param si solo usas los métodos derivados de Spring Data JPA para estos nuevos métodos.
// Si tenías @Query o @Param para otros métodos, déjalos.
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date; // Necesario para el método findByFechaBetween
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenRepository extends JpaRepository<OrdenModel, Integer> {

    // Método necesario para getOrdenesByNombre en OrdenServices
    // Asumo que tu OrdenModel tiene un campo 'nombre'.
    // Si el campo es 'titulo' o similar, ajusta el nombre del método findByTitulo
    List<OrdenModel> findByNombre(String nombre); // <--- ¡AÑADIR ESTE MÉTODO!

    // Método necesario para getOrdenesByFecha en OrdenServices
    // Asumo que tu OrdenModel tiene un campo 'fecha' de tipo Date.
    List<OrdenModel> findByFechaBetween(Date fechaInicio, Date fechaFin); // <--- ¡AÑADIR ESTE MÉTODO!

    // Los demás métodos que ya tenías en OrdenServices (como findById, findAll, save, deleteById)
    // son manejados automáticamente por JpaRepository.

    // Si tu OrdenServices tiene otros métodos que usan convenciones de findBy... o @Query,
    // asegúrate de que también existan aquí.
}