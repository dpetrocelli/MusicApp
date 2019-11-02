package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.ArtistaServicio;
import com.backend.servicios.BandaServicio;
import com.backend.servicios.PuntuacionServicio;
import com.backend.servicios.UsuarioServicio;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/puntuacion/")
public class PuntuacionControlador {
    @Autowired UsuarioServicio usuarioServicio;
    @Autowired ArtistaServicio artistaServicio;
    @Autowired
    BandaServicio bandaServicio;
    @Autowired PuntuacionServicio puntuacionServicio;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("nuevo")
    public ResponseEntity<?> crear  (@RequestParam("login") String login, @RequestParam("usuarioPuntuado") String art, @RequestParam("comentario") String comentario, @RequestParam("puntuacion") String puntuacion) {

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                boolean yaPuntue = this.puntuacionServicio.validarSiYaPuntuee(ld, art);
                //yaPuntue = false;
                if (!yaPuntue){
                    boolean guardado = this.puntuacionServicio.guardarPuntuacionArtista(ld, art, comentario,puntuacion);
                    if (guardado){
                        return new ResponseEntity(new Mensaje(" HOLA "), HttpStatus.OK);
                    }else{
                        return new ResponseEntity(new Mensaje(" REVENTO "), HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity(new Mensaje(" Ya puntuaste a ese usuario "), HttpStatus.BAD_REQUEST);
                }



            }else return new ResponseEntity(new Mensaje(" ERROR no autorizado"), HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("no pude crear post"), HttpStatus.OK);
        }
    }

    @PostMapping("obtenerPuntuacion")
    public ResponseEntity<?> validar (@RequestBody LoginDatos loginDatos){
        List<PuntuacionArtista> lista = this.puntuacionServicio.obtenerPuntuacionArtistaByLoginDatos(loginDatos);
        /*ArrayList<PuntuacionArtista> pArt = new ArrayList<PuntuacionArtista>();
        for (PuntuacionArtista pa: lista) {
            // ARtista puntuador
            Artista artPuntuador = new Artista();
            Usuario u = new Usuario();
            u.setUsername(pa.getArtistaPuntuador().getUsuario().getUsername());
            artPuntuador.setUsuario(u);
            PuntuacionArtista puntuacionArtista = new PuntuacionArtista();
            puntuacionArtista.setArtistaPuntuador(artPuntuador);
            // Artista puntuado
            Artista artPuntuado = new Artista();
            u = new Usuario();
            u.setUsername(pa.getArtistaPuntuado().getUsuario().getUsername());
            artPuntuado.setUsuario(u);

            puntuacionArtista.setArtistaPuntuador(artPuntuador);
            puntuacionArtista.setArtistaPuntuado(artPuntuado);
            puntuacionArtista.setFechaPuntuacion(pa.getFechaPuntuacion());
            puntuacionArtista.setPuntuacion(pa.getPuntuacion());
            puntuacionArtista.setComentario(pa.getComentario());

            // Agrego a la lista respuesta
            pArt.add(puntuacionArtista);

        }
        log.info(" Obteniendo lista de puntuaciones");
        return new ResponseEntity<ArrayList<PuntuacionArtista>>(pArt, HttpStatus.OK);

         */
        return new ResponseEntity<List<PuntuacionArtista>>(lista, HttpStatus.OK);
    }


    @PostMapping("obtenerPuntuacionMiBanda")
    public ResponseEntity<?> obtenerPuntuacionMiBanda (@RequestBody LoginDatos loginDatos){
        List<PuntuacionBanda> lista = this.puntuacionServicio.obtenerPuntuacionBandaByLoginDatos(loginDatos);

        return new ResponseEntity<List<PuntuacionBanda>>(lista, HttpStatus.OK);
    }
    @PostMapping("verificarSiPuntuee")
    public ResponseEntity<?>  verificarSiPuntuee (@RequestParam("login") String login, @RequestParam("nombre") String nombre){
        LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);

        boolean yaPuntue = this.puntuacionServicio.validarSiYaPuntuee(ld, nombre);

        return new ResponseEntity<Boolean>(yaPuntue, HttpStatus.OK);
    }

    @PostMapping("RedSocialObtenerPuntuacion")
    public ResponseEntity<?> RedSocialObtenerPuntuacion (@RequestParam("login") String login, @RequestParam("nombre") String nombre){

        Usuario usuario = this.usuarioServicio.obtenerPorNombre(nombre);
        Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
        List<PuntuacionArtista> lista = this.puntuacionServicio.obtenerPuntuacionArtistaByArtista(artista);
        log.info(" Obteniendo lista de puntuaciones");
        return new ResponseEntity<List<PuntuacionArtista>>(lista, HttpStatus.OK);
    }

    @PostMapping("ObtenerPuntuacionBanda")
    public ResponseEntity<?> ObtenerPuntuacionBanda (@RequestParam("login") String login, @RequestParam("nombre") String nombre){
        Banda banda = this.bandaServicio.obtenerBandaPorNombre(nombre);

        List<PuntuacionBanda> lista = this.puntuacionServicio.obtenerPuntuacionBandaByBanda(banda);
        log.info(" Obteniendo lista de puntuaciones");
        return new ResponseEntity<List<PuntuacionBanda>>(lista, HttpStatus.OK);
    }






}
