package com.backend.controladores;

import com.backend.entidades.Notificacion;
import com.backend.servicios.NotificacionServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/notificacion/")
public class NotificacionControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    NotificacionServicio notificacionServicio;

    @GetMapping("listar")
    public ResponseEntity<List<Notificacion>> getLista() {
        List<Notificacion> lista = notificacionServicio.obtenerTodos();
        log.info(" Obteniendo lista de Pagos");
        return new ResponseEntity<List<Notificacion>>(lista, HttpStatus.OK);
    }

}
