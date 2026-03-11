package com.example.eco.Productos.Repository;

import com.example.eco.Model.ProductosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<ProductosModel, Integer> {

    List<ProductosModel> findByNombre(String nombre);

    List<ProductosModel> findByPrecioBetween(Integer precioMin, Integer precioMax);

    @Query("SELECT p FROM ProductosModel p WHERE p.descripcion LIKE %:descripcion%")
    List<ProductosModel> findByDescripcionContaining(@Param("descripcion") String descripcion);

    @Query("SELECT p FROM ProductosModel p WHERE p.precio <= :precio")
    List<ProductosModel> findByPrecioLessThanEqual(@Param("precio") Integer precio);
}