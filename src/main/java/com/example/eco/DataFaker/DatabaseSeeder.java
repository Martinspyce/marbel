package com.example.eco.DataFaker; // Un buen lugar para esta clase, o en el paquete raíz de tu aplicación

import com.example.eco.Model.OrdenModel;
import com.example.eco.Model.ProductosModel;
import com.example.eco.Model.ReportesModel;
import com.example.eco.Model.UsuarioModel;

// Importa tus interfaces de repositorio correctas
import com.example.eco.Orden.Repository.OrdenRepository;
import com.example.eco.Productos.Repository.ProductosRepository; // ¡Asegúrate de que el nombre del paquete sea correcto!
import com.example.eco.Reportes.Repository.ReportesRepository;
import com.example.eco.Usuarios.Repository.UsuarioRepository;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // Indica a Spring que esta clase es un componente gestionado
public class DatabaseSeeder implements CommandLineRunner {

    // Inyección de dependencias de tus repositorios
    private final UsuarioRepository usuarioRepository;
    private final ProductosRepository productosRepository;
    private final OrdenRepository ordenRepository;
    private final ReportesRepository reportesRepository;

    // Constructor para la inyección de dependencias (método preferido)
    public DatabaseSeeder(UsuarioRepository usuarioRepository,
                          ProductosRepository productosRepository,
                          OrdenRepository ordenRepository,
                          ReportesRepository reportesRepository) {
        this.usuarioRepository = usuarioRepository;
        this.productosRepository = productosRepository;
        this.ordenRepository = ordenRepository;
        this.reportesRepository = reportesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Este método se ejecutará automáticamente cuando la aplicación Spring Boot se inicie
        System.out.println("--- Iniciando la carga de datos falsos en la base de datos ---");

        // --- Carga de Usuarios ---
        // Solo carga usuarios si la tabla de usuarios está vacía
        if (usuarioRepository.count() == 0) {
            System.out.println("No se encontraron usuarios existentes. Generando usuarios falsos...");
            for (int i = 0; i < 10; i++) { // Generar 10 usuarios
                UsuarioModel usuario = Datafakerloader.generarUsuarioFalso();
                // Opcional: Para el password real, deberías encriptarlo aquí antes de guardar
                // Por ejemplo, con BCryptPasswordEncoder:
                // usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
                usuarioRepository.save(usuario); // Guarda el objeto UsuarioModel en la base de datos
            }
            System.out.println("Se crearon " + usuarioRepository.count() + " usuarios falsos.");
        } else {
            System.out.println("Ya existen " + usuarioRepository.count() + " usuarios. Omitiendo la creación de usuarios.");
        }

        // --- Carga de Productos ---
        if (productosRepository.count() == 0) {
            System.out.println("No se encontraron productos existentes. Generando productos falsos...");
            for (int i = 0; i < 15; i++) { // Generar 15 productos
                ProductosModel producto = Datafakerloader.generarProductoFalso();
                productosRepository.save(producto); // Guarda el objeto ProductosModel en la base de datos
            }
            System.out.println("Se crearon " + productosRepository.count() + " productos falsos.");
        } else {
            System.out.println("Ya existen " + productosRepository.count() + " productos. Omitiendo la creación de productos.");
        }

        // --- Carga de Órdenes ---
        if (ordenRepository.count() == 0) {
            System.out.println("No se encontraron órdenes existentes. Generando órdenes falsas...");
            for (int i = 0; i < 7; i++) { // Generar 7 órdenes
                OrdenModel orden = Datafakerloader.generarOrdenFalsa();
                ordenRepository.save(orden); // Guarda el objeto OrdenModel en la base de datos
            }
            System.out.println("Se crearon " + ordenRepository.count() + " órdenes falsas.");
        } else {
            System.out.println("Ya existen " + ordenRepository.count() + " órdenes. Omitiendo la creación de órdenes.");
        }

        // --- Carga de Reportes ---
        if (reportesRepository.count() == 0) {
            System.out.println("No se encontraron reportes existentes. Generando reportes falsos...");
            for (int i = 0; i < 5; i++) { // Generar 5 reportes
                ReportesModel reporte = Datafakerloader.generarReporteFalso();
                reportesRepository.save(reporte); // Guarda el objeto ReportesModel en la base de datos
            }
            System.out.println("Se crearon " + reportesRepository.count() + " reportes falsos.");
        } else {
            System.out.println("Ya existen " + reportesRepository.count() + " reportes. Omitiendo la creación de reportes.");
        }

        System.out.println("--- Carga de datos falsos finalizada ---");
    }
}