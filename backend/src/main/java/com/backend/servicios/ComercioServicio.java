package com.backend.servicios;

import com.backend.entidades.Comercio;
import com.backend.entidades.Rol;
import com.backend.entidades.TokenUsuario;
import com.backend.entidades.Usuario;
import com.backend.recursos.LoginDatos;
import com.backend.repositorios.ComercioRepositorio;
import com.backend.repositorios.RolRepositorio;
import com.backend.repositorios.UsuarioRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;


@Service
@Transactional
public class ComercioServicio {
    private static final Logger log = LoggerFactory.getLogger(ComercioServicio.class);
    @Autowired
    ComercioRepositorio comercioRepositorio;



    public boolean guardar(Usuario usuario) {
        Comercio c = new Comercio();
        c.setUsuario(usuario);
        this.comercioRepositorio.save(c);
        return true;

    }

    public Comercio obtener (Usuario usuario){
        return this.comercioRepositorio.findByUsuario(usuario).get();
    }


    public boolean existe (Usuario tipoUsuario) {
        boolean result =this.comercioRepositorio.existsByUsuario(tipoUsuario);
        log.info(" RESULTADO:" +result);
        return result;
    }
}
