package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.repositorios.ArtistaRepositorio;
import com.backend.repositorios.ComercioRepositorio;
import com.backend.repositorios.RolRepositorio;
import com.backend.repositorios.TokenUsuarioRepositorio;
import com.backend.repositorios.UsuarioRepositorio;

import com.backend.singleton.ConfiguradorSingleton;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;


@Service
@Transactional
public class UsuarioServicio {
	
    private static final Logger log = LoggerFactory.getLogger(UsuarioServicio.class);
    @Autowired UsuarioRepositorio usuarioRepositorio;
    @Autowired ArtistaRepositorio artistaRepositorio;
    @Autowired ComercioRepositorio comercioRepositorio;
    @Autowired RolRepositorio rolRepositorio;
    @Autowired TokenUsuarioServicio tokenUsuarioServicio;
    @Autowired ComercioServicio comercioServicio;
    @Autowired ComercioServicio artistaServicio;
    @Autowired InstrumentoServicio instrumentoServicio;
    @Autowired ConfiguradorSingleton configuradorSingleton;
    @Autowired BiografiaServicio biografiaServicio;
    public boolean validarCredenciales(Usuario usuarioFrontend){
        try{
            Usuario usuarioBackend = usuarioRepositorio.findByUsername(usuarioFrontend.getUsername());
            if (usuarioBackend.validatePassword(usuarioFrontend.getpwd())){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public boolean guardarArtista (Usuario usuarioFrontEnd, JsonObject formulario, String instrumentos){
        log.info( " ENTRAMOS ");
        if (this.usuarioRepositorio.existsByUsername(usuarioFrontEnd.getUsername())) return false; else {
            // PARTE DE USUARIO BASICO, OK
            // Obtengo el rol que me dice
            Rol rol = rolRepositorio.findByNombre("Artista");
            usuarioFrontEnd.addRol(rol);

            // Luego encodeo la pass
            usuarioFrontEnd.encodePassword();
            //log.info("Persistiendo usuario:"+usuarioFrontEnd.getUsername()+" rol: "+usuarioFrontEnd.rolesToString()+ " Artista: "+artista.getUsuario().getUsername());

            log.info(" desde FORM a Artista");
            Artista artista = new Artista();
            artista.setNombre(formulario.get("nombre").getAsString());
            artista.setApellido(formulario.get("apellido").getAsString());
            artista.setNickname(formulario.get("nickname").getAsString());
            artista.setGenero(formulario.get("genero").getAsString());
            try{
                artista.setDocumentoIdentidad(Integer.parseInt(formulario.get("documento").getAsString()));
            }catch (Exception e){
                artista.setDocumentoIdentidad(0);
            }
            //artista.setFechaNacimiento(formulario.get("fechanacimiento").getasd);


            ArrayList<String> listaInstrumentos = new ArrayList<String>();
            Set<Instrumento> setInstrumento = new HashSet<Instrumento>();

            try{
                String[] splitter = instrumentos.split(Pattern.quote(","));
                listaInstrumentos = (ArrayList<String>) Arrays.asList(splitter);
            }catch (Exception e){
                listaInstrumentos.add(instrumentos);
            }

            for (String inst : listaInstrumentos){
                try{
                    Instrumento instrumentoBD = this.instrumentoServicio.obtenerPorNombre(inst).get();
                    setInstrumento.add(instrumentoBD);

                }catch (Exception e){

                }

            }

            artista.setInstrumento(setInstrumento);
            artista.setUsuario(usuarioFrontEnd);
            Biografia biografia = new Biografia();
            biografia.setArtista(artista);
            this.biografiaServicio.guardar(biografia);
            this.usuarioRepositorio.save(usuarioFrontEnd);
            this.artistaRepositorio.save(artista);




            return true;
        }
    }

    public boolean guardarComercio(Usuario usuario, JsonObject formulario) {
        if (this.usuarioRepositorio.existsByUsername(usuario.getUsername())) return false; else {
            Rol rol = rolRepositorio.findByNombre("Comercio");
            usuario.addRol(rol);

            // Luego encodeo la pass
            usuario.encodePassword();
            Comercio c = new Comercio();
            c.setUsuario(usuario);
            c.setDireccion(formulario.get("direccion").getAsString());
            c.setRazonsocial(formulario.get("razonsocial").getAsString());
            this.usuarioRepositorio.save(usuario);
            this.comercioRepositorio.save(c);
            return true;
        }
    }

    public boolean guardar(Usuario usuarioFrontEnd, Comercio comercio) {
    	 if (this.usuarioRepositorio.existsByUsername(usuarioFrontEnd.getUsername())) return false; else {
			 // Obtengo el rol que me dice
			 Rol rol = rolRepositorio.findByNombre("Comercio");
			 usuarioFrontEnd.addRol(rol);
			 
			 // Luego encodeo la pass
			 usuarioFrontEnd.encodePassword();
			 this.usuarioRepositorio.save(usuarioFrontEnd);
			 this.comercioRepositorio.save(comercio);
     		 return true;
         }
    }

    public TokenUsuario generarTokenUsuario(Usuario usuario) {
        //log.info( "Generar Token Usuario  -> "+usuario.getUsername());
        TokenUsuario tu = tokenUsuarioServicio.crearTokenUsuario(usuario);
        this.tokenUsuarioServicio.guardarTokenUsuario(tu);
        return tu;
    }

    public boolean validarTokenUsuario (LoginDatos loginDatos){
        Long idUsuarioFE = loginDatos.getIdUsuario();
        String tokenFE = loginDatos.getTokenUsuario();

        TokenUsuario tuBE = tokenUsuarioServicio.obtenerTokenUsuario(idUsuarioFE);
        //log.info(" OBTUVIMOS TOKEN USUARIO CON ID USUARIO FE ");
        if ((tuBE != null) && (tokenFE.equals(tuBE.getToken()))){

            return tokenUsuarioServicio.validarToken(tuBE);
            // Si devuelve true -> Válido y renovado
            // Si devuelve false -> Se expiró
        }else{
            log.info(" DATOS INCONSISTENTES; DESLOGUEAR");
            return false;
        }
    }

    public Usuario obtener (Long idUsuario){
       return this.usuarioRepositorio.findById(idUsuario).get();
    }


    public Usuario obtenerPorNombre (String nombre){
        return this.usuarioRepositorio.findByUsername(nombre);
    }
    public boolean existe(Usuario usuarioFrontEnd) {
    	return this.usuarioRepositorio.existsByUsername((usuarioFrontEnd.getUsername()));
    }
    // Ahora no lo uso, solo uso a nivel general por el can activate del frontend
    // no hago chequeo a nivel de permisos locales
	public boolean disponePermisos(LoginDatos ld, String subsite) {
        Usuario tipoUsuario = this.usuarioRepositorio.findById(ld.getIdUsuario()).get();
        //log.info("encontre el usuario completo, valide su token, ahora");
        //log.info(" me encargo de ver si sus permisos están en la lista");
        try{
            if (this.comercioServicio.existe(tipoUsuario)){
                log.info(" EL USUARIO ES COMERCIO, entonces..");
                //log.error(" LISTA DE PERMISOS: "+this.configuradorSingleton.permisosDelComercio.toString());
                //log.error(" Y EL USUARIO pregunta por "+site);
                log.info(" OK -> Singleton: "+this.configuradorSingleton.permisosDelComercio.toString()+ " Subsite pedido: "+subsite);
                if (this.configuradorSingleton.permisosDelComercio.contains(subsite)){
                    log.info(" OK -> Singleton: "+this.configuradorSingleton.permisosDelComercio.toString()+ " Subsite: "+subsite);
                    return true;
                }else{
                    log.info(" No OK -> CHE NO ESTABAN");
                    return false;
                }
            }else{
                // ES un ARTISTa hay que completar
                return false;
            }
        }catch (Exception e){
            log.error("salida por excepcion" +e.getMessage());
            return false;
        }




	}


}
