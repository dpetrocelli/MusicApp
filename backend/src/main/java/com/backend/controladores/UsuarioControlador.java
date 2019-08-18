package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.Artista;
import com.backend.entidades.Comercio;
import com.backend.entidades.Rol;
import com.backend.entidades.TokenUsuario;
import com.backend.entidades.Usuario;

import com.backend.recursos.LoginDatos;


import com.backend.servicios.UsuarioServicio;
import com.backend.singleton.ConfiguradorSingleton;
import com.backend.wrappers.RegistrarUsuarioRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/usuario/")
@CrossOrigin(origins = "*")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;


    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("registrar")
    public ResponseEntity<?> registrar(@RequestBody RegistrarUsuarioRequest request){
    	Usuario usuarioFE = request.getUsuario();
    	if(request.getArtista() != null) {
    		log.info("Registrando a "+usuarioFE.getUsername()+" Tipo: Artista.");
    		Artista artista = request.getArtista();
            if(this.usuarioServicio.guardar(usuarioFE, artista))
            	return new ResponseEntity(new Mensaje(" El usuario "+ usuarioFE.getUsername()+" se creó correctamente"), HttpStatus.OK);
            else
                return new ResponseEntity(new Mensaje(" Error al persistir"), HttpStatus.BAD_REQUEST);
            	
    	}else{
    		log.info("Registrando a "+usuarioFE.getUsername()+" Tipo: Comercio.");
    		Comercio comercio = request.getComercio();
            if(this.usuarioServicio.guardar(usuarioFE, comercio))
            	return new ResponseEntity(new Mensaje(" El usuario "+ usuarioFE.getUsername()+" se creó correctamente"), HttpStatus.OK);
            else
                return new ResponseEntity(new Mensaje(" Error al persistir"), HttpStatus.BAD_REQUEST);
    	}
    }

    @PostMapping("ingresar")
    public ResponseEntity<?> ingresar(@RequestBody Usuario usuarioFrontEnd){
        // [STEP 0] - Validar usuario y contraseña
        log.info("Usuario : " + usuarioFrontEnd.getUsername() + " Está ingresando al sistema");
        if (!this.usuarioServicio.existe (usuarioFrontEnd)){
            return new ResponseEntity("Usuario o contraseña incorrectos", HttpStatus.BAD_REQUEST); //No existe dicho usuario
        }else{
            // SI NO HAY USUARIO SALE X EXCEPT QUE NO EXISTE
            String username = usuarioFrontEnd.getUsername();
            // Validar
            if (!usuarioServicio.validarCredenciales (usuarioFrontEnd)){
                log.info("La contraseña del usuario no es válida para el usuario: "+ usuarioFrontEnd.getUsername());
                return new ResponseEntity("Usuario o contraseña incorrectos", HttpStatus.BAD_REQUEST);
            }else{
                Usuario usuarioBackend = this.usuarioServicio.obtenerPorNombre(username);
                log.info("Usuario y Password OK: "+usuarioBackend.getUsername()+" |Rol:"+usuarioBackend.rolesToString());

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

    @PostMapping("chequear_permisos_por_rol")
    public ResponseEntity<?> permisosgeneral(@RequestBody String payload){
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try {
            log.info("Pude crear el objeto básico JSON ");
            log.info("siendo: " + payload.toString());
            log.info(" Como yo se los objetos que vienen, puedo castear los wrappers por parte");
            log.info("Objeto 1: LoginDatos, Objeto 2: String -> será luego roles");
            LoginDatos ld = new Gson().fromJson(json.get("ld"), LoginDatos.class);
            String funcionSistema = json.get("sitio").getAsString();
            log.info("/Check Permisos/ -> User:" + ld.getNombreUsuario() + " Sitio: " + funcionSistema);
            return null;
        }catch (Exception e){
            return new ResponseEntity<Mensaje>(new Mensaje("No tiene permisos su usuario"), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("chequear_permisos_por_subsite")
    public ResponseEntity<?> permisos(@RequestBody String payload){
		// Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje

        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
		try{
			log.info("Pude crear el objeto básico JSON ");
			log.info ("siendo: "+payload.toString());
			log.info( " Como yo se los objetos que vienen, puedo castear los wrappers por parte");
			log.info( "Objeto 1: LoginDatos, Objeto 2: String");
			LoginDatos ld = new Gson().fromJson(json.get("ld"), LoginDatos.class);
			String funcionSistema = json.get("sitio").getAsString();
			log.info("/Check Permisos/ -> User:"+ld.getNombreUsuario()+" Sitio: "+funcionSistema);

            try {
                log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
                boolean result = this.usuarioServicio.validarTokenUsuario(ld);
                // SI DATOS OK
                if (result){
                    log.info(" MI USUARIO Y TOKEN TIENEN PERMISO, SIGO a 'DISPONE PERMISO' ");
                    // tengo que validar si dispone la funcion habilitada (es decir el string)
                    boolean permisos = this.usuarioServicio.disponePermisos(ld,funcionSistema);
                    log.error(" PERMISO ?:  "+permisos);
                    if (permisos) return new ResponseEntity<Mensaje>(new Mensaje("Tiene permiso"), HttpStatus.OK);
                    else  return new ResponseEntity<Mensaje>(new Mensaje("No tiene permisos su usuario"), HttpStatus.FORBIDDEN);

                }else{
                    return new ResponseEntity<Mensaje>(new Mensaje("Token de usuario alterado o inválido"), HttpStatus.FORBIDDEN);
                }
            }catch (Exception e){
                return new ResponseEntity<Mensaje>(new Mensaje("Excepcion en token de usuario"), HttpStatus.FORBIDDEN);
            }


		}catch(Exception e){
			System.out.println(e.getMessage());
			return new ResponseEntity<Mensaje>(new Mensaje("Error de comunicacion entre servidor/cliente"), HttpStatus.NOT_FOUND);
		}


    }
}
