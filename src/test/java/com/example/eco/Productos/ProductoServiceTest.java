package com.example.eco.Productos;

import com.example.eco.Model.ProductosModel;
import com.example.eco.Productos.Repository.ProductosRepository;
import com.example.eco.Productos.Services.ProductosServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductosRepository productosRepository;

    @InjectMocks
    private ProductosServices productosServices;

    private ProductosModel productoEjemplo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productoEjemplo = new ProductosModel();
        productoEjemplo.setId(1);
        productoEjemplo.setNombre("Manzana");
        productoEjemplo.setDescripcion("Fruta fresca");
        productoEjemplo.setPrecio(500);
        productoEjemplo.setFecha(new Date());
    }

    @Test
    void testGetAllProductos() {
        when(productosRepository.findAll()).thenReturn(Arrays.asList(productoEjemplo));
        List<ProductosModel> productos = productosServices.getAllProductos();

        assertEquals(1, productos.size());
        assertEquals("Manzana", productos.get(0).getNombre());
        verify(productosRepository, times(1)).findAll();
    }

    @Test
    void testGetProductoById() {
        when(productosRepository.findById(1)).thenReturn(Optional.of(productoEjemplo));

        Optional<ProductosModel> result = productosServices.getProductoById(1);

        assertTrue(result.isPresent());
        assertEquals("Manzana", result.get().getNombre());
        verify(productosRepository, times(1)).findById(1);
    }

    @Test
    void testSaveProducto() {
        when(productosRepository.save(any(ProductosModel.class))).thenReturn(productoEjemplo);

        ProductosModel saved = productosServices.saveProducto(productoEjemplo);

        assertNotNull(saved);
        assertEquals("Manzana", saved.getNombre());
        verify(productosRepository, times(1)).save(productoEjemplo);
    }

    @Test
    void testUpdateProductoSuccess() {
        ProductosModel updatedData = new ProductosModel();
        updatedData.setNombre("Pera");
        updatedData.setDescripcion("Otra fruta");
        updatedData.setPrecio(800);
        updatedData.setFecha(new Date());

        when(productosRepository.findById(1)).thenReturn(Optional.of(productoEjemplo));
        when(productosRepository.save(any(ProductosModel.class))).thenReturn(productoEjemplo);

        ProductosModel result = productosServices.updateProducto(1, updatedData);

        assertEquals("Pera", result.getNombre());
        assertEquals("Otra fruta", result.getDescripcion());
        verify(productosRepository).findById(1);
        verify(productosRepository).save(any(ProductosModel.class));
    }

    @Test
    void testDeleteProducto() {
        doNothing().when(productosRepository).deleteById(1);

        productosServices.deleteProducto(1);

        verify(productosRepository, times(1)).deleteById(1);
    }
}
