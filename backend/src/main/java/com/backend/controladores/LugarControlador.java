package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.ComercioServicio;
import com.backend.servicios.LugarServicio;
import com.backend.servicios.UsuarioServicio;
import com.backend.servicios.ZonaGeograficaServicio;
import com.google.gson.Gson;
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
@RequestMapping("/api/lugar/")
public class LugarControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    LugarServicio LugarServicio;
    @Autowired
    ZonaGeograficaServicio zonaServicio;
    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ComercioServicio comercioServicio;

    @GetMapping("listar")
    public ResponseEntity<List<Lugar>> getLista(){
        List<Lugar> lista = LugarServicio.obtenerTodos();
        log.info(" Obteniendo lista de Lugars");
        return new ResponseEntity<List<Lugar>>(lista, HttpStatus.OK);
    }

    @GetMapping("listarLosMios/{id}")
    public ResponseEntity<List<Lugar>> listarLosMios(@PathVariable Long id){
        Usuario usuario = this.usuarioServicio.obtener(id);
        Comercio comercio = this.comercioServicio.obtener(usuario);
        List<Lugar> lista = LugarServicio.obtenerTodosMisLugares(comercio);
        log.info(" Obteniendo lista de Lugars");
        return new ResponseEntity<List<Lugar>>(lista, HttpStatus.OK);
    }

    @GetMapping("detalle/{id}")
    public ResponseEntity<Lugar> getOne(@PathVariable Long id){
        log.info(" Obteniendo detalle de Lugar: "+id);

        if(!LugarServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe ese producto"), HttpStatus.NOT_FOUND);
        Lugar Lugar = LugarServicio.obtenerPorId(id).get();
        return new ResponseEntity<Lugar>(Lugar, HttpStatus.OK);
    }

    @PostMapping("nuevo")
    public ResponseEntity<?> create (@RequestParam("login") String login, @RequestParam("lugar") String lug){
        LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
        Lugar lugar = new Gson().fromJson(lug, Lugar.class);
        log.info(" Insertando nuevo Lugar: "+lugar.getNombre());
        if(StringUtils.isBlank(lugar.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(lugar.getDireccion()))
            return new ResponseEntity(new Mensaje("el tipo de Lugar es obligatorio"), HttpStatus.BAD_REQUEST);
        if(LugarServicio.existePorNombre(lugar.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);

        Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
        Comercio comercio = this.comercioServicio.obtener(usuario);
        lugar.setComercio(comercio);
        LugarServicio.guardar(lugar);
        return new ResponseEntity(new Mensaje("Lugar guardado"), HttpStatus.CREATED);
    }
/*
    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> update(@RequestBody Lugar Lugar, @PathVariable("id") Long id){
        log.info(" Actualizar Lugar: "+id);
        if(!LugarServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe el Lugar "+LugarServicio.obtenerPorId(id)), HttpStatus.NOT_FOUND);
        if(StringUtils.isBlank(lugar.getNombreLugar()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);



        Lugar baseLugar = LugarServicio.obtenerPorId(id).get();
        baselugar.setNombreProducto(lugar.getNombreLugar());
        baselugar.setTipoLugar(lugar.getTipoLugar());
        LugarServicio.guardar(baseLugar);
        return new ResponseEntity(new Mensaje("Lugar actualizado"), HttpStatus.CREATED);
    }
    */
    @DeleteMapping("borrar/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        log.info(" Borrando Lugar: "+id);
        if(!LugarServicio.existePorId(id))
            return new ResponseEntity(new Mensaje("no existe ese Lugar"), HttpStatus.NOT_FOUND);
        LugarServicio.borrar(id);
        return new ResponseEntity(new Mensaje("Lugar eliminado"), HttpStatus.OK);
    }

    @PostMapping("buscarLike")
    public ResponseEntity<?> buscarLike (@RequestParam("login") String login, @RequestParam("busqueda") String busqueda, @RequestParam("zona") String zona){
        log.error( " ENTRAMOS A LA BUSUQEDA LIKE ");
        try{

            List<Lugar> lp = this.LugarServicio.buscarLike (busqueda, zona);
            return new ResponseEntity<List<Lugar>>(lp, HttpStatus.OK);
        } catch (Exception e) {
            log.info(" something has failed");
            return new ResponseEntity<List<Artista>>((List<Artista>) null, HttpStatus.BAD_REQUEST);
        }


    }

}
