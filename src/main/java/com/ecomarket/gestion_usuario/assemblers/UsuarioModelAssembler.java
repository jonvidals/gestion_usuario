package com.ecomarket.gestion_usuario.assemblers;

import com.ecomarket.gestion_usuario.controller.UsuarioController;
import com.ecomarket.gestion_usuario.model.Usuario;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;

@Component
public class UsuarioModelAssembler extends RepresentationModelAssemblerSupport<Usuario, EntityModel<Usuario>> {

    public UsuarioModelAssembler() {
        super(UsuarioController.class, (Class<EntityModel<Usuario>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).buscarUsuario(usuario.getId(), usuario)).withSelfRel(),
                linkTo(methodOn(UsuarioController.class).listarUsuarios()).withRel("usuarios")
        );
        
    }

}