package com.ecomarket.gestion_usuario.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecomarket.gestion_usuario.model.Usuario;
import com.ecomarket.gestion_usuario.repository.UsuarioRepository;

public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdateUserById() {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setRut("12345678-9");
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setEmail("jon@ctla.cl");
        usuario.setPassword("password123");
        usuario.setRol(0);
        usuario.setActivo(true);

        when(usuarioRepository.findById(1)).thenReturn(usuario);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        
    }
}
