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
            return new ResponseEntity(new Mensaje("no pude obtener bien los datos"), HttpStatus.OK);
        }
    }


    @PostMapping("eliminar")
    public ResponseEntity<?> Eliminar (@RequestParam("login") String login, @RequestParam("id") String id){
        try{
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            if (this.notificacionBandaUsuarioServicio.validarTokenUsuario(ld)){

                this.notificacionBandaUsuarioServicio.eliminar(id);
                return new ResponseEntity(new Mensaje("OK"), HttpStatus.OK);
            }

            else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }

    @PostMapping("actualizar")
    public ResponseEntity<?> Actualizar (@RequestParam("login") String login, @RequestParam("msg") String msg, @RequestParam("id") String id){
        try{
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            if (this.notificacionBandaUsuarioServicio.validarTokenUsuario(ld)){

                this.notificacionBandaUsuarioServicio.actualizar(id, msg);
                return new ResponseEntity(new Mensaje("OK"), HttpStatus.OK);
            }

            else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }

    @PostMapping("descartar")
    public ResponseEntity<?> Descartar (@RequestParam("login") String login, @RequestParam("nombreOrigen") String nombreOrigen,@RequestParam("nombreDestino") String nombreDestino,  @RequestParam("id") String id){
        try{
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            if (this.notificacionBandaUsuarioServicio.validarTokenUsuario(ld)){

                this.notificacionBandaUsuarioServicio.descartar(id, nombreOrigen, nombreDestino);
                return new ResponseEntity(new Mensaje("OK"), HttpStatus.OK);
            }

            else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }
}
