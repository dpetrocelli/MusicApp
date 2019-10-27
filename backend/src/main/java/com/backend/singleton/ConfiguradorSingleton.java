package com.backend.singleton;

import com.backend.entidades.*;
import com.backend.repositorios.*;
import com.backend.servicios.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    PostServicio postServicio;
    @Autowired
    PostRepositorio postRepositorio;
    @Autowired
    ComercioRepositorio comercioRepositorio;
    @Autowired
    InstrumentoServicio instrumentoServicio;

    @Autowired
    ZonaGeograficaServicio zonaGeograficaServicio;


    @Autowired BiografiaServicio biografiaServicio;

    @Autowired GeneroMusicalServicio generoMusicalServicio;
    public ArrayList<String> permisosDelComercio;
    public ArrayList<String> permisosDelArtista;
    public String baseURLSistema;
    public List<Post> listPost;
    String msg;
    @Override
    public void run(String...args) {
        this.msg = " HOLA ";
        // SETEAR LA BASE URL PARA EL FUNCIONAMIENTO DEL SISTEMA
        this.baseURLSistema = "https://lacajadepandora.ddns.net:8081";

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

            log.info("HAY QUE AGREGAR USUARIO ADMIN ");
            Rol r = rolRepositorio.findByNombre("admin");
            Usuario usuarioAdmin = new Usuario();
            usuarioAdmin.setUsername("admin");
            usuarioAdmin.setPassword("admin");
            usuarioAdmin.setEmail("admin@gmiusicapp.com");
            usuarioAdmin.addRol(r);
            usuarioRepositorio.save(usuarioAdmin);
            log.info("Usuario admin almacenado con exito");

            // USUARIO COMERCIO
            log.info("HAY QUE AGREGAR USUARIO COMERCIO ");
            r = rolRepositorio.findByNombre("comercio");
            Usuario usuarioComercio = new Usuario();
            usuarioComercio.setUsername("comercio");
            usuarioComercio.setPassword("comercio");
            usuarioComercio.setEmail("comercio@gmail.com");
            usuarioComercio.addRol(r);


            Comercio comercio = new Comercio();
            comercio.setUsuario(usuarioComercio);
            comercio.setRazonsocial("comecio");
            comercio.setDireccion("comercio direccion");

            comercioRepositorio.save(comercio);
            usuarioRepositorio.save(usuarioComercio);


            log.info("Usuario comercio almacenado con exito");

            // USUARIO ARTISTA
            // PRIMERO le clavo el genero musical básico y las zonas
            if ((this.zonaGeograficaServicio.obtenerTodos()).size()==0){
                this.zonaGeograficaServicio.guardar(new Zona ("sur" ));
                this.zonaGeograficaServicio.guardar(new Zona ("norte" ));
                this.zonaGeograficaServicio.guardar(new Zona ("este" ));
                this.zonaGeograficaServicio.guardar(new Zona ("oeste" ));
            }

            if  ((this.generoMusicalServicio.obtenerTodos()).size()==0){
                this.generoMusicalServicio.guardar(new GeneroMusical("rock"));
                this.generoMusicalServicio.guardar(new GeneroMusical("pop"));
                this.generoMusicalServicio.guardar(new GeneroMusical("metal"));
            }



            log.error("guarde el genero musical");
            log.info("HAY QUE AGREGAR USUARIO ARTISTa ");
            r = rolRepositorio.findByNombre("artista");
            Usuario usuarioArtista = new Usuario();
            usuarioArtista.setUsername("artista");
            usuarioArtista.setPassword("artista");
            usuarioArtista.setEmail("artista@gmail.com");
            usuarioArtista.addRol(r);


            Artista artista = new Artista();
            artista.setUsuario(usuarioArtista);
            artista.setDocumentoIdentidad(1234);
            artista.setGenero("masculino");
            artista.setNickname("nickname");
            artista.setNombre("nombre");
            artista.setApellido("apellido");
            artista.setFechaNacimiento(new Date());

            GeneroMusical generoMusical = this.generoMusicalServicio.obtenerPorId(Long.valueOf(1)).get();
            artista.setGeneroMusical(generoMusical);

            Zona zona = this.zonaGeograficaServicio.obtenerPorId(Long.valueOf(1)).get();

            artista.setZona(zona);
            Set<Instrumento> setInstrumento = new HashSet<Instrumento>();
            Set<Banda> setBanda = new HashSet<>();
            artista.setInstrumento(setInstrumento);
            artista.setBanda(setBanda);
            Biografia biografia = new Biografia();
            biografia.setArtista(artista);
            this.biografiaServicio.guardar(biografia);
            artistaRepositorio.save(artista);
            usuarioRepositorio.save(usuarioArtista);



            log.info("Usuario artista almacenado con exito");


        }else{
            log.info("ADMIN USER LOADED");
        }

        if (marketPlaceRepositorio.count()==0){
            // INSERTO LA CONF (Temporal) - MERCADOLIBRE
            MarketPlace mp = new MarketPlace("8023447927546095", "pDoIXPLnMSp1aU8epxwhDCOq7ploVm9u",2.69, (long) 1000000);
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


        /*
        this.listPost = new ArrayList<Post>();
        while (true){
            try {
                Thread.sleep(10000);
                synchronized (this.listPost){
                    this.listPost =  this.postRepositorio.findAll();
                    //findTop100ByOrderByIdDesc();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        */





    }

    public void printMessage() {

        System.out.println(this.msg);
    }


}

