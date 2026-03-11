package com.example.eco.Usuarios.Service;

import com.example.eco.Model.UsuarioModel;
import com.example.eco.Usuarios.Repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsuarioById() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setId(1);
        usuario.setNombre("Benja");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<UsuarioModel> result = usuarioService.getUsuarioById(1);

        assertTrue(result.isPresent());
        assertEquals("Benja", result.get().getNombre());
    }

    @Test
    void testGetAllUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new UsuarioModel(), new UsuarioModel()));

        List<UsuarioModel> result = usuarioService.getAllUsuarios();

        assertEquals(2, result.size());
    }

    @Test
    void testGetUsuarioByEmail() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail("admin@example.com");

        when(usuarioRepository.findByEmail("admin@example.com")).thenReturn(Optional.of(usuario));

        Optional<UsuarioModel> result = usuarioService.getUsuarioByEmail("admin@example.com");

        assertTrue(result.isPresent());
        assertEquals("admin@example.com", result.get().getEmail());
    }

    @Test
    void testSaveUsuario() {
        UsuarioModel usuario = new UsuarioModel();
        usuario.setNombre("Nuevo");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioModel result = usuarioService.saveUsuario(usuario);

        assertEquals("Nuevo", result.getNombre());
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(usuarioRepository).deleteById(1);

        assertDoesNotThrow(() -> usuarioService.deleteUsuario(1));
        verify(usuarioRepository, times(1)).deleteById(1);
    }
}
