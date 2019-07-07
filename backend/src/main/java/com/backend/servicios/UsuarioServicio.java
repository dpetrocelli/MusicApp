package com.backend.servicios;

import com.backend.entidades.Usuario;
import com.backend.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UsuarioServicio {

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    public Optional<Usuario> getByNombreUsuario(String nu){
        return usuarioRepositorio.findByNombreUsuario(nu);
    }

    public boolean existePorNombre(String nu){
        return usuarioRepositorio.existsByNombreUsuario(nu);
    }

    public  boolean existePorEmail(String email){
        return usuarioRepositorio.existsByEmail(email);
    }

    public void guardar(Usuario usuario){
        usuarioRepositorio.save(usuario);
    }
}
