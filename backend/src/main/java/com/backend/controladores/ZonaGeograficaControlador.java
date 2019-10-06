package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.Zona;
import com.backend.servicios.ZonaGeograficaServicio;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    @RequestMapping("/api/zonaGeografica")
    @CrossOrigin(origins = "*")
    public class ZonaGeograficaControlador {

        @Autowired
        ZonaGeograficaServicio ZonaServicio;

        @GetMapping("/listar")
        public ResponseEntity<List<Zona>> getLista(){
            List<Zona> lista = ZonaServicio.obtenerTodos();
            return new ResponseEntity<List<Zona>>(lista, HttpStatus.OK);
        }

        @GetMapping("/detalle/{id}")
        public ResponseEntity<Zona> getOne(@PathVariable Long id){
            if(!ZonaServicio.existePorId(id))
                return new ResponseEntity(new Mensaje("no existe esa zona"), HttpStatus.NOT_FOUND);
            Zona zona = ZonaServicio.obtenerPorId(id).get();
            return new ResponseEntity<Zona>(zona, HttpStatus.OK);
        }

        @PostMapping("nuevo")
        public ResponseEntity<?> create(@RequestBody Zona zona){
            if(StringUtils.isBlank(zona.getNombreZona()))
                return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
            if(ZonaServicio.existePorNombre(zona.getNombreZona()))
                return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
            ZonaServicio.guardar(zona);
            return new ResponseEntity(new Mensaje("zona guardada"), HttpStatus.CREATED);
        }

        @PutMapping("/actualizar/{id}")
        public ResponseEntity<?> update(@RequestBody Zona zona, @PathVariable("id") Long id){
            if(!ZonaServicio.existePorId(id))
                return new ResponseEntity(new Mensaje("no existe la zona "+ZonaServicio.obtenerPorId(id)), HttpStatus.NOT_FOUND);
            if(StringUtils.isBlank(zona.getNombreZona()))
                return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
            if(ZonaServicio.existePorNombre(zona.getNombreZona()) &&
                    (zona.getId() != id))
                return new ResponseEntity(new Mensaje("ese nombre de zona ya existe"), HttpStatus.BAD_REQUEST);
            Zona baseZona = ZonaServicio.obtenerPorId(id).get();
            baseZona.setNombreZona(zona.getNombreZona());
            ZonaServicio.guardar(baseZona);
            return new ResponseEntity(new Mensaje("Zona actualizada"), HttpStatus.CREATED);
        }

        @DeleteMapping("/borrar/{id}")
        public ResponseEntity<?> delete(@PathVariable Long id){
            if(!ZonaServicio.existePorId(id))
                return new ResponseEntity(new Mensaje("no existe esa zona"), HttpStatus.NOT_FOUND);
            ZonaServicio.borrar(id);
            return new ResponseEntity(new Mensaje("zona eliminada"), HttpStatus.OK);
        }
    }


