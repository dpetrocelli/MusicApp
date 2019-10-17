package com.backend.controladores;

import com.backend.entidades.Pago;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.PagoServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/pago/")
public class PagoControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    PagoServicio pagoServicio;

    @PostMapping("listar")
    public ResponseEntity<?> getLista(@RequestBody LoginDatos ld) {
        log.info(" POST -> /listar/ \n User Logged: " + ld.getNombreUsuario());
        try {

            if(pagoServicio.validarTokenUsuario(ld)) {
                List<Pago> lista = pagoServicio.obtenerTodos();
                log.info(" Obteniendo lista de Pagos");
                return new ResponseEntity<List<Pago>>(lista, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            log.info("Estamos saliendo por except " + e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
