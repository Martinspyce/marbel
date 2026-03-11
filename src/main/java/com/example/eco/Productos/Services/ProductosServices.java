package com.example.eco.Productos.Services;

import com.example.eco.Model.ProductosModel;
import com.example.eco.Productos.Repository.ProductosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductosServices {

    private final ProductosRepository productosRepository;

    public List<ProductosModel> getAllProductos() {
        return productosRepository.findAll();
    }

    public Optional<ProductosModel> getProductoById(Integer id) {
        return productosRepository.findById(id);
    }

    public ProductosModel saveProducto(ProductosModel producto) {
        return productosRepository.save(producto);
    }

    public ProductosModel updateProducto(Integer id, ProductosModel productoDetails) {
        return productosRepository.findById(id)
                .map(producto -> {
                    producto.setNombre(productoDetails.getNombre());
                    producto.setDescripcion(productoDetails.getDescripcion());
                    producto.setPrecio(productoDetails.getPrecio());
                    producto.setFecha(productoDetails.getFecha());
                    return productosRepository.save(producto);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }

    public void deleteProducto(Integer id) {
        productosRepository.deleteById(id);
    }

    public List<ProductosModel> getProductosByNombre(String nombre) {
        return productosRepository.findByNombre(nombre);
    }

    public List<ProductosModel> getProductosByPrecio(Integer precioMin, Integer precioMax) {
        return productosRepository.findByPrecioBetween(precioMin, precioMax);
    }

}
