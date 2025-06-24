package com.ecomarket.gestion_usuario.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        if (usuarioActual.getRol() == 0) {
            Usuario usuarioActualizado = usuarioService.updateUserById(usuarioActualizar);
            if (usuarioActualizado != null) {
                return ResponseEntity.ok(usuarioActualizado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        }
        if (usuarioActual.getId() == usuarioActualizar.getId()) {
            Usuario usuarioActualizado = usuarioService.updateUserById(usuarioActualizar);
            if (usuarioActualizado != null) {
                return ResponseEntity.ok(usuarioActualizado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @PostMapping("/listar/{id}")
    public ResponseEntity<Usuario> buscarUsuario(
            @PathVariable long id,
            @RequestBody(required = false) Usuario usuarioBuscar) {

        Usuario usuarioActual = usuarioService.findById(id);
        if (usuarioActual == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (usuarioActual.getRol() == 0) { //admin
            if (usuarioBuscar != null && usuarioBuscar.getId() != 0) {
                Usuario usuario = usuarioService.findById(usuarioBuscar.getId());
                if (usuario != null) {
                    return ResponseEntity.ok(usuario);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } else {
                return ResponseEntity.ok(usuarioActual);
            }
        } else {
            return ResponseEntity.ok(usuarioActual);
        }
    }

    @GetMapping("/listar") 
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsers();
        if (usuarios != null && !usuarios.isEmpty()) {
            return ResponseEntity.ok(usuarios);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/desactivar/{id}") 
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable long id, @RequestBody Usuario usuarioDesactivar) {
        Usuario usuarioActual = usuarioService.findById(id);
        if (usuarioActual == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        if (usuarioActual.getRol() == 0) { // admin
            Usuario usuarioDesactivado = usuarioService.desactivarById(usuarioDesactivar.getId());
            if (usuarioDesactivado != null) {
                return ResponseEntity.ok(usuarioDesactivado);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            if (usuarioActual.getId() == usuarioDesactivar.getId()) {
                Usuario usuarioDesactivado = usuarioService.desactivarById(usuarioDesactivar.getId());
                if (usuarioDesactivado != null) {
                    return ResponseEntity.ok(usuarioDesactivado);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }
        }
    }
}
