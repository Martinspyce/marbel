package com.example.eco.Reportes.Repository;

import com.example.eco.Model.ReportesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface ReportesRepository extends JpaRepository<ReportesModel, Integer> {

    List<ReportesModel> findByNombre(String nombre);

    List<ReportesModel> findByFechaBetween(Date fechaInicio, Date fechaFin);

    @Query("SELECT r FROM ReportesModel r WHERE r.descripcion LIKE %:descripcion%")
    List<ReportesModel> findByDescripcionContaining(@Param("descripcion") String descripcion);
}
