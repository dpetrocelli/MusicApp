package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.Artista;
import com.backend.entidades.Biografia;
import com.backend.entidades.Post;
import com.backend.entidades.Usuario;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elemento/")
@CrossOrigin(origins = "*")

public class ElementoControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired InstrumentoServicio instrumentoServicio;
    @Autowired PromocionServicio promocionServicio;
    @Autowired UsuarioServicio usuarioServicio;
    @Autowired ArtistaServicio artistaServicio;
    @Autowired BiografiaServicio biografiaServicio;

    @PostMapping("obtenerPosts")
    public ResponseEntity<?> obtenerPosts (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje

        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);
            String idpost = json.get("idpost").getAsString();

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                /*
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    Biografia bio = this.biografiaServicio.obtener(artista);
                    bio.setBiografiaBasica(biografia);
                    bio.setArtista(artista);
                    this.biografiaServicio.guardar (bio);
                }*/
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


            }catch (Exception e){
                return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
            }
    }


}
