package com.backend.controladores;

import com.backend.dto.Mensaje;
import com.backend.entidades.Artista;
import com.backend.entidades.Comercio;
import com.backend.entidades.Rol;
import com.backend.entidades.TokenUsuario;
import com.backend.entidades.Usuario;

import com.backend.recursos.LoginDatos;


import com.backend.servicios.ArtistaServicio;
import com.backend.servicios.ComercioServicio;
import com.backend.servicios.UsuarioServicio;
import com.backend.singleton.ConfiguradorSingleton;
import com.backend.wrappers.RegistrarUsuarioRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuario/")
public class UsuarioControlador {

    @Autowired
    UsuarioServicio usuarioServicio;
    @Autowired
    ComercioServicio comercioServicio;

    @Autowired
    ArtistaServicio artistaServicio;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @PostMapping("registrar")
    public ResponseEntity<?> registrar(@RequestBody String payload) {
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje

        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try {
            log.info("Pude crear el objeto básico JSON ");
            log.info("siendo: " + payload.toString());
            //log.info( " Como yo se los objetos que vienen, puedo castear los wrappers por parte");
            //log.info( "Objeto 1: LoginDatos, Objeto 2: Formulario (String), 3: String[+ ");
            Usuario usuario = new Gson().fromJson(json.get("usuario"), Usuario.class);
            JsonObject formulario = new Gson().fromJson(json.get("formulario"), JsonObject.class);
            log.info ("FORM: "+formulario.toString());
            boolean artista = formulario.get("isArtista").getAsBoolean();

            if (artista){
                String instrumentos = new Gson().fromJson(json.get("instrumentos"), String.class);
                this.usuarioServicio.guardarArtista(usuario, formulario, instrumentos);
                return new ResponseEntity(new Mensaje(" El usuario ARTISTA se creó correctamente"), HttpStatus.OK);
            }else{
                this.usuarioServicio.guardarComercio(usuario, formulario);
                return new ResponseEntity(new Mensaje(" El usuario COMERCIO se creó correctamente"), HttpStatus.OK);

            }


        } catch (Exception e) {
            return new ResponseEntity(new Mensaje(" El uNOOOOOOOOOOOOOOOOOOOO se creó correctamente"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("obtenerTodos")
    public ResponseEntity<?> obtenerTodos (@RequestBody LoginDatos ld) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                List<Artista> listArtista = this.artistaServicio.obtenerTodos();
                ArrayList<String> listaRespuesta = new ArrayList<String>();

                for (Artista artista : listArtista){
                    listaRespuesta.add( artista.getUsuario().getUsername());
                }
                return new ResponseEntity<ArrayList<String>>(listaRespuesta, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("obtenerDatosUsuario")
    public ResponseEntity<?> obtenerUsuario (@RequestBody LoginDatos ld) {
        // [STEP 0] - Validar usuario y contraseña

        try {
            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                return new ResponseEntity<Artista>(artista, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }


    @PostMapping("obtenerDatosUsuarioPorId")
    public ResponseEntity<?> obtenerDatosUsuarioPorId (@RequestParam("login") String login, @RequestParam("id") String id){
        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                Artista artista = this.artistaServicio.obtener(Long.parseLong(id));
                return new ResponseEntity<Artista>(artista, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("RedSocialObtenerDatosUsuario")
    public ResponseEntity<?> RedSocialObtenerDatosUsuario (@RequestParam("login") String login, @RequestParam("nombre") String nombre){
        try {
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);

            if (this.usuarioServicio.validarTokenUsuario(ld)) {
                Usuario usuario = this.usuarioServicio.obtenerPorNombre(nombre);
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                return new ResponseEntity<Artista>(artista, HttpStatus.OK);

            }else{
                return new ResponseEntity<String>(" No autorizado", HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception e) {
            return new ResponseEntity<String>(" ERROR ", HttpStatus.BAD_REQUEST);
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
                //log.info("---\n VAMOS A GENERAR/REGENERAR TOKEN:\n"+usuarioBackend.toString()+"\n---");
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

    @PostMapping("comercio_esta_activado")
    public ResponseEntity<Boolean> comercio_esta_activado (@RequestBody LoginDatos loginDatos){
        log.info(" LLEGO A COMERCIO:ESTA:ACTIVADO");

        log.info ( " USUARIO: "+loginDatos.getIdUsuario());
        Usuario u = this.usuarioServicio.obtenerPorNombre(loginDatos.getNombreUsuario());
        Comercio c = this.comercioServicio.obtener(u);
        log.info(" Obtuve el comercio");
        if (c.getAccessToken()!= null) {
            log.info(" hay vinculación a MP ");
            return new ResponseEntity(true, HttpStatus.OK);
        }
        else {
            log.info(" no hay vinculación a mp");
            return new ResponseEntity(false, HttpStatus.OK);
        }



    }
    @PostMapping("validar")
    public ResponseEntity<Boolean> validar (@RequestBody LoginDatos loginDatos){

        try{
            //log.info(" VALIDANDO TOKEN USUARIO "+loginDatos.getNombreUsuario());

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
    public ResponseEntity<?> permisosgeneral(@RequestBody LoginDatos loginDatos){
        //log.info(" Chequear -> permisos -> ROL");
        try{
            Usuario u = this.usuarioServicio.obtener(loginDatos.getIdUsuario());
            String rolResponse = "";
            for(Rol rol: u.getRoles()) {
                rolResponse = rol.getNombre();
                break;
            }
            //log.info(" ROL de USER: "+rolResponse);
            return new ResponseEntity<Mensaje>(new Mensaje(rolResponse), HttpStatus.OK);
        }catch (Exception e){
            log.info(" ESPLOTE");
            return new ResponseEntity<Mensaje>(new Mensaje(" NOOK "), HttpStatus.UNAUTHORIZED);
        }




    }
/*
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
    */

}
