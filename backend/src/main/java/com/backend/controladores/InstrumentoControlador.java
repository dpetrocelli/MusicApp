package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.Instrumento;
import com.backend.servicios.InstrumentoServicio;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/instrumento/")
public class InstrumentoControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    InstrumentoServicio instrumentoServicio;

    @GetMapping("listar")
    public ResponseEntity<List<Instrumento>> getLista(){
        List<Instrumento> lista = instrumentoServicio.obtenerTodos();
        log.info(" Obteniendo lista de Instrumentos");
        return new ResponseEntity<List<Instrumento>>(lista, HttpStatus.OK);
    }

    @GetMapping("detalle/{id}")
    public ResponseEntity<Instrumento> getOne(@PathVariable Long id){
        log.info(" Obteniendo detalle de instrumento: "+id);

        if(!instrumentoServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe ese producto"), HttpStatus.NOT_FOUND);
        Instrumento instrumento = instrumentoServicio.obtenerPorId(id).get();
        return new ResponseEntity<Instrumento>(instrumento, HttpStatus.OK);
    }

    @PostMapping("nuevo")
    public ResponseEntity<?> create(@RequestBody Instrumento instrumento){
        log.info(" Insertando nuevo instrumento: "+instrumento.getNombreInstrumento());
        if(StringUtils.isBlank(instrumento.getNombreInstrumento()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(instrumento.getTipoInstrumento()))
            return new ResponseEntity(new Mensaje("el tipo de instrumento es obligatorio"), HttpStatus.BAD_REQUEST);
        if(instrumentoServicio.existePorNombre(instrumento.getNombreInstrumento()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        instrumentoServicio.guardar(instrumento);
        return new ResponseEntity(new Mensaje("instrumento guardado"), HttpStatus.CREATED);
    }

    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody Instrumento instrumento, @PathVariable("id") Long id){
        log.info(" Actualizar instrumento: "+id);
        if(!instrumentoServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe el instrumento "+instrumentoServicio.obtenerPorId(id)), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(instrumento.getNombreInstrumento()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        /*
        if(instrumentoServicio.existePorNombre(instrumento.getNombreInstrumento())) {
            log.info(" EL instrumento existe");
            if (instrumento.getId() != (instrumentoServicio.obtenerPorNombre(instrumento.getNombreInstrumento())).get().getId()){
                log.info(" nefli");
                return new ResponseEntity(new Mensaje("ese nombre de instrumento ya existe"), HttpStatus.BAD_REQUEST);
            }
        }
        */


        Instrumento baseInstrumento = instrumentoServicio.obtenerPorId(id).get();
        baseInstrumento.setNombreProducto(instrumento.getNombreInstrumento());
        baseInstrumento.setTipoInstrumento(instrumento.getTipoInstrumento());
        instrumentoServicio.guardar(baseInstrumento);
        return new ResponseEntity(new Mensaje("Instrumento actualizado"), HttpStatus.CREATED);
    }

    @DeleteMapping("borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info(" Borrando instrumento: "+id);
        if(!instrumentoServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe ese instrumento"), HttpStatus.NOT_FOUND);
        instrumentoServicio.borrar(id);
        return new ResponseEntity(new Mensaje("instrumento eliminado"), HttpStatus.OK);
    }
}
