package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.Artista;
import com.backend.entidades.Biografia;
import com.backend.entidades.Instrumento;
import com.backend.entidades.Usuario;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/")
@CrossOrigin(origins = "*")
public class PostControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired InstrumentoServicio instrumentoServicio;
    @Autowired PromocionServicio promocionServicio;
    @Autowired UsuarioServicio usuarioServicio;
    @Autowired ArtistaServicio artistaServicio;
    @Autowired BiografiaServicio biografiaServicio;

    @PostMapping("obtenerBiografia")
    public ResponseEntity<?> create(@RequestBody LoginDatos ld){
        try{

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());

                Artista artista = this.artistaServicio.obtener(u.getId());

                    Biografia b =  this.biografiaServicio.obtener(artista);
                    return new ResponseEntity(b, HttpStatus.OK);



            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener biografia"), HttpStatus.OK);
        }
    }


}
