package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.*;
import com.backend.singleton.ConfiguradorSingleton;
import com.backend.wrappers.PostRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/post/")
public class PostControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    final String UPLOAD_FOLDER = "src/images/";
    @Autowired InstrumentoServicio instrumentoServicio;
    @Autowired PromocionServicio promocionServicio;
    @Autowired UsuarioServicio usuarioServicio;
    @Autowired ArtistaServicio artistaServicio;
    @Autowired BiografiaServicio biografiaServicio;
    @Autowired PostServicio postServicio;
    @Autowired ElementoServicio elementoServicio;
    @Autowired ConfiguradorSingleton configuradorSingleton;
    @Autowired BandaServicio bandaServicio;
    @Autowired BiografiaBandaServicio biografiaBandaServicio;

    /*
        En post controlador voy a tener varias cosas que van a estar disponibles para el usuario
        - Obtener biografía
        - Obtener imagen de perfil
        - Obtener posts
        - Obtener elementos
        (4 cosas que están diferenciadas en el frontend
     */

    @PostMapping("existeBiografia")
    public ResponseEntity<?> existeBiografia (@RequestBody LoginDatos ld){
        try{

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Biografia b =  this.biografiaServicio.obtener(artista);
                if (b.getBiografiaBasica()!= null){
                    return new ResponseEntity (new Mensaje("existe biografia"), HttpStatus.OK);
                }else{
                    return new ResponseEntity(new Mensaje("No existe biografia"), HttpStatus.BAD_REQUEST);
                }

            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No existe biografia"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("RedSocialObtenerBiografia")
    public ResponseEntity<?> RedSocialObtenerBiografia (@RequestParam("login") String login, @RequestParam("nombre") String nombre){
        try{
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtenerPorNombre(nombre);
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Biografia b =  this.biografiaServicio.obtener(artista);
                ArrayList<String> respuesta = new ArrayList<String>();
                respuesta.add(b.getBiografiaBasica());
                respuesta.add(b.getSpotify());
                respuesta.add(b.getFacebook());

                return new ResponseEntity<ArrayList<String>> (respuesta, HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener biografia"), HttpStatus.OK);
        }
    }

    @PostMapping("RedSocialObtenerBiografiaBanda")
    public ResponseEntity<?> RedSocialObtenerBiografiaBanda (@RequestParam("login") String login, @RequestParam("nombre") String nombre){
        try{
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);

            if (promocionServicio.validarTokenUsuario(ld)){

                Banda banda = this.bandaServicio.obtenerBandaPorNombre(nombre);
                BiografiaBanda biografiaBanda = this.biografiaBandaServicio.obtener(banda);
                ArrayList<String> respuesta = new ArrayList<String>();
                respuesta.add(biografiaBanda.getBiografiaBasica());
                respuesta.add(biografiaBanda.getSpotify());
                respuesta.add(biografiaBanda.getFacebook());
                respuesta.add(biografiaBanda.getVideoBasico());
                respuesta.add(biografiaBanda.getListaYoutube());

                return new ResponseEntity<ArrayList<String>> (respuesta, HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener biografia"), HttpStatus.OK);
        }
    }

    @PostMapping("obtenerBiografia")
    public ResponseEntity<?> obtener (@RequestBody LoginDatos ld){
        try{

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Biografia b =  this.biografiaServicio.obtener(artista);
                ArrayList<String> respuesta = new ArrayList<String>();
                respuesta.add(b.getBiografiaBasica());
                respuesta.add(b.getSpotify());
                respuesta.add(b.getFacebook());

                return new ResponseEntity<ArrayList<String>> (respuesta, HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener biografia"), HttpStatus.OK);
        }
    }



    @PostMapping("obtenerBiografiaBanda")
    public ResponseEntity<?> obtenerbiografiaBanda (@RequestBody LoginDatos ld){
        try{

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Banda banda = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista).get(0);
                BiografiaBanda b =  this.biografiaBandaServicio.obtener(banda);
                ArrayList<String> respuesta = new ArrayList<String>();
                respuesta.add(b.getBiografiaBasica());
                respuesta.add(b.getSpotify());
                respuesta.add(b.getFacebook());
                respuesta.add(b.getVideoBasico());
                respuesta.add(b.getListaYoutube());

                return new ResponseEntity<ArrayList<String>> (respuesta, HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener biografia"), HttpStatus.OK);
        }
    }

    @PostMapping("actualizarBiografia")
    public ResponseEntity<?> actualizar (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje
        log.info (" BACKI");
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    Biografia bio = this.biografiaServicio.obtener(artista);
                    try{
                        String biografia = json.get("biografia").getAsString();
                        bio.setBiografiaBasica(biografia);
                    }catch (Exception e){

                    }
                     
                    bio.setArtista(artista);
                    this.biografiaServicio.guardar (bio);
                }
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
        }
    }

    @PostMapping("actualizarBiografiaBanda")
    public ResponseEntity<?> actualizarBanda (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje
        log.info (" BACKI");
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                Banda banda = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista).get(0);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    BiografiaBanda bio = this.biografiaBandaServicio.obtener(banda);
                    try{
                        String biografia = json.get("biografia").getAsString();
                        bio.setBiografiaBasica(biografia);
                    }catch (Exception e){

                    }

                    bio.setBanda(banda);
                    this.biografiaBandaServicio.guardar (bio);
                }
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
        }
    }



    @PostMapping("actualizarBiografiaSpotify")
    public ResponseEntity<?> actualizarBiografiaSpotify (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje
        log.info (" BACKI");
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    Biografia bio = this.biografiaServicio.obtener(artista);
                    try{
                        String spotify = json.get("spotify").getAsString();
                        bio.setSpotify(spotify);
                    }catch (Exception e){

                    }
                    bio.setArtista(artista);
                    this.biografiaServicio.guardar (bio);
                }
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
        }
    }


    @PostMapping("actualizarListaYoutube")
    public ResponseEntity<?> actualizarlistaYoutube (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje
        log.info (" BACKI");
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                Banda banda = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista).get(0);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    BiografiaBanda bio = this.biografiaBandaServicio.obtener(banda);
                    try{
                        String youtube = json.get("listaYoutube").getAsString();
                        if (!(youtube.startsWith("https://www.youtube.com/watch?v="))){
                            youtube = youtube.split(Pattern.quote("?"))[0];
                        }


                        bio.setListaYoutube(youtube);
                    }catch (Exception e){

                    }
                    bio.setBanda(banda);
                    this.biografiaBandaServicio.guardar (bio);
                }
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
        }
    }

    @PostMapping("actualizarVideoYoutube")
    public ResponseEntity<?> actualizarVideoYoutube (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje
        log.info (" BACKI");
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                Banda banda = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista).get(0);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    BiografiaBanda bio = this.biografiaBandaServicio.obtener(banda);
                    try{
                        String youtube = json.get("videoYoutube").getAsString();
                        youtube = youtube.replace("watch?v=", "embed/");

                        youtube = youtube.split(Pattern.quote("&"))[0];
                        bio.setVideoBasico(youtube);
                    }catch (Exception e){

                    }
                    bio.setBanda(banda);
                    this.biografiaBandaServicio.guardar (bio);
                }
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
        }
    }


    @PostMapping("actualizarBiografiaSpotifyBanda")
    public ResponseEntity<?> actualizarBiografiaSpotifyBanda (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje
        log.info (" BACKI");
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                Banda banda = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista).get(0);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    BiografiaBanda bio = this.biografiaBandaServicio.obtener(banda);
                    try{
                        String spotify = json.get("spotify").getAsString();
                        bio.setSpotify(spotify);
                    }catch (Exception e){

                    }
                    bio.setBanda(banda);
                    this.biografiaBandaServicio.guardar (bio);
                }
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
        }
    }
    @PostMapping("actualizarBiografiaFacebook")
    public ResponseEntity<?> actualizarBiografiaFacebook (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje
        log.info (" BACKI");
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    Biografia bio = this.biografiaServicio.obtener(artista);
                    try{
                        String facebook = json.get("facebook").getAsString();
                        bio.setFacebook(facebook);
                    }catch (Exception e){

                    }
                    bio.setArtista(artista);
                    this.biografiaServicio.guardar (bio);
                }
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
        }
    }

    @PostMapping("actualizarBiografiaFacebookBanda")
    public ResponseEntity<?> actualizarBiografiaFacebookBanda (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje
        log.info (" BACKI");
        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                Banda banda = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista).get(0);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    BiografiaBanda bio = this.biografiaBandaServicio.obtener(banda);
                    try{
                        String facebook = json.get("facebook").getAsString();
                        bio.setFacebook(facebook);
                    }catch (Exception e){

                    }
                    bio.setBanda(banda);
                    this.biografiaBandaServicio.guardar (bio);
                }
                return new ResponseEntity(new Mensaje("REALIZADO"), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude actualizar biografia"), HttpStatus.OK);
        }
    }


    @PostMapping("RedSocialObtenerPost")
    public ResponseEntity<?> RedSocialObtenerPost (@RequestParam("login") String login, @RequestParam("nombre") String nombre){
        try{
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtenerPorNombre(nombre);
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Biografia b =  this.biografiaServicio.obtener(artista);

                List<Post> arrayPost = b.getPosts();
                ArrayList<Post> alist = new ArrayList<Post>();
                for (Post post : arrayPost) {
                    Post p = new Post();
                    p.setInformacion(post.getInformacion());
                    p.setId(post.getId());
                    p.setFechaCreacion(post.getFechaCreacion());
                    p.setFechaEdicion(post.getFechaEdicion());
                    List<Elemento> elementos = this.elementoServicio.obtenerTodos(post);
                    //log.info("Primer elemento: "+elementos.get(0).getRutaAcceso()+" // "+elementos.get(0).getTipoRecurso()+" ID: "+elementos.get(0).getId());
                    ArrayList<Elemento> response = new ArrayList<Elemento>();
                    for (Elemento elemento: elementos) {
                        Elemento e = new Elemento();
                        e.setRutaAcceso(elemento.getRutaAcceso());
                        e.setTipoRecurso(elemento.getTipoRecurso());
                        response.add(e);
                    }
                    p.setElementos(response);
                    alist.add(p);
                }
                log.info(" Llegamos ");

                return new ResponseEntity<ArrayList<Post>> (alist, HttpStatus.OK);
            }

            else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }


    @PostMapping("obtenerpostsporusuario")
    public ResponseEntity<?> obtenerPosts (@RequestBody LoginDatos ld){
        try{
            log.info(("TOMAR DATOS POSTS"));
            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Biografia b =  this.biografiaServicio.obtener(artista);

                List<Post> arrayPost = b.getPosts();
                ArrayList<Post> alist = new ArrayList<Post>();
                for (Post post : arrayPost) {
                    Post p = new Post();
                    p.setInformacion(post.getInformacion());
                    p.setId(post.getId());
                    p.setFechaCreacion(post.getFechaCreacion());
                    p.setFechaEdicion(post.getFechaEdicion());
                    List<Elemento> elementos = this.elementoServicio.obtenerTodos(post);
                    //log.info("Primer elemento: "+elementos.get(0).getRutaAcceso()+" // "+elementos.get(0).getTipoRecurso()+" ID: "+elementos.get(0).getId());
                    ArrayList<Elemento> response = new ArrayList<Elemento>();
                    for (Elemento elemento: elementos) {
                        Elemento e = new Elemento();
                        e.setRutaAcceso(elemento.getRutaAcceso());
                        e.setTipoRecurso(elemento.getTipoRecurso());
                        response.add(e);
                    }
                    p.setElementos(response);
                    alist.add(p);
                }
                log.info(" Llegamos ");

                return new ResponseEntity<ArrayList<Post>> (alist, HttpStatus.OK);
            }

            else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }


    @PostMapping("obtenerElementos")
    public ResponseEntity<?> obtenerElementos (@RequestBody Long idpost){
        try{
            log.info(("TOMAR DATOS ID POST PARA ELEMENTOS "));
            Post post = this.postServicio.obtenerPostPorId(idpost);
            List<Elemento> elementos = this.elementoServicio.obtenerTodos(post);
            //log.info("Primer elemento: "+elementos.get(0).getRutaAcceso()+" // "+elementos.get(0).getTipoRecurso()+" ID: "+elementos.get(0).getId());
            ArrayList<Elemento> response = new ArrayList<Elemento>();
            for (Elemento elemento: elementos) {
                Elemento e = new Elemento();
                e.setRutaAcceso(elemento.getRutaAcceso());
                e.setTipoRecurso(elemento.getTipoRecurso());
                response.add(e);
            }
            return new ResponseEntity<ArrayList<Elemento>> (response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }

    @PostMapping("RedSocialObtenerImagenPerfil")
    public ResponseEntity<?> RedSocialObtenerImagenPerfil (@RequestParam("login") String login, @RequestParam("nombre") String nombre){
        try{
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            if (this.promocionServicio.validarTokenUsuario(ld)){
                // Si el usuario de logindatos es válido, voy a avanzar a buscar la info del otro usuario

                Usuario u =  this.usuarioServicio.obtenerPorNombre(nombre);
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Biografia b =  this.biografiaServicio.obtener(artista);
                String pathImagenPerfil = b.getPathImagenPerfil();

                return new ResponseEntity(new Mensaje(pathImagenPerfil), HttpStatus.OK);

            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener la imagen de perfil "), HttpStatus.OK);
        }
    }

    @PostMapping("RedSocialObtenerImagenPerfilBanda")
    public ResponseEntity<?> RedSocialObtenerImagenPerfilBanda (@RequestParam("login") String login, @RequestParam("nombre") String nombre){
        try{
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            if (this.promocionServicio.validarTokenUsuario(ld)){
                // Si el usuario de logindatos es válido, voy a avanzar a buscar la info del otro usuario

                Banda banda = this.bandaServicio.obtenerBandaPorNombre(nombre);
                BiografiaBanda biografiaBanda = this.biografiaBandaServicio.obtener(banda);
                String pathImagenPerfil = biografiaBanda.getPathImagenPerfil();

                return new ResponseEntity(new Mensaje(pathImagenPerfil), HttpStatus.OK);

            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener la imagen de perfil "), HttpStatus.OK);
        }
    }

    @PostMapping("obtenerImagenPerfil")
    public ResponseEntity<?> obtenerimagenperfil (@RequestBody LoginDatos ld){
        try{

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Biografia b =  this.biografiaServicio.obtener(artista);
                String pathImagenPerfil = b.getPathImagenPerfil();

                return new ResponseEntity(new Mensaje(pathImagenPerfil), HttpStatus.OK);

            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener la imagen de perfil "), HttpStatus.OK);
        }
    }

    @PostMapping("obtenerImagenPerfilBanda")
    public ResponseEntity<?> obtenerImagenPerfilBanda (@RequestBody LoginDatos ld){
        try{

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Banda b = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista).get(0);
                BiografiaBanda bb = this.biografiaBandaServicio.obtener(b);

                String pathImagenPerfil = bb.getPathImagenPerfil();

                return new ResponseEntity(new Mensaje(pathImagenPerfil), HttpStatus.OK);

            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener la imagen de perfil "), HttpStatus.OK);
        }
    }

    @PostMapping("obtenerImagenPortada")
    public ResponseEntity<?> obtenerimagenportada(@RequestBody LoginDatos ld){
        try{

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(u);
                Biografia b =  this.biografiaServicio.obtener(artista);
                String pathImagenPortada = b.getPathImagenPortada();

                return new ResponseEntity <String> (pathImagenPortada, HttpStatus.OK);



            }else{
                return new ResponseEntity(new Mensaje("no pude validar token"), HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude obtener la imagen de portada"), HttpStatus.OK);
        }
    }


    @PostMapping("crearPost")
    public ResponseEntity<?> crearPost (@RequestBody String payload){
        // Lo que hago es generar un objeto general JSON con la carga que me viene en el mensaje
        // esto aplica a cualquier tipo de mensaje

        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);
            JsonObject formulario = new Gson().fromJson(json.get("post"), JsonObject.class);

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                Biografia bio = this.biografiaServicio.obtener(artista);
                Post post = new Post();
                post.setBiografia(bio);
                post.setNickname(artista.getNickname());
                post.setUsername(usuario.getUsername());
                post.setInformacion(formulario.get("informacion").getAsString());
                Date actual = Calendar.getInstance().getTime();
                post.setFechaCreacion(actual);

                //bio.addPost(post);
                this.biografiaServicio.guardar(bio);

                this.postServicio.guardar(post);

                // creo el elemento youtube
                try{
                    String urlYoutube = formulario.get("youtube").getAsString();
                    if (urlYoutube.length()>0){
                        try{
                            urlYoutube = urlYoutube.replace("watch?v=", "embed/");

                            urlYoutube = urlYoutube.split(Pattern.quote("&"))[0];

                        }catch (Exception e){
                            log.info(" NO youtube URL CHANGEd");
                        }
                        Elemento video = new Elemento();
                        video.setTipoRecurso("youtube");
                        video.setPost(post);
                        video.setRutaAcceso(urlYoutube);
                        this.elementoServicio.guardar(video);
                    }
                }catch (Exception e){

                }

                return new ResponseEntity(new Mensaje(String.valueOf(post.getId())), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }




        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude crear post"), HttpStatus.OK);
        }
    }


    @PostMapping("borrarPost")
    public ResponseEntity<?> borrarPost (@RequestBody PostRequest request){
        LoginDatos ld = request.getLoginDatos();
        Post post = request.getPost();
        log.info(" POST -> /borrarPost/ User Logged: " + ld.getNombreUsuario() + " - FROM: " + post.getInformacion() );
        try{
            if (postServicio.validarTokenUsuario(ld)){

                this.postServicio.borrarPost(post);
                log.info(" ELIMINE BD");
                return new ResponseEntity<Mensaje>(new Mensaje("Borrado correctamente"), HttpStatus.OK);

            }
            return new ResponseEntity<String>("Token de autenticación no válido", HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            log.info("Estamos saliendo por except "+e.getMessage());
            return new ResponseEntity<Object>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("subirImagenPerfil")
    public ResponseEntity<?> subirImagenPerfil (@RequestParam("file") MultipartFile file, @RequestParam("login") String login){
        try{
            // [STEP 0] - Obtener las estructuras

            LoginDatos eled = new Gson().fromJson(login, LoginDatos.class);


            Usuario usuario = this.usuarioServicio.obtener(Long.valueOf(eled.getIdUsuario()));
            Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
            Biografia bio = this.biografiaServicio.obtener(artista);

            // [STEP 1] Preparo para guardar el binario en el folder del usuario
            String folder = this.UPLOAD_FOLDER+usuario.getUsername()+"/perfil/";
            File directory = new File(folder);
            if (!(directory).exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Directorio creado"+directory.getAbsolutePath());
                }
            }else{
                String[] files = directory.list();
                for (String f: files) {
                    File remove = new File(directory.getAbsolutePath() + f);
                    remove.delete();
                    remove.deleteOnExit();
                }
            }


            System.out.println("Eliminé imágenes actuales (para dejar 1 sola");

            // [STEP 3] - subir img de perfil

            String pathFile = folder +file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path, bytes);
            // creo el elemento vacio

            bio.setPathImagenPerfil(pathFile);
            this.biografiaServicio.guardar(bio);
            return new ResponseEntity(new Mensaje("Se guardo la imagen de perfil "), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }


    @PostMapping("subirImagenPerfilBanda")
    public ResponseEntity<?> subirImagenPerfilBanda (@RequestParam("file") MultipartFile file, @RequestParam("login") String login){
        try{
            // [STEP 0] - Obtener las estructuras

            LoginDatos eled = new Gson().fromJson(login, LoginDatos.class);


            Usuario usuario = this.usuarioServicio.obtener(Long.valueOf(eled.getIdUsuario()));
            Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
            Banda b = this.bandaServicio.obtenerBandasDeLasQueSoyAdmin(artista).get(0);
            BiografiaBanda  bio = this.biografiaBandaServicio.obtener(b);


            // [STEP 1] Preparo para guardar el binario en el folder del usuario
            String folder = this.UPLOAD_FOLDER+b.getNombre()+"/banda/";
            File directory = new File(folder);
            if (!(directory).exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Directorio creado"+directory.getAbsolutePath());
                }
            }else{
                String[] files = directory.list();
                for (String f: files) {
                    File remove = new File(directory.getAbsolutePath() + f);
                    remove.delete();
                    remove.deleteOnExit();
                }
            }


            System.out.println("Eliminé imágenes actuales (para dejar 1 sola");

            // [STEP 3] - subir img de perfil

            String pathFile = folder +file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path, bytes);
            // creo el elemento vacio

            bio.setPathImagenPerfil(pathFile);
            this.biografiaBandaServicio.guardar(bio);
            return new ResponseEntity(new Mensaje("Se guardo la imagen de perfil "), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }

    @PostMapping("obtenerHomeSite")
    public ResponseEntity<?> obtenerHomeSite (@RequestParam("login") String login, @RequestParam("inicio") String inicio, @RequestParam("fin") String fin){

        try{
            List<Post> lp = this.postServicio.obtenerTodos();
           return new ResponseEntity<List<Post>>(lp, HttpStatus.OK);
        }catch (Exception e){
            log.info(" something has failed");
            return new ResponseEntity<List<Post>>((List<Post>) null, HttpStatus.BAD_REQUEST);
        }


    }

    @PostMapping("buscarLike")
    public ResponseEntity<?> buscarLike (@RequestParam("login") String login, @RequestParam("opcion") String opcion, @RequestParam("busqueda") String busqueda){

        try{

            List<Post> lp = this.postServicio.buscarLike (busqueda);
            return new ResponseEntity<List<Post>>(lp, HttpStatus.OK);
        }catch (Exception e){
            log.info(" something has failed");
            return new ResponseEntity<List<Post>>((List<Post>) null, HttpStatus.BAD_REQUEST);
        }


    }


    @PostMapping("subirimagen")
    public ResponseEntity<?> subirimagen (@RequestParam("file") MultipartFile file, @RequestParam("id") String id, @RequestParam("login") String login ){
        try{
            // Obtengo en post
            log.info ("arrancamos vamos a buscar");
            LoginDatos ld = new Gson().fromJson(login, LoginDatos.class);
            Usuario usuario = this.usuarioServicio.obtener(Long.valueOf(ld.getIdUsuario()));

            Post post = this.postServicio.obtenerPostPorId(Long.parseLong(id));

            // verifico si existe el path
            String folder = this.UPLOAD_FOLDER+"/"+usuario.getUsername()+"/recursosUsuario/";
            File directory = new File(folder);
            if (!(directory).exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Directorio creado"+directory.getAbsolutePath());
                }
            }

            String pathFile = this.UPLOAD_FOLDER+usuario.getUsername()+"/recursosUsuario/"+file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path, bytes);
            // creo el elemento vacio
            Elemento el = new Elemento();

            el.setRutaAcceso(pathFile);
            el.setPost(post);
            el.setTipoRecurso("imglocal");
            Date actual = Calendar.getInstance().getTime();
            el.setFechaCreacion(actual);
            this.elementoServicio.guardar(el);
            return new ResponseEntity(new Mensaje("Se guardaron los elementos y el post"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }


}
