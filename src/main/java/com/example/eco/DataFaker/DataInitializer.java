package com.example.eco.DataFaker;

import com.example.eco.Model.UsuarioModel;
import com.example.eco.Usuarios.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin";
        String adminPasswordRaw = "messi";
        String adminRol = "USER"; // o "ADMIN"

        Optional<UsuarioModel> existingAdmin = usuarioRepository.findByEmail(adminEmail);

        if (existingAdmin.isEmpty()) {
            UsuarioModel adminUser = new UsuarioModel();
            adminUser.setNombre("Administrador");
            adminUser.setApellido("Sistema");
            adminUser.setEmail(adminEmail);
            adminUser.setTelefono("555000000");
            adminUser.setFechaNacimiento(new Date(90, 0, 1)); // Año 1990
            adminUser.setPassword(passwordEncoder.encode(adminPasswordRaw));
            adminUser.setRol(adminRol);

            usuarioRepository.save(adminUser);
            System.out.println(">> ✅ Usuario administrador por defecto creado.");
            System.out.println("   Email: " + adminEmail);
            System.out.println("   Contraseña: " + adminPasswordRaw + " (¡no compartir en producción!)");
        } else {
            UsuarioModel adminUser = existingAdmin.get();
            adminUser.setPassword(passwordEncoder.encode(adminPasswordRaw));
            usuarioRepository.save(adminUser);
            System.out.println(">> 🔄 Contraseña del usuario admin actualizada.");
            System.out.println("   Email: " + adminEmail);
            System.out.println("   Nueva contraseña: " + adminPasswordRaw);
        }
    }
}
