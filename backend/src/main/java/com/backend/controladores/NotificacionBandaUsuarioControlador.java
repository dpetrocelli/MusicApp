package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.*;
import com.backend.singleton.ConfiguradorSingleton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notificacionesBandaUsuario/")

public class NotificacionBandaUsuarioControlador {
    @Autowired
    NotificacionBandaUsuarioServicio notificacionBandaUsuarioServicio;

    @PostMapping("obtener")
    public ResponseEntity<?> obtener (@RequestBody LoginDatos ld) {

        try{


            if (this.notificacionBandaUsuarioServicio.validarTokenUsuario(ld)){
                Artista artista = this.notificacionBandaUsuarioServicio.obtenerArtista(ld);

                ArrayList<NotificacionBandaUsuario> lista = this.notificacionBandaUsuarioServicio.obtenerNotificaciones(artista.getUsuario().getUsername());

                return new ResponseEntity<ArrayList<NotificacionBandaUsuario>> (lista, HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener biografia"), HttpStatus.OK);
        }
    }




}
