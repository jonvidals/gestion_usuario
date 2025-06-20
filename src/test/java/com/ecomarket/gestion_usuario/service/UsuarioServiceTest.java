package com.ecomarket.gestion_usuario.service;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void testUpdateUserById() {
        Usuario usuario = new Usuario(1L, "12345678-9", "jona", "vidal", "jonvidal@duoc.com", "password", 1, true);
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.updateUserById(usuario);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("jona");
        assertThat(resultado.getEmail()).isEqualTo("jonvidal@duoc.com");

    }
    @Test
    public void testDesactivarById() {
        Usuario usuario = new Usuario(1L, "12345678-9", "jona", "vidal", "jonvidal@duoc.com", "password", 1, true);
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.desactivarById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.isActivo()).isFalse();
    }
    @Test
    public void testGetAllUsers() {
        Usuario usuario1 = new Usuario(1L, "12345678-9", "jona", "vidal", "jon.vidal@duoc.com", "password", 1, true);
        Usuario usuario2 = new Usuario(2L, "98765432-1", "juan", "pepe", "juan.pepe@duoc.com", "password", 2, true);
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario1, usuario2));

        List<Usuario> resultados = usuarioService.getAllUsers();

        assertThat(resultados).isNotNull();
        assertThat(resultados.size()).isEqualTo(2);
        assertThat(resultados.get(0).getNombre()).isEqualTo("jona");
        assertThat(resultados.get(1).getNombre()).isEqualTo("juan");
    }
    @Test
    public void testGetUserById() {
        Usuario usuario = new Usuario(1L, "12345678-9", "jon", "vidal", "jon.vidal@duoc.com", "password", 1, true);
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuario));

        Usuario resultado = usuarioService.getUserById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("jon");
    }
}