package com.backend;

import com.backend.entidades.MusicApp;
import com.backend.entidades.MarketPlace;
import com.backend.entidades.Rol;
import com.backend.entidades.Usuario;
import com.backend.repositorios.GananciaRepositorio;
import com.backend.repositorios.MarketPlaceRepositorio;
import com.backend.repositorios.RolRepositorio;
import com.backend.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.boot.CommandLineRunner;
/**
 * Database initializer that populates the database with some
 * initial bank accounts using a JPA repository.
 *
 * This component is started only when app.db-init property is set to true
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Inicializador implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(Inicializador.class);
    @Autowired
    private RolRepositorio rolRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private MarketPlaceRepositorio marketPlaceRepositorio;

    @Autowired
    private GananciaRepositorio gananciaRepositorio;


    @Override
    public void run(String...args) {

        if (rolRepositorio.count()==0){
            log.info("HAY QUE METER DATOS");
            Rol rol = new Rol("admin", "adminfull", "allsites");
            rolRepositorio.save(rol);

            rol = new Rol("comercio", "listadeaccesocomercio", "todas");
            rolRepositorio.save(rol);
            rol = new Rol("artista", "listadeaccesocomercio", "todas");
            rolRepositorio.save(rol);

        }else{
            log.info("YA HAY DATOS");
        }

        if (usuarioRepositorio.count()==0){
            log.info("HAY QUE AGREGAR USUARIO ADMIN ");
            Rol r = rolRepositorio.findByNombre("admin");
            Usuario usuarioAdmin = new Usuario();
            usuarioAdmin.setUsername("admin");
            usuarioAdmin.setPassword("admin");
            usuarioAdmin.setEmail("dmpetrocelli@gmail.com");
            usuarioAdmin.addRol(r);
            usuarioRepositorio.save(usuarioAdmin);
            log.info("Usuario admin almacenado con exito");

        }else{
            log.info("ADMIN USER LOADED");
        }

        if (marketPlaceRepositorio.count()==0){
            // INSERTO LA CONF (Temporal) - MERCADOLIBRE
            MarketPlace mp = new MarketPlace("5801276810386206", "3KLt51OEOUQHfzG9p2QlKnrkRa1VZ98Y");
            marketPlaceRepositorio.save(mp);
        }

        if (gananciaRepositorio.count()==0){
            MusicApp musicApp = new MusicApp(2.69);
            gananciaRepositorio.save(musicApp);
        }

    }
}

