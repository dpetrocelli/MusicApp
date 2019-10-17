package com.backend.controladores;
import com.backend.entidades.Notificacion;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.NotificacionServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/notificacion/")
public class NotificacionControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    NotificacionServicio notificacionServicio;

    @PostMapping("listar")
    public ResponseEntity<?> getLista(@RequestBody LoginDatos ld) {
        log.info(" POST -> /listar/ \n User Logged: " + ld.getNombreUsuario());
        try {
            if (notificacionServicio.validarTokenUsuario(ld)) {
                List<Notificacion> notificaciones = notificacionServicio.obtenerTodos();
                log.info(" Obteniendo lista de Notificaciones");
                return new ResponseEntity<List<Notificacion>>(notificaciones, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            log.info("Estamos saliendo por except " + e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
