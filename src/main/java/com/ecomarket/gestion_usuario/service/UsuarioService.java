package com.ecomarket.gestion_usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.gestion_usuario.model.Usuario;
import com.ecomarket.gestion_usuario.repository.UsuarioRepository;
@Service
public class UsuarioService {
     @Autowired
    private UsuarioRepository usuarioRepository;
    public Usuario updateUserById(Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getId());
            usuarioExistente.setRut(usuario.getRut());
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setApellido(usuario.getApellido());
            usuarioExistente.setEmail(usuario.getEmail());
            usuarioExistente.setPassword(usuario.getPassword());
            usuarioExistente.setRol(usuario.getRol());
            usuarioExistente.setActivo(usuario.isActivo());     
            usuarioRepository.save(usuarioExistente);
            return usuarioExistente;
 
    }

    public Usuario deleteUserById(int id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario != null) {
            usuarioRepository.delete(usuario);
            return usuario;
        }
        return null;
    }
    public Usuario getUserById(int id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario != null) {
            return usuario;
        }
        return null;
    }
   public List<Usuario> getAllUsers() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios != null && !usuarios.isEmpty()) {
            return usuarios;
        }
        return null;
    }

  /*  public Usuario desactivarById(int id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario != null) {
            usuario.setActivo(false);
            usuarioRepository.save(usuario);
            return usuario;
        }
        return null;
   }*/


    

}
