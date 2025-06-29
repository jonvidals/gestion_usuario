package com.ecomarket.gestion_usuario.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.ecomarket.gestion_usuario.model.Usuario;
import com.ecomarket.gestion_usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testActualizarUsuarioPorAdmin() throws Exception {
        Usuario admin = new Usuario(1L, "11111111-1", "Admin", "blabla", "admin@duoc.cl", "adminpass", 0, true);
        Usuario usuarioActualizar = new Usuario(2L, "22222222-2", "Jona", "Vidal", "jona@duoc.cl", "nuevaClave", 1, true);
        Usuario actualizado = new Usuario(2L, "22222222-2", "Jona", "Vidal", "jona@duoc.cl", "nuevaClave", 1, true);

        when(usuarioService.findById(1L)).thenReturn(admin);
        when(usuarioService.updateUserById(any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuario/actualizar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioActualizar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("nuevaClave"));
    }

    @Test
    void testActualizarUsuarioPorUsuario() throws Exception {
        Usuario user = new Usuario(1L, "11111111-1", "Jona", "Vidal", "jona@duoc.cl", "clave", 1, true);
        Usuario actualizado = new Usuario(1L, "11111111-1", "Jona", "Vidal", "jona@duoc.cl", "claveNueva", 1, true);

        when(usuarioService.findById(1L)).thenReturn(user);
        when(usuarioService.updateUserById(any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuario/actualizar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").value("claveNueva"));
    }

    @Test
    void testActualizarUsuarioNoEncontrado() throws Exception {
        when(usuarioService.findById(99L)).thenReturn(null);

        Usuario dummy = new Usuario(99L, "99999999-9", "usuario", "noencontrado", "nofound@duoc.cl", "x", 1, true);

        mockMvc.perform(put("/api/usuario/actualizar/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummy)))
                .andExpect(status().isNotFound());
    }
    @Test
    void testDesactivarUsuarioPorAdmin() throws Exception {
        Usuario admin = new Usuario(1L, "11111111-1", "Admin", "blabla", "admin@duoc.cl", "adminpass", 0, true);
        Usuario usuarioDesactivar = new Usuario(2L, "22222222-2", "Jona", "Vidal", "jona@duoc.cl", "clave", 1, true);
        Usuario desactivado = new Usuario(2L, "22222222-2", "Jona", "Vidal", "jona@duoc.cl", "clave", 1, false);

        when(usuarioService.findById(1L)).thenReturn(admin);
        when(usuarioService.desactivarById(2L)).thenReturn(desactivado);

        mockMvc.perform(put("/api/usuario/desactivar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDesactivar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    void testDesactivarUsuarioPorUsuario() throws Exception {
        Usuario user = new Usuario(1L, "11111111-1", "Jona", "Vidal", "jona@duoc.cl", "clave", 1, true);
        Usuario desactivado = new Usuario(1L, "11111111-1", "Jona", "Vidal", "jona@duoc.cl", "clave", 1, false);

        when(usuarioService.findById(1L)).thenReturn(user);
        when(usuarioService.desactivarById(1L)).thenReturn(desactivado);

        mockMvc.perform(put("/api/usuario/desactivar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activo").value(false));
    }

    @Test
    void testDesactivarUsuarioNoEncontrado() throws Exception {
        when(usuarioService.findById(99L)).thenReturn(null);

        Usuario dummy = new Usuario(99L, "99999999-9", "Fake", "User", "fake@duoc.cl", "x", 1, true);

        mockMvc.perform(put("/api/usuario/desactivar/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummy)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListarUsuarios() throws Exception {
        Usuario u1 = new Usuario(1L, "11111111-1", "Admin", "blabla", "admin@duoc.cl", "pass", 1, true);
        Usuario u2 = new Usuario(2L, "22222222-2", "Jona", "Vidal", "jona@duoc.cl", "pass", 1, true);

        when(usuarioService.getAllUsers()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/api/usuario/listar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Admin"))
                .andExpect(jsonPath("$[1].nombre").value("Jona"));
    }

    @Test
    void testListarUsuariosVacio() throws Exception {
        when(usuarioService.getAllUsers()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/usuario/listar"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testBuscarUsuarioPorAdmin() throws Exception {
        Usuario admin = new Usuario(1L, "11111111-1", "Admin", "blabla", "admin@duoc.cl", "adminpass", 0, true);
        Usuario buscado = new Usuario(2L, "22222222-2", "Jona", "Vidal", "jona@duoc.cl", "clave", 1, true);

        when(usuarioService.findById(1L)).thenReturn(admin);
        when(usuarioService.findById(2L)).thenReturn(buscado);

        mockMvc.perform(post("/api/usuario/listar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buscado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Jona"));
    }

    @Test
    void testAutoBuscarseAdmin() throws Exception {
        Usuario admin = new Usuario(1L, "11111111-1", "Admin", "blabla", "admin@duoc.cl", "adminpass", 0, true);

        when(usuarioService.findById(1L)).thenReturn(admin);

        mockMvc.perform(post("/api/usuario/listar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Admin"));
    }

    @Test
    void testBuscarUsuarioNoAdmin() throws Exception {
        Usuario user = new Usuario(1L, "11111111-1", "User", "Test", "user@duoc.cl", "pass", 1, true);

        when(usuarioService.findById(1L)).thenReturn(user);

        mockMvc.perform(post("/api/usuario/listar/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("User"));
    }

    @Test
    void testBuscarUsuarioNoEncontrado() throws Exception {
        when(usuarioService.findById(99L)).thenReturn(null);

        mockMvc.perform(post("/api/usuario/listar/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isNotFound());
    }
}
