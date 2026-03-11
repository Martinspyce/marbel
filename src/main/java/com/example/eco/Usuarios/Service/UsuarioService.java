package com.example.eco.Usuarios.Service;

import com.example.eco.Model.UsuarioModel;
import com.example.eco.Usuarios.Repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Guardar nuevo usuario (encripta la contraseña y asigna rol si falta)
    public UsuarioModel saveUsuario(UsuarioModel usuario) {
        // Encripta la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asigna un rol por defecto si no se proporciona
        if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
            usuario.setRol("USER"); // Puede ser "USER" o "CLIENT"
        }

        return usuarioRepository.save(usuario);
    }

    // Obtener todos los usuarios
    public List<UsuarioModel> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por ID
    public Optional<UsuarioModel> getUsuarioById(Integer id) {
        return usuarioRepository.findById(id);
    }

    // Actualizar usuario por ID
    public UsuarioModel updateUsuario(Integer id, UsuarioModel usuarioDetails) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioDetails.getNombre());
                    usuario.setApellido(usuarioDetails.getApellido());
                    usuario.setEmail(usuarioDetails.getEmail());
                    usuario.setFechaNacimiento(usuarioDetails.getFechaNacimiento());

                    // Si la contraseña cambió, la encriptamos nuevamente
                    if (usuarioDetails.getPassword() != null &&
                            !usuarioDetails.getPassword().isEmpty() &&
                            !usuarioDetails.getPassword().equals(usuario.getPassword())) {
                        usuario.setPassword(passwordEncoder.encode(usuarioDetails.getPassword()));
                    }

                    // Si también cambia el rol (opcional)
                    if (usuarioDetails.getRol() != null && !usuarioDetails.getRol().isEmpty()) {
                        usuario.setRol(usuarioDetails.getRol());
                    }

                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // Eliminar un usuario por ID
    public void deleteUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // Buscar usuario por email
    public Optional<UsuarioModel> getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Buscar usuario por nombre
    public Optional<UsuarioModel> getUsuarioByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }

    // Buscar usuarios por apellido
    public List<UsuarioModel> getUsuariosByApellido(String apellido) {
        return usuarioRepository.findByApellido(apellido);
    }
}
