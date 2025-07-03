package com.ecomarket.gestion_usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.gestion_usuario.model.Usuario;
import com.ecomarket.gestion_usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario updateUserById(Usuario usuario) {
        return usuarioRepository.findById(usuario.getId())
                .map(usuarioExistente -> {
                    usuarioExistente.setRut(usuario.getRut());
                    usuarioExistente.setNombre(usuario.getNombre());
                    usuarioExistente.setApellido(usuario.getApellido());
                    usuarioExistente.setEmail(usuario.getEmail());
                    usuarioExistente.setPassword(usuario.getPassword());
                    usuarioExistente.setRol(usuario.getRol());
                    usuarioExistente.setActivo(usuario.isActivo());
                    return usuarioRepository.save(usuarioExistente);
                }).orElse(null);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Usuario desactivarById(Long id) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setActivo(false);
                    return usuarioRepository.save(usuario);
                }).orElse(null);
    }
}
