package com.backend.servicios;

import com.backend.entidades.Rol;
import com.backend.entidades.Usuario;
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
public class UsuarioServicio {
    private static final Logger log = LoggerFactory.getLogger(UsuarioServicio.class);
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    RolRepositorio rolRepositorio;


    public HashMap<Usuario, String> ingresar(Usuario usuarioFrontend){
        HashMap<Usuario, String> hmap = new HashMap<Usuario, String>();
        Usuario usuarioBackend = usuarioRepositorio.findByUsername(usuarioFrontend.getUsername());

        //Usuario usuarioBackend = usuarioRepositorio.findByUsername("sarasa");
        if (usuarioBackend == null) {
            hmap.put(null, "usuario inexistente");
        } else {
            if (usuarioBackend.validatePassword(usuarioFrontend.getpwd())){
                hmap.put (usuarioBackend, "ok");
            } else {
                hmap.put (usuarioBackend, "password no valida");
            }
        }
        return hmap;

    }

    public boolean guardar(Usuario usuarioFrontEnd, String nombreRol) {


        if (this.usuarioRepositorio.existsByUsername(usuarioFrontEnd.getUsername())) return false; else {
            // Obtengo el rol que me dice

            Rol rol = rolRepositorio.findById((long) Float.parseFloat(nombreRol)).get();

            usuarioFrontEnd.addRol(rol);
            // Luego encodeo la pass
            usuarioFrontEnd.encodePassword();
            // luego lo guardo
            this.usuarioRepositorio.save(usuarioFrontEnd);




            return true;
        }

    }
}
