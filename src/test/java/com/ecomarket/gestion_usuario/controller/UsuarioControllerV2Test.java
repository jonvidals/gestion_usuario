package com.ecomarket.gestion_usuario.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ecomarket.gestion_usuario.model.Usuario;
import com.ecomarket.gestion_usuario.service.UsuarioService;

@WebMvcTest(UsuarioControllerV2.class)
public class UsuarioControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void testListarUsuariosConLinks() throws Exception {
        Usuario u1 = new Usuario(1L, "11111111-1", "Admin", "Apellido", "admin@duoc.cl", "pass", 0, true);
        Usuario u2 = new Usuario(2L, "22222222-2", "Jona", "Vidal", "jona@duoc.cl", "pass", 1, true);

        when(usuarioService.getAllUsers()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/api/hateoas/listar")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList[0].nombre").value("Admin"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].nombre").value("Jona"))
                .andExpect(jsonPath("$._embedded.usuarioList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testListarUsuariosConLinksVacio() throws Exception {
        when(usuarioService.getAllUsers()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/hateoas/listar")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarUsuarioPorId() throws Exception {
        Usuario u = new Usuario(1L, "11111111-1", "Admin", "Apellido", "admin@duoc.cl", "pass", 0, true);

        when(usuarioService.findById(1L)).thenReturn(u);

        mockMvc.perform(get("/api/hateoas/buscar/1")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Admin"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.usuarios.href").exists());
    }

    @Test
    void testBuscarUsuarioPorIdNoEncontrado() throws Exception {
        when(usuarioService.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/hateoas/buscar/99")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarUsuarios() throws Exception {
        Usuario u1 = new Usuario(1L, "11111111-1", "Admin", "Apellido", "admin@duoc.cl", "pass", 0, true);
        Usuario u2 = new Usuario(2L, "22222222-2", "Jona", "Vidal", "jona@duoc.cl", "pass", 1, true);

        when(usuarioService.getAllUsers()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/api/hateoas/buscar")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.usuarioList[0].nombre").value("Admin"))
                .andExpect(jsonPath("$._embedded.usuarioList[1].nombre").value("Jona"))
                .andExpect(jsonPath("$._embedded.usuarioList[0]._links.self.href").exists())
                .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void testBuscarUsuariosVacio() throws Exception {
        when(usuarioService.getAllUsers()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/hateoas/buscar")
                .accept(MediaTypes.HAL_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
void testListarUsuariosConLinksNull() throws Exception {
    when(usuarioService.getAllUsers()).thenReturn(null);

    mockMvc.perform(get("/api/hateoas/listar")
            .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isNotFound());
}

@Test
void testBuscarUsuariosNull() throws Exception {
    when(usuarioService.getAllUsers()).thenReturn(null);

    mockMvc.perform(get("/api/hateoas/buscar")
            .accept(MediaTypes.HAL_JSON))
            .andExpect(status().isNotFound());
}

}
