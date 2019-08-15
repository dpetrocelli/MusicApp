package com.backend.servicios;

import com.backend.entidades.Rol;
import com.backend.entidades.TokenUsuario;
import com.backend.entidades.Usuario;
import com.backend.recursos.LoginDatos;
import com.backend.repositorios.ComercioRepositorio;
import com.backend.repositorios.RolRepositorio;
import com.backend.repositorios.TokenUsuarioRepositorio;
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
public class UsuarioServicio {
    private static final Logger log = LoggerFactory.getLogger(UsuarioServicio.class);
    @Autowired
    UsuarioRepositorio usuarioRepositorio;
    @Autowired
    RolRepositorio rolRepositorio;
    @Autowired
    TokenUsuarioServicio tokenUsuarioServicio;

    @Autowired
    ComercioServicio comercioServicio;

    public boolean validarCredenciales(Usuario usuarioFrontend){

        try{
            Usuario usuarioBackend = usuarioRepositorio.findByUsername(usuarioFrontend.getUsername());
            if (usuarioBackend.validatePassword(usuarioFrontend.getpwd())){
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            return false;
        }




    }

    public boolean guardar(Usuario usuarioFrontEnd, String nombreRol) {


        if (this.usuarioRepositorio.existsByUsername(usuarioFrontEnd.getUsername())) return false; else {
            // Obtengo el rol que me dice
            log.info(" Recibo Usuario (FE) + Rol que eligio ");
            Rol rol = rolRepositorio.findById((long) Float.parseFloat(nombreRol)).get();

            usuarioFrontEnd.addRol(rol);
            // Luego encodeo la pass
            usuarioFrontEnd.encodePassword();
            log.info (" Agrego el rol al usuario y encodeo la password");
            // luego lo guardo
            this.usuarioRepositorio.save(usuarioFrontEnd);

            //Set<Rol> roles = usuarioFrontEnd.getRoles();
            //Rol rolComercio = this.rolRepositorio.findByNombre("comercio");
            if (rol.getNombre().equals("comercio")){
                log.info(" ES un rol comercio, entonces lo guardo como tal");
                this.comercioServicio.guardar(usuarioFrontEnd);
            }
            else{
                log.info(" ES un rol artista, entonces lo guardo como tal");
                //this.artistaServicio.guardar(usuarioFrontEnd);
            }






            return true;
        }

    }

    public TokenUsuario generarTokenUsuario(Usuario usuario) {
        log.info( "Generar Token Usuario  -> "+usuario.getUsername());
        TokenUsuario tu = tokenUsuarioServicio.crearTokenUsuario(usuario);
        this.tokenUsuarioServicio.guardarTokenUsuario(tu);

        return tu;
    }

    public boolean validarTokenUsuario (LoginDatos loginDatos){
        Long idUsuarioFE = loginDatos.getIdUsuario();
        String tokenFE = loginDatos.getTokenUsuario();
        System.out.println(" validarTokenUsuario (LoginDatos loginDatos) ");
        TokenUsuario tuBE = tokenUsuarioServicio.obtenerTokenUsuario(idUsuarioFE);
        System.out.println(" OBTUVIMOS TOKEN USUARIO CON ID USUARIO FE ");
        if ((tuBE != null) && (tokenFE.equals(tuBE.getToken()))){

            return tokenUsuarioServicio.validarToken(tuBE);
            // Si devuelve true -> Válido y renovado
            // Si devuelve false -> Se expiró
        }else{
            System.out.println(" DATOS INCONSISTENTES; DESLOGUEAR");
            return false;
        }

    }

    public Usuario obtener (Long idUsuario){
       return this.usuarioRepositorio.findById(idUsuario).get();

    }

    public Usuario obtenerPorNombre (String nombre){
        return this.usuarioRepositorio.findByUsername(nombre);
    }
    public boolean existe(Usuario usuarioFrontEnd) {
        log.info("HOLA");
        boolean result = this.usuarioRepositorio.existsByUsername((usuarioFrontEnd.getUsername()));
        log.info ("chau");
        return result;
    }
}
