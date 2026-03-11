package com.example.eco.DataFaker;
import net.datafaker.Faker;
import com.example.eco.Model.*;
import java.util.Date;
import java.util.Calendar;

public class Datafakerloader {
    private static final Faker faker = new Faker();



    private static Date generarFechaPasada(int diasAtras) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -faker.number().numberBetween(1, diasAtras));
        return cal.getTime();
    }

    private static Date generarFechaNacimiento() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -faker.number().numberBetween(18, 80));
        cal.add(Calendar.DAY_OF_YEAR, -faker.number().numberBetween(0, 365));
        return cal.getTime();
    }

    // Genera un UsuarioModel falso
    public static UsuarioModel generarUsuarioFalso() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombre(faker.name().firstName());
        usuario.setApellido(faker.name().lastName());
        usuario.setEmail(faker.internet().emailAddress());
        usuario.setTelefono(faker.phoneNumber().cellPhone());
        usuario.setFechaNacimiento(generarFechaNacimiento());
        usuario.setPassword(faker.internet().password(8, 16, true, true));
        usuario.setRol(faker.options().option("ADMIN", "USER", "GUEST"));
        return usuario;
    }

    // Genera un ProductsModel falso
    public static ProductosModel generarProductoFalso() {
        ProductosModel producto = new ProductosModel();
        producto.setNombre(faker.commerce().productName());
        producto.setDescripcion(faker.lorem().sentence());
        producto.setPrecio(faker.number().numberBetween(100, 9999));
        producto.setFecha(generarFechaPasada(30));
        return producto;
    }

    // Genera un OrdenModel falso
    public static OrdenModel generarOrdenFalsa() {
        OrdenModel orden = new OrdenModel();
        orden.setNombre(faker.commerce().promotionCode());
        orden.setFecha(generarFechaPasada(15));
        orden.setDescripcion(faker.lorem().paragraph());
        return orden;
    }

    // Genera un ReportesModel falso
    public static ReportesModel generarReporteFalso() {
        ReportesModel reporte = new ReportesModel();
        reporte.setNombre(faker.options().option("Reporte Ventas", "Reporte Inventario"));
        reporte.setDescripcion(faker.lorem().paragraph());
        reporte.setFecha(generarFechaPasada(10));
        return reporte;
    }
}