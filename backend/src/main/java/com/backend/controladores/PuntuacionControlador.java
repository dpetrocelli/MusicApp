package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.ArtistaServicio;
import com.backend.servicios.PuntuacionServicio;
import com.backend.servicios.UsuarioServicio;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/puntuacion/")
public class PuntuacionControlador {
    @Autowired UsuarioServicio usuarioServicio;
    @Autowired ArtistaServicio artistaServicio;
    @Autowired PuntuacionServicio puntuacionServicio;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("nuevo")
    public ResponseEntity<?> crear  (@RequestParam("login") String login, @RequestParam("usuarioPuntuado") String art, @RequestParam("comentario") String comentario, @RequestParam("puntuacion") String puntuacion) {

        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){

                boolean guardado = this.puntuacionServicio.guardarPuntuacionArtista(ld, art, comentario,puntuacion);
                if (guardado){
                    return new ResponseEntity(new Mensaje(" HOLA "), HttpStatus.OK);
                }else{
                    return new ResponseEntity(new Mensaje(" REVENTO "), HttpStatus.BAD_REQUEST);
                }


            }else return new ResponseEntity(new Mensaje(" ERROR no autorizado"), HttpStatus.UNAUTHORIZED);

        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("no pude crear post"), HttpStatus.OK);
        }
    }

    @PostMapping("obtenerPuntuacion")
    public ResponseEntity<?> validar (@RequestBody LoginDatos loginDatos){
        List<PuntuacionArtista> lista = this.puntuacionServicio.obtenerPuntuacionArtista(loginDatos);
        log.info(" Obteniendo lista de puntuaciones");
        return new ResponseEntity<List<PuntuacionArtista>>(lista, HttpStatus.OK);
    }


}
