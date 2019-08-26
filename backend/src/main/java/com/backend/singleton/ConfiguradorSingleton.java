package com.backend.singleton;

import com.backend.entidades.*;
import com.backend.repositorios.ArtistaRepositorio;
import com.backend.repositorios.MarketPlaceRepositorio;
import com.backend.repositorios.RolRepositorio;
import com.backend.repositorios.UsuarioRepositorio;
import com.backend.servicios.ArtistaServicio;
import com.backend.servicios.InstrumentoServicio;
import com.backend.servicios.RolServicio;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class ConfiguradorSingleton implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ConfiguradorSingleton.class);
    @Autowired
    private RolRepositorio rolRepositorio;
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private ArtistaRepositorio artistaRepositorio;

    @Autowired ArtistaServicio artistaServicio;
    @Autowired
    private MarketPlaceRepositorio marketPlaceRepositorio;


    @Autowired
    RolServicio rolServicio;

    @Autowired
    InstrumentoServicio instrumentoServicio;

    public ArrayList<String> permisosDelComercio;
    public ArrayList<String> permisosDelArtista;
    public String baseURLSistema;

    @Override
    public void run(String...args) {
        // SETEAR LA BASE URL PARA EL FUNCIONAMIENTO DEL SISTEMA
        this.baseURLSistema = "http://localhost:8081";

        if (rolRepositorio.count()==0){
            log.info("HAY QUE METER DATOS");
            Rol rol = new Rol("admin", "adminfull", "allsites");
            rolRepositorio.save(rol);

            rol = new Rol("comercio", "listadeaccesocomercio", "activarcomercio,promociones,login");
            rolRepositorio.save(rol);
            rol = new Rol("artista", "listadeaccesocomercio", "comprar,login,calificarbanda,calificarusuario,bandas,perfil");
            rolRepositorio.save(rol);

        }else{
            log.info("YA HAY DATOS");
        }

        if (usuarioRepositorio.count()==0){
              /*
            log.info("HAY QUE AGREGAR USUARIO ARTISTa ");
            Rol r = rolRepositorio.findByNombre("artista");
            Usuario usuarioArtista = new Usuario();
            usuarioArtista.setUsername("artista");
            usuarioArtista.setPassword("artista");
            usuarioArtista.setEmail("dmpetrocelli@gmail.com");
            usuarioArtista.addRol(r);


            Artista artista = new Artista();
            artista.setUsuario(usuarioArtista);
            artista.setDocumentoIdentidad(1234);
            artista.setGenero("masculino");
            artista.setNickname("nickname");
            artista.setNombre("nombre");
            artista.setApellido("apellido");
            artista.setGeneroMusical("raro");

            Set<Instrumento> setInstrumento = new HashSet<Instrumento>();
            Set<Banda> setBanda = new HashSet<>();
            artista.setInstrumento(setInstrumento);
            artista.setBanda(setBanda);
            usuarioRepositorio.save(usuarioArtista);
            artistaRepositorio.save(artista);
            log.info("Usuario artista almacenado con exito");

            */
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
            MarketPlace mp = new MarketPlace("5801276810386206", "3KLt51OEOUQHfzG9p2QlKnrkRa1VZ98Y",2.69, (long) 1000000);
            marketPlaceRepositorio.save(mp);
        }

        // CARGO LOS PERMISOS DEL ROL DEL COMERCIO
        Rol r = this.rolServicio.obtener("comercio");
        this.permisosDelComercio = new ArrayList<String>(Arrays.asList(r.getOpcioneshabilitadas().split(",")));

        // CARGO LOS PERMISOS DEL ROL DEL ARTISTA
        r = this.rolServicio.obtener("artista");
        this.permisosDelArtista = new ArrayList<String>(Arrays.asList(r.getOpcioneshabilitadas().split(",")));

        if ((this.instrumentoServicio.obtenerTodos()).size()==0){
            this.instrumentoServicio.guardar(new Instrumento("guitarra", "cuerdas"));
            this.instrumentoServicio.guardar(new Instrumento("guitapoca", "cuerdas"));
            this.instrumentoServicio.guardar(new Instrumento("bajo", "cuerdas"));
            this.instrumentoServicio.guardar(new Instrumento("siku", "viento"));
            this.instrumentoServicio.guardar(new Instrumento("flauta", "viento"));
        }


    }
}

