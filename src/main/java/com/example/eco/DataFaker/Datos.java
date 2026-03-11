package com.example.eco.DataFaker;

import com.example.eco.Model.*;
import com.example.eco.Orden.Repository.OrdenRepository;
import com.example.eco.Productos.Repository.ProductosRepository;
import com.example.eco.Reportes.Repository.ReportesRepository;
import com.example.eco.Usuarios.Repository.UsuarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Profile("dev")
@Component
public class Datos implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private OrdenRepository ordenRepository;

    @Autowired
    private ReportesRepository reportesRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        System.out.println("Iniciando carga de datos de prueba...");

        // Generar usuarios
        System.out.println("Generando usuarios...");
        for (int i = 0; i < 20; i++) {
            UsuarioModel usuario = Datafakerloader.generarUsuarioFalso();
            usuarioRepository.save(usuario);
        }

        // Generar productos
        System.out.println("Generando productos...");
        for (int i = 0; i < 50; i++) {
            ProductosModel producto = Datafakerloader.generarProductoFalso();
            productosRepository.save(producto);
        }

        // Obtener listas para relaciones
        List<UsuarioModel> usuarios = usuarioRepository.findAll();
        List<ProductosModel> productos = productosRepository.findAll();

        // Generar órdenes
        System.out.println("Generando órdenes...");
        for (int i = 0; i < 30; i++) {
            OrdenModel orden = Datafakerloader.generarOrdenFalsa();

            // Asignar usuario aleatorio si tienes relación
            if (!usuarios.isEmpty()) {
                // orden.setUsuario(usuarios.get(random.nextInt(usuarios.size())));
            }

            ordenRepository.save(orden);
        }

        // Generar reportes
        System.out.println("Generando reportes...");
        for (int i = 0; i < 10; i++) {
            ReportesModel reporte = Datafakerloader.generarReporteFalso();
            reportesRepository.save(reporte);
        }

        // Mostrar estadísticas finales
        System.out.println("=== DATOS GENERADOS ===");
        System.out.println("Usuarios: " + usuarioRepository.count());
        System.out.println("Productos: " + productosRepository.count());
        System.out.println("Órdenes: " + ordenRepository.count());
        System.out.println("Reportes: " + reportesRepository.count());
        System.out.println("Carga de datos completada exitosamente!");
    }
}

