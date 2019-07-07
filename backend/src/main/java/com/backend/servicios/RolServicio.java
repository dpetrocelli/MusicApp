package com.backend.servicios;

import com.backend.entidades.Rol;
import com.backend.enums.RolNombre;
import com.backend.repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolServicio {

    @Autowired
    RolRepositorio rolRepositorio;

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepositorio.findByRolNombre(rolNombre);
    }
}
