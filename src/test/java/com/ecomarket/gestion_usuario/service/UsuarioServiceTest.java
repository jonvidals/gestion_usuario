package com.ecomarket.gestion_usuario.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.ecomarket.gestion_usuario.model.Usuario;
import com.ecomarket.gestion_usuario.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void testUpdateUserById_Success() {
        Usuario usuario = new Usuario(1L, "12345678-9", "jona", "vidal", "jon@duoc.com", "password", 1, true);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.updateUserById(usuario);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("jona");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    public void testUpdateUserById_NotFound() {
        Usuario usuario = new Usuario(1L, "12345678-9", "jona", "vidal", "jon@duoc.com", "password", 1, true);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.updateUserById(usuario);

        assertThat(resultado).isNull();
    }

    @Test
    public void testDesactivarById_Success() {
        Usuario usuario = new Usuario(1L, "12345678-9", "jona", "vidal", "jon@duoc.com", "password", 1, true);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.desactivarById(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.isActivo()).isFalse();
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    public void testDesactivarById_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.desactivarById(1L);

        assertThat(resultado).isNull();
    }

    @Test
    public void testGetAllUsers_WithUsers() {
        Usuario usuario1 = new Usuario(1L, "12345678-9", "jona", "vidal", "jon@duoc.com", "password", 1, true);
        Usuario usuario2 = new Usuario(2L, "98765432-1", "juan", "pepe", "juan@duoc.com", "password", 2, true);
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario1, usuario2));

        List<Usuario> resultados = usuarioService.getAllUsers();

        assertThat(resultados).hasSize(2);
        verify(usuarioRepository).findAll();
    }

    @Test
    public void testGetAllUsers_EmptyList() {
        when(usuarioRepository.findAll()).thenReturn(List.of());

        List<Usuario> resultados = usuarioService.getAllUsers();

        assertThat(resultados).isEmpty();
        verify(usuarioRepository).findAll();
    }

    @Test
    public void testFindById_Success() {
        Usuario usuario = new Usuario(1L, "12345678-9", "jona", "vidal", "jon@duoc.com", "password", 1, true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.findById(1L);

        assertThat(resultado).isNotNull();
        verify(usuarioRepository).findById(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        Usuario resultado = usuarioService.findById(1L);

        assertThat(resultado).isNull();
    }
}
