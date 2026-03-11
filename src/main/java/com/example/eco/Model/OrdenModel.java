package com.example.eco.Model;

import jakarta.persistence.*;
import  lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Ordenes")
public class OrdenModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String nombre;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(nullable = false, length = 350)
    private String descripcion;

}