package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;

import com.backend.servicios.ComercioServicio;
import com.backend.servicios.PromocionServicio;

import com.backend.servicios.UsuarioServicio;
import com.backend.wrappers.PromocionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/promocion/")
@CrossOrigin(origins = "*")

public class PromocionControlador {


    @Autowired
    PromocionServicio promocionServicio;

    @Autowired
    UsuarioServicio usuarioServicio;

    @Autowired
    ComercioServicio comercioServicio;
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @PostMapping("listar")
    public ResponseEntity<?> registrar(@RequestBody LoginDatos ld){
        log.info(" POST -> /listar/ \n User Logged: "+ld.getNombreUsuario());
        try{
            boolean result = promocionServicio.validarTokenUsuario(ld);
            if (!result){
                return new ResponseEntity<Boolean>(false, HttpStatus.OK);
            }else{
                log.info(" Vamos a buscar las promociones");
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                Comercio c = this.comercioServicio.obtener(u);

                if (this.promocionServicio.existe(c.getId())){
                    log.info (" EXISTE AL MENOS UNA PROMOCION ");
                    ArrayList<Promocion> promociones = this.promocionServicio.obtenerPromociones(c.getId());
                    log.info (" hay una x cantidad de promociones"+promociones.size());

                    return new ResponseEntity<ArrayList<Promocion>>(promociones, HttpStatus.OK);
                }else{
                    log.info (" No hay promociones" );
                    return new ResponseEntity<String>("no hay promociones", HttpStatus.OK);
                }


            }

        }catch (Exception e){
            log.info("Estamos saliendo por except "+e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("nuevo")
    public ResponseEntity<?> nuevo(@RequestBody PromocionRequest request){
        log.info(" POST -> /nuevo/ ");
        LoginDatos tu = request.getLoginDatos();
        //Comercio comercio; Needed to know commerce id 
        Promocion promocion = request.getPromocion();

        log.info(" TU -> /nuevo/ -> User: "+tu.getNombreUsuario());
        log.info(" Articulo -> /nuevo/ -> Titulo: "+promocion.getTitulo());
        try{
            //boolean result = promocionServicio.validarTokenUsuario(tu);
            boolean result = true;
            if (!result){
                return new ResponseEntity<Boolean>(false, HttpStatus.OK);
            }else{
                result = this.promocionServicio.nuevaPromocion(promocion, tu);
                if (result)  return new ResponseEntity<Boolean>(true, HttpStatus.OK); else return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
           }

        }catch (Exception e){
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("detalle/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id, @RequestBody LoginDatos ld){
        log.info(" POST -> /detalle/id: "+ld.getIdUsuario());

        try{
            boolean result = promocionServicio.validarTokenUsuario(ld);
            if (!result){
                return new ResponseEntity<String>("Token de usuario invalido ", HttpStatus.OK);
            }else{
                log.info(" Vamos a buscar los detalles de la promocion ");
                if (this.promocionServicio.existePromocion(id)) {
                    log.info(" La promoción existe");
                    Promocion p = this.promocionServicio.obtenerPromocion(id);
                    log.info(" Obtuve los datos de la promoción, se la devuelvo a FE");
                    return new ResponseEntity<Promocion>(p, HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>(" Id de promoción inexistente ", HttpStatus.OK);
                }

            }

        }catch (Exception e){
            log.info("Estamos saliendo por except "+e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("editar")
    public ResponseEntity<?> editar (@RequestBody PromocionRequest request){
        LoginDatos ld = request.getLoginDatos();
        Promocion promocion = request.getPromocion();
        log.info(" POST -> /editar/ \n User Logged: "+ld.getNombreUsuario());

        try{
            boolean result = promocionServicio.validarTokenUsuario(ld);
            if (!result){
                return new ResponseEntity<String>("Token de usuario invalido ", HttpStatus.OK);
            }else{
                log.info(" Vamos a editar la promocion con ID "+promocion.getId());
                // los datos de promocion ya están editados, lo que hay que hacer es actualizar las propiedades de MPAGO
                this.promocionServicio.editarPromocion(promocion, ld);
                log.info(" TERMINE DE EDITAR y GUARDE");
                return new ResponseEntity<String>("TODO BIEN AMIGO", HttpStatus.OK);


            }

        }catch (Exception e){
            log.info("Estamos saliendo por except "+e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }


    }
}
