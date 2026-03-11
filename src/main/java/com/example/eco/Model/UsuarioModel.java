package com.example.eco.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "Usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String apellido;

    @Column(nullable = false, unique = true) // El email DEBE ser único para el login
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;

    @Column(nullable = false) // Campo para la contraseña encriptada
    private String password;

    @Column(nullable = false, length = 50) // Campo para el rol del usuario (ej. "ADMIN", "USER")
    private String rol;
}