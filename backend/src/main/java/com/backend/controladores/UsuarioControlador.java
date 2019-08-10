package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.Usuario;
import com.backend.servicios.UsuarioServicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/usuario/")
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;


    @PostMapping("registrar/{nombre}")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuarioFrontEnd, @PathVariable String nombre){
        System.out.println("USER:" +usuarioFrontEnd.toString());
        System.out.println("ROL POR URL :" +nombre);

        boolean guardado = this.usuarioServicio.guardar(usuarioFrontEnd, nombre);
        if (guardado){
            return new ResponseEntity(new Mensaje(" El usuario "+ usuarioFrontEnd.getUsername()+" se creó correctamente"), HttpStatus.OK);
        }else{
            return new ResponseEntity(new Mensaje(" El usuario a crear ya existe "), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("ingresar")
    public ResponseEntity<?> ingresar(@RequestBody Usuario usuarioFrontEnd){

            HashMap<Usuario,String> resultado = usuarioServicio.ingresar(usuarioFrontEnd);
            Map.Entry<Usuario,String> entry = resultado.entrySet().iterator().next();
            Usuario usuarioBackend = entry.getKey();
            String msg = entry.getValue();
            //System.out.println("MSG: "+msg+ " USUARIO BACKEND: "+usuarioBackend.getUsername());

            if (usuarioBackend == null){
                return new ResponseEntity(new Mensaje(" Usuario Inexistente: "+ usuarioFrontEnd.getUsername()), HttpStatus.BAD_REQUEST);
            }else{
                if (msg.equals("ok")){
                	usuarioBackend.setPassword("******");
                	System.out.println("---\nDevolviendo:\n"+usuarioBackend.toString()+"\n---");

                    return new ResponseEntity<Usuario>(usuarioBackend, HttpStatus.OK);
                }else{
                    return new ResponseEntity(new Mensaje(msg), HttpStatus.BAD_REQUEST);
                }
            }




    }

}
