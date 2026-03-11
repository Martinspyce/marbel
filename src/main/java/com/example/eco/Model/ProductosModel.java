package com.example.eco.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import jakarta.persistence.Entity;

@Entity
@Table(name = "Productos")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductosModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false, length = 300)
    private String descripcion;
    @Column(nullable = false, length = 9999)
    private int precio;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
}
