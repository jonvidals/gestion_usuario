package com.ecomarket.gestion_usuario.service;

import static org.mockito.Mockito.*;

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
    void setup(){
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void testUpdateUser(){
       Usuario usuarioExistente = new Usuario(1, "21111111", "hola", "chao", "caca@gmail",
        "omg", 1, true);

        Usuario usuarioUpdate = new Usuario(1, null, null, "chaoxd", "cacsssa@gmail",
        "omgxd", 2, false);

        when(usuarioRepository.findById(1)).thenReturn(usuarioExistente);
        
    
    }
}
