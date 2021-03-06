package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/banda/")
public class BandaControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired ArtistaServicio artistaServicio;

    @Autowired
    BandaServicio bandaServicio;
    @Autowired
    BiografiaBandaServicio biografiaBandaServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @PostMapping("buscarLike")
    public ResponseEntity<?> buscarLike (@RequestParam("login") String login, @RequestParam("opcion") String opcion, @RequestParam("busqueda") String busqueda, @RequestParam("zona") String zona, @RequestParam("instrumento") String instrumento, @RequestParam("genero") String genero){

        try{

            List<Banda> lp = this.bandaServicio.buscarLike (busqueda, zona, genero);
            return new ResponseEntity<List<Banda>>(lp, HttpStatus.OK);
        }catch (Exception e){
            log.info(" something has failed");
            return new ResponseEntity<List<Banda>>((List<Banda>) null, HttpStatus.BAD_REQUEST);
        }



    }

    @PostMapping("obtenerBandaPorNombre")
    public ResponseEntity<?> obtenerBandaPorNombre (@RequestParam("login") String login, @RequestParam("nombreBanda") String nombre) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            String nombreBanda = new Gson().fromJson(nombre, String.class);

            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                Banda banda= this.bandaServicio.obtenerBandaPorNombre(nombreBanda);

                return new ResponseEntity<Banda>(banda, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("obtenerDatosBanda")
    public ResponseEntity<?> obtenerDatosBanda (@RequestBody LoginDatos ld) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                Banda banda= this.bandaServicio.obtenerBandaPorNombre(ld.getNombreUsuario());

                return new ResponseEntity<Banda>(banda, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("buscarArtistas")
    public ResponseEntity<?> buscarArtistas (@RequestParam("login") String login, @RequestParam("banda") String bandax) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Banda banda = new Gson().fromJson(bandax, Banda.class);
            if (this.usuarioServicio.validarTokenUsuario(ld)) {

                //List<Artista> la = this.bandaServicio.obtenerTodosArtistasDeBanda(b);

                List<Artista> listaArtista = this.artistaServicio.obtenerTodos();
                ArrayList<Artista> artistasRespuesta = new ArrayList<Artista>();
                for (Artista art : listaArtista) {
                    Set<Banda> ba = art.getBanda();

                    for (Banda bandaInterna: ba) {
                        if (bandaInterna.getNombre().equals(banda.getNombre())) {
                            artistasRespuesta.add(art);
                        }
                    }

                }
                log.info(" LISTA DE ARTISTAS DE BANDA " + artistasRespuesta);

                return new ResponseEntity<ArrayList<Artista>>(artistasRespuesta, HttpStatus.OK);


            } else {
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("obtenerArtistasDeBanda")
    public ResponseEntity<?> obtenerArtistasDeBanda (@RequestParam("login") String login, @RequestParam("artista") String artista) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Artista a = new Gson().fromJson(artista, Artista.class);
            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                if (a == null) {
                    Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                    a = this.artistaServicio.obtenerPorUsuario(u);
                }
                Set<Banda> b = a.getBanda();
                Iterator<Banda> iterator = b.iterator();
                Banda banda = iterator.next();
                //List<Artista> la = this.bandaServicio.obtenerTodosArtistasDeBanda(b);

                List<Artista> listaArtista = this.artistaServicio.obtenerTodos();
                ArrayList<Artista> artistasRespuesta = new ArrayList<Artista>();
                for (Artista art : listaArtista) {
                    Set<Banda> ba = art.getBanda();

                    for (Banda bandaInterna: ba) {
                        if (bandaInterna.getNombre().equals(banda.getNombre())) {
                            artistasRespuesta.add(art);
                        }
                    }

                }
                log.info(" LISTA DE ARTISTAS DE BANDA " + artistasRespuesta);

                return new ResponseEntity<ArrayList<Artista>>(artistasRespuesta, HttpStatus.OK);


            } else {
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("borrarBandaTotal")
    public ResponseEntity<?> borrarBanda (@RequestParam("login") String login, @RequestParam("banda") String banda) {
        // [STEP 0] - Validar usuario y contraseña
        log.info(" ESTAMOS ACA");
        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Banda b = new Gson().fromJson(banda, Banda.class);
            if (this.usuarioServicio.validarTokenUsuario(ld)) {

                this.bandaServicio.borrar(b);
                return new ResponseEntity<String>(" OK", HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("obtenerTodos")
    public ResponseEntity<?> obtenerTodos (@RequestBody LoginDatos ld) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                List<Banda> listaBanda = this.bandaServicio.obtenerTodos();
                ArrayList<String> listaRespuesta = new ArrayList<String>();

                for (Banda banda : listaBanda){
                    listaRespuesta.add( banda.getNombre());
                }
                return new ResponseEntity<ArrayList<String>>(listaRespuesta, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("detalle/{id}")
    public ResponseEntity<Banda> getOne(@PathVariable Long id){
        log.info(" Obteniendo detalle de Lugar: "+id);

        if(!bandaServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe ese producto"), HttpStatus.NOT_FOUND);
        Banda banda = this.bandaServicio.obtenerPorId(id);
        return new ResponseEntity<Banda>(banda, HttpStatus.OK);
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody Banda banda, @PathVariable("id") Long id){
        log.info(" Actualizar Lugar: "+id);
        if(!this.bandaServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe el Lugar "+this.bandaServicio.obtenerPorId(id)), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(banda.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);



        Banda baseBanda = this.bandaServicio.obtenerPorId(id);

        baseBanda.setGeneroMusical(banda.getGeneroMusical());
        baseBanda.setNombre(banda.getNombre());
        baseBanda.setZona(banda.getZona());


        this.bandaServicio.guardar(baseBanda);


        return new ResponseEntity(new Mensaje("banda actualizado"), HttpStatus.CREATED);
    }


    @PostMapping("soyDuenioBanda")
    public ResponseEntity<?> soyDuenioBanda (@RequestParam("login") String login, @RequestParam("artista") String artista) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Artista a = new Gson().fromJson(artista, Artista.class);
            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                if (a == null) {
                    Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                    a = this.artistaServicio.obtenerPorUsuario(u);
                }

                ArrayList<Banda> rest = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(a);
                // por ahora vamos a tener una sola banda duenio
                Banda b = rest.get(0);

                List<Artista> lista = this.artistaServicio.obtenerTodos();
                ArrayList<Artista> artistasRespuesta = new ArrayList<Artista>();
                for (Artista art: lista) {
                    Set<Banda> ba = art.getBanda();
                    for (Banda bandita: ba) {
                        if (bandita.getNombre().equals(b.getNombre())){
                            artistasRespuesta.add(art);
                        }
                    }


                }
                /*
                boolean result = false;
                if (rest.size()>0){
                    result = true;
                }
                */


                return new ResponseEntity<ArrayList<Artista>>(artistasRespuesta, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("soyDuenioBandaLogin")
    public ResponseEntity<?> soyDuenioBandaLogin (@RequestParam("login") String login) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
            Artista a = this.artistaServicio.obtenerPorUsuario(u);

                ArrayList<Banda> rest = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(a);
                // por ahora vamos a tener una sola banda duenio
                if (rest.size()>0) {
                    return new ResponseEntity<Boolean>(true, HttpStatus.OK);
                }else{
                    return new ResponseEntity<Boolean>(false, HttpStatus.OK);
                }





            

        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("datosBanda")
    public ResponseEntity<?> datosBanda (@RequestBody LoginDatos ld) {
        // [STEP 0] - Validar usuario y contraseña
        log.info(" DATOS BANDA");
        try {
            //LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
            Artista a = this.artistaServicio.obtenerPorUsuario(u);

            ArrayList<Banda> rest = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(a);
            // por ahora vamos a tener una sola banda duenio
            if (rest.size()>0) {
                return new ResponseEntity<Banda>(rest.get(0), HttpStatus.OK);
            }else{
                return new ResponseEntity<Boolean>(false, HttpStatus.OK);
            }







        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("datosBandaUna")
    public ResponseEntity<?> datosBandaUna (@RequestBody LoginDatos ld) {
        // [STEP 0] - Validar usuario y contraseña
        log.info(" DATOS BANDA");
        try {
            //LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
            Artista a = this.artistaServicio.obtenerPorUsuario(u);

            Banda rest = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(a).get(0);
            // por ahora vamos a tener una sola banda duenio

                return new ResponseEntity<Banda>(rest, HttpStatus.OK);








        } catch (Exception e) {
            return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("eliminarArtistaDeBanda")
    public ResponseEntity<?> eliminarArtistaDeBanda (@RequestParam("login") String login, @RequestParam("banda") String banda, @RequestParam("artista") String artista) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Banda b = new Gson().fromJson(banda, Banda.class);
            Artista a = new Gson().fromJson(artista, Artista.class);

            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                if (a == null) {
                    Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                    a = this.artistaServicio.obtenerPorUsuario(u);
                }

               a.removeBanda();
                this.artistaServicio.guardar(a);


                return new ResponseEntity<Boolean>(true, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("nueva")
    public ResponseEntity<?> nueva (@RequestParam("login") String login, @RequestParam("banda") String banda) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Banda b = new Gson().fromJson(banda, Banda.class);
            Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
            Artista a = this.artistaServicio.obtenerPorUsuario(u);

            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                b.setArtistaLider(a);
                this.bandaServicio.guardar (b);
                BiografiaBanda bb = new BiografiaBanda();
                bb.setBanda(b);
                this.biografiaBandaServicio.guardar(bb);
                return new ResponseEntity<Boolean>( true, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<Boolean>( true, HttpStatus.OK);
        }

    }

}
