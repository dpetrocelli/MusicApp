package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.GeneroMusical;
import com.backend.servicios.GeneroMusicalServicio;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/generoMusical")
@CrossOrigin(origins = "*")
public class GeneroMusicalControlador {

    @Autowired
    GeneroMusicalServicio generoMusicalServicio;

    @GetMapping("/listar")
    public ResponseEntity<List<GeneroMusical>> getLista(){
        List<GeneroMusical> lista = generoMusicalServicio.obtenerTodos();
        return new ResponseEntity<List<GeneroMusical>>(lista, HttpStatus.OK);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<GeneroMusical> getOne(@PathVariable Long id){
        if(!generoMusicalServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe esa genero musical"), HttpStatus.NOT_FOUND);
        GeneroMusical generoMusical = generoMusicalServicio.obtenerPorId(id);
        return new ResponseEntity<GeneroMusical>(generoMusical, HttpStatus.OK);
    }

    @PostMapping("nuevo")
    public ResponseEntity<?> create(@RequestBody GeneroMusical generoMusical){
        if(StringUtils.isBlank(generoMusical.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(generoMusicalServicio.existePorNombre(generoMusical.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        generoMusicalServicio.guardar(generoMusical);
        return new ResponseEntity(new Mensaje("genero musical guardada"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody GeneroMusical generoMusical, @PathVariable("id") Long id){
        if(!generoMusicalServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe la generoMusical "+ generoMusicalServicio.obtenerPorId(id)), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(generoMusical.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(generoMusicalServicio.existePorNombre(generoMusical.getNombre()) &&
                (generoMusical.getId() != id))
            return new ResponseEntity(new Mensaje("ese nombre de genero musical ya existe"), HttpStatus.BAD_REQUEST);
        GeneroMusical baseGeneroMusical = generoMusicalServicio.obtenerPorId(id);
        baseGeneroMusical.setNombre(generoMusical.getNombre());
        generoMusicalServicio.guardar(baseGeneroMusical);
        return new ResponseEntity(new Mensaje("GeneroMusical actualizada"), HttpStatus.CREATED);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(!generoMusicalServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe esa generoMusical"), HttpStatus.NOT_FOUND);
        generoMusicalServicio.borrar(id);
        return new ResponseEntity(new Mensaje("generoMusical eliminada"), HttpStatus.OK);
    }
}
