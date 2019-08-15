package com.backend.servicios;

import com.backend.entidades.Rol;
import com.backend.entidades.TokenUsuario;
import com.backend.entidades.Usuario;
import com.backend.recursos.LoginDatos;
import com.backend.repositorios.RolRepositorio;
import com.backend.repositorios.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Set;


@Service
@Transactional
public class RolServicio {
    private static final Logger log = LoggerFactory.getLogger(RolServicio.class);
    @Autowired
    RolRepositorio rolRepositorio;

    public Rol obtener (String nombre){
        return this.rolRepositorio.findByNombre(nombre);
    }
}
