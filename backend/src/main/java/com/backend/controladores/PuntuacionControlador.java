package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.ComercioServicio;
import com.backend.servicios.UsuarioServicio;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/api/puntuacion/")
@CrossOrigin(origins = "*")
public class PuntuacionControlador {
    @Autowired UsuarioServicio usuarioServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("nuevo")
    public ResponseEntity<?> crear  (@RequestBody String payload) {
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje

        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try {
            log.info("siendo: " + payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);
            Puntuacion puntuacion = new Gson().fromJson(json.get("puntuacion"), Puntuacion.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);


                return new ResponseEntity(new Mensaje(" HOLA "), HttpStatus.OK);


        } catch (Exception e) {
            return new ResponseEntity(new Mensaje("no pude crear post"), HttpStatus.OK);
        }
    }




}
