package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.Rol;
import com.backend.entidades.TokenUsuario;
import com.backend.entidades.Usuario;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.UsuarioServicio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/usuario/")
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("registrar/{nombre}")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuarioFrontEnd, @PathVariable String nombre){
        log.info (" USER: "+usuarioFrontEnd.toString() + " - ROL (url) "+nombre);

      
        boolean guardado = this.usuarioServicio.guardar(usuarioFrontEnd, nombre);

        if (guardado){
            return new ResponseEntity(new Mensaje(" El usuario "+ usuarioFrontEnd.getUsername()+" se creó correctamente"), HttpStatus.OK);
        }else{
            return new ResponseEntity(new Mensaje(" El usuario a crear ya existe "), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("ingresar")
    public ResponseEntity<?> ingresar(@RequestBody Usuario usuarioFrontEnd){
            // [STEP 0] - Validar usuario y contraseña
        log.info("Usuario : " + usuarioFrontEnd.getUsername() + " ESTá tratando de ingresar");


                    if (!this.usuarioServicio.existe (usuarioFrontEnd)){
                        return new ResponseEntity(" No existe dicho usuario ", HttpStatus.BAD_REQUEST);
                    }else{
                        // SI NO HAY USUARIO SALE X EXCEPT QUE NO EXISTE
                        String username = usuarioFrontEnd.getUsername();
                        log.info("Usuario EXISTE ! : " + username );
                        // Validar
                        boolean resultado = usuarioServicio.validarCredenciales (usuarioFrontEnd);

                        if (!resultado){
                            log.info("La contraseña del usuario no es válida para el usuario: "+ usuarioFrontEnd.getUsername());
                            return new ResponseEntity(" contraseña no valida: ", HttpStatus.BAD_REQUEST);
                        }else{
                            Usuario usuarioBackend = this.usuarioServicio.obtenerPorNombre(username);
                            log.info(" Usuario y Password OK: "+usuarioBackend.getUsername());

                            // [STEP 1] - Cómo es el ingreso del usuario - Vamos a GENERAR Token (1st) ingreso
                            //           o ACTUALIZAR EL TOKEN de algún login
                            log.info("---\n VAMOS A GENERAR/REGENERAR TOKEN:\n"+usuarioBackend.toString()+"\n---");
                            TokenUsuario tu = usuarioServicio.generarTokenUsuario(usuarioBackend);
                            LoginDatos ld = new LoginDatos();
                            ld.setIdUsuario(tu.getIdUsuario());
                            ld.setNombreUsuario(usuarioBackend.getUsername());
                            Set<Rol> lr = usuarioBackend.getRoles();
                            String roles = "";
                            for (Rol rol: lr) {
                                roles+=rol.getNombre()+"-";
                            }
                            roles = roles.substring(0,roles.length()-1);
                            ld.setRoles(roles);
                            ld.setTokenUsuario(tu.getToken());

                            return new ResponseEntity<LoginDatos>(ld, HttpStatus.OK);

                        }
                    }











    }

    @PostMapping("validar")
    public ResponseEntity<Boolean> validar (@RequestBody LoginDatos loginDatos){

        try{
            log.info(" VALIDANDO TOKEN USUARIO "+loginDatos.getNombreUsuario());

            boolean result = this.usuarioServicio.validarTokenUsuario(loginDatos);

            // RESULT TRUE -> Credenciales OK y Token revalidado
            // RESULT FALSE -> Credenciales NOK o TOKEN No  OK
            if (result){
                log.info(" VALI OK");
                return new ResponseEntity(true, HttpStatus.OK);
            }else{
                log.info(" VALI NO OK ");
                return new ResponseEntity(false, HttpStatus.OK);
            }
        }catch (Exception e ){
            log.info(" ERROR PORQUE: "+e.getMessage());
            return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
        }


    }

}
