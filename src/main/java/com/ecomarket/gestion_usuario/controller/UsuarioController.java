package com.ecomarket.gestion_usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomarket.gestion_usuario.model.Usuario;
import com.ecomarket.gestion_usuario.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Usuario> actualizarUsuarioPorId(@PathVariable long id, @RequestBody Usuario usuarioActualizar) {
        Usuario usuarioActual = usuarioService.findById(id);
        if (usuarioActual == null) {
            return ResponseEntity.notFound().build();
        }

        if (usuarioActual.getRol() == 0 || usuarioActual.getId().equals(usuarioActualizar.getId())) {
            Usuario actualizado = usuarioService.updateUserById(usuarioActualizar);
            return (actualizado != null)
                    ? ResponseEntity.ok(actualizado)
                    : ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/listar/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable long id,
                                                 @RequestBody(required = false) Usuario usuarioBuscar) {
        Usuario usuarioActual = usuarioService.findById(id);
        if (usuarioActual == null) {
            return ResponseEntity.notFound().build();
        }

        if (usuarioActual.getRol() == 0 && usuarioBuscar != null && usuarioBuscar.getId() != 0) {
            Usuario usuario = usuarioService.findById(usuarioBuscar.getId());
            return (usuario != null)
                    ? ResponseEntity.ok(usuario)
                    : ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuarioActual);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsers();
        return (!usuarios.isEmpty())
                ? ResponseEntity.ok(usuarios)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable long id, @RequestBody Usuario usuarioDesactivar) {
        Usuario usuarioActual = usuarioService.findById(id);
        if (usuarioActual == null) {
            return ResponseEntity.notFound().build();
        }

        if (usuarioActual.getRol() == 0 || usuarioActual.getId().equals(usuarioDesactivar.getId())) {
            Usuario desactivado = usuarioService.desactivarById(usuarioDesactivar.getId());
            return (desactivado != null)
                    ? ResponseEntity.ok(desactivado)
                    : ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
