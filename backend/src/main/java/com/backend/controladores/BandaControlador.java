package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.ArtistaServicio;
import com.backend.servicios.BandaServicio;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/banda/")
public class BandaControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    BandaServicio bandaServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

}
