package com.backend.controladores;

import com.backend.entidades.Pago;
import com.backend.servicios.PagoServicio;
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
@RequestMapping("/api/pago/")
public class PagoControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    PagoServicio pagoServicio;

    @GetMapping("listar")
    public ResponseEntity<List<Pago>> getLista() {
        List<Pago> lista = pagoServicio.obtenerTodos();
        log.info(" Obteniendo lista de Pagos");
        return new ResponseEntity<List<Pago>>(lista, HttpStatus.OK);
    }

}
