package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.UsuarioServicio;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/puntuacion/")
@CrossOrigin(origins = "*")
public class PuntuacionControlador {
    @Autowired UsuarioServicio usuarioServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("nuevo")
    public ResponseEntity<?> crear  (@RequestParam("login") String login, @RequestParam("puntuacion") String puntuacion) {
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje


        try {

            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            PuntuacionArtista punt = new Gson().fromJson(puntuacion, PuntuacionArtista.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);


                return new ResponseEntity(new Mensaje(" HOLA "), HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("no pude crear post"), HttpStatus.OK);
        }
    }




}
