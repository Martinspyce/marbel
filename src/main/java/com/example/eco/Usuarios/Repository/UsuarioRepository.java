package com.example.eco.Usuarios.Repository; // Asegúrate de que este paquete sea correcto

import com.example.eco.Model.UsuarioModel; // Importa tu modelo de UsuarioModel
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // <-- ¡Esta importación es crucial!
import org.springframework.data.repository.query.Param; // <-- ¡Esta importación es crucial!
import org.springframework.stereotype.Repository;

import java.util.List; // <-- ¡Esta importación es crucial para List!
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

    Optional<UsuarioModel> findByNombre(String nombre);

    // Este es el método CRÍTICO para que Spring Security pueda cargar el usuario por su email
    Optional<UsuarioModel> findByEmail(String email);

    List<UsuarioModel> findByApellido(String apellido);

    Optional<UsuarioModel> findByTelefono(String telefono);

    // La anotación @Query utiliza Strings y otros tipos de Java, que necesitan estar importados
    // Asegúrate de que la consulta JPQL sea correcta (parece estarlo)
    @Query("SELECT u FROM UsuarioModel u WHERE u.nombre LIKE %:nombre% OR u.apellido LIKE %:apellido%")
    List<UsuarioModel> findByNombreOrApellidoContaining(@Param("nombre") String nombre, @Param("apellido") String apellido);
}