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
@CrossOrigin(origins = "*")
@RequestMapping("/api/promocion/")
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
            if (promocionServicio.validarTokenUsuario(ld)){
                log.info (" TOKEN VALIDADO OK ! ");

	                log.info("Buscando promociones");
	                Usuario user = this.usuarioServicio.obtener(ld.getIdUsuario());
	                Comercio comercio = this.comercioServicio.obtener(user);
                     ArrayList<Promocion> promociones = new ArrayList<Promocion>();
	                if (this.promocionServicio.existe(comercio.getId())){

	                   promociones = this.promocionServicio.obtenerPromociones(comercio.getId());
	                    log.info ("Cantidad de promociones: "+promociones.size());
	                    return new ResponseEntity<ArrayList<Promocion>>(promociones, HttpStatus.OK);
	                }else{
	                    log.info (" No hay promociones" );

	                    return new ResponseEntity<ArrayList<Promocion>>(promociones, HttpStatus.OK);
	                }

            }else{
                return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
            }


        }catch (Exception e){
            log.info("Estamos saliendo por except "+e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("obtenerVigentes")
    public ResponseEntity<?> obtenerVigentes(@RequestBody LoginDatos ld) {
        log.info(" POST -> /listar/ \n User Logged: " + ld.getNombreUsuario());
        try {
            if (promocionServicio.validarTokenUsuario(ld)) {
                log.info(" TOKEN VALIDADO OK ! ");

                log.info("Buscando promociones vigentes");
                ArrayList<Promocion> promociones = new ArrayList<Promocion>();

                promociones = this.promocionServicio.obtenerPromocionesVigentes();
                log.info("Cantidad de promociones: " + promociones.size());
                return new ResponseEntity<ArrayList<Promocion>>(promociones, HttpStatus.OK);

            } else {
                return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
            }


        } catch (Exception e) {
            log.info("Estamos saliendo por except " + e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("nuevo")
    public ResponseEntity<?> nuevo(@RequestBody PromocionRequest request){

        LoginDatos ld = request.getLoginDatos();
        Promocion promocion = request.getPromocion();
        log.info(" POST -> /nuevo/ -> User: "+ld.getNombreUsuario());
        log.info(" Articulo -> /nuevo/ -> Titulo: "+promocion.getTitulo());
        try{

            // [STEP 0] - Validar Token Usuario
            if (promocionServicio.validarTokenUsuario(ld)) {
                // [STEP 1] - Validar que tenga los permisos necesarios para realizar la operacion


                    String resultadoNuevaPromocion = this.promocionServicio.nuevaPromocion(promocion, ld);
                    if (resultadoNuevaPromocion.equals("ok")){
                        log.info(" GUARDE CON EXITO, respondo");
                        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
                    }else {
                        if (resultadoNuevaPromocion.equals("No se pudo publicar en MP la promocion")){
                            log.info(" Tuve problemas con MP");
                            return new ResponseEntity<String>(resultadoNuevaPromocion, HttpStatus.BAD_REQUEST);
                        }else{
                             // ES DECIR EXCEPCION
                            log.info(" Salí por except: "+resultadoNuevaPromocion);
                            return new ResponseEntity<String>(resultadoNuevaPromocion, HttpStatus.BAD_REQUEST);
                        }
                    }

            }	
            return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("detalle/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id, @RequestBody LoginDatos ld){
        log.info(" POST -> /detalle/id: "+ld.getIdUsuario());

        try{
            if (promocionServicio.validarTokenUsuario(ld)){

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
            return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.info("Estamos saliendo por except "+e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("editar")
    public ResponseEntity<?> editar (@RequestBody PromocionRequest request){
        LoginDatos ld = request.getLoginDatos();
        Promocion promocion = request.getPromocion();
        log.info(" POST -> /editar/ User Logged: "+ld.getNombreUsuario() + " - PROM: " +promocion.getTitulo() );
        try{
            if (promocionServicio.validarTokenUsuario(ld)){

	                //log.info(" Vamos a editar la promocion con ID "+promocion.getId());
	                // los datos de promocion ya están editados, lo que hay que hacer es actualizar las propiedades de MPAGO
	                this.promocionServicio.editarPromocion(promocion, ld);
	                log.info(" TERMINE DE EDITAR y GUARDE");
	                return new ResponseEntity<Mensaje>(new Mensaje("Editado correctamente"), HttpStatus.OK);

            }
            return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.info("Estamos saliendo por except "+e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("borrar")
    public ResponseEntity<?> borrar (@RequestBody PromocionRequest request){
        LoginDatos ld = request.getLoginDatos();
        Promocion promocion = request.getPromocion();
        log.info(" POST -> /borrar/ User Logged: "+ld.getNombreUsuario() + " - PROM: " +promocion.getTitulo() );
        try{
            if (promocionServicio.validarTokenUsuario(ld)){

                this.promocionServicio.borrarPromocion(promocion, ld);
                log.info(" TERMINE DE BORRAR MP y ELIMINE BD");
                return new ResponseEntity<Mensaje>(new Mensaje("Borrado correctamente"), HttpStatus.OK);

            }
            return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.info("Estamos saliendo por except "+e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }


    }
}
