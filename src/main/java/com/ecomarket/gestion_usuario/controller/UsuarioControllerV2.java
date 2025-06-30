package com.ecomarket.gestion_usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.ecomarket.gestion_usuario.model.Usuario;
import com.ecomarket.gestion_usuario.service.UsuarioService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/hateoas")
public class UsuarioControllerV2 {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/listar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> listarUsuariosConLinks() {
        List<Usuario> usuarios = usuarioService.getAllUsers();
        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<EntityModel<Usuario>> usuarioModels = usuarios.stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioControllerV2.class).buscarUsuario(usuario.getId(), null)).withSelfRel()))
                .collect(java.util.stream.Collectors.toList());

        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(
                usuarioModels,
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuariosConLinks()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/buscar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Usuario>> buscarUsuarioPorId(@PathVariable Long id) {

        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        EntityModel<Usuario> usuarioModel = EntityModel.of(usuario,
                linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarioPorId(id)).withSelfRel(),
                linkTo(methodOn(UsuarioControllerV2.class).listarUsuariosConLinks()).withRel("usuarios"));

        return ResponseEntity.ok(usuarioModel);
    }

    @GetMapping(value = "/buscar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Usuario>>> buscarUsuarios() {

        List<Usuario> usuarios = usuarioService.getAllUsers();
        if (usuarios == null || usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<EntityModel<Usuario>> usuarioModels = usuarios.stream()
                .map(usuario -> EntityModel.of(usuario,
                        linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarioPorId(usuario.getId())).withSelfRel()))
                .collect(java.util.stream.Collectors.toList());

        CollectionModel<EntityModel<Usuario>> collectionModel = CollectionModel.of(
                usuarioModels,
                linkTo(methodOn(UsuarioControllerV2.class).buscarUsuarios()).withSelfRel()
        );

        return ResponseEntity.ok(collectionModel);
    }
        
    public ResponseEntity<EntityModel<Usuario>> buscarUsuario(Long id, Object unused) {
        return null;
    }
}

