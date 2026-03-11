package com.example.eco.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
@Table(name = "Reportes")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false, length = 300)
    private String descripcion;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
}
