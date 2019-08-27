package com.backend.controladores;
import com.backend.dto.Mensaje;
import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.servicios.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/post/")
@CrossOrigin(origins = "*")
public class PostControlador {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired InstrumentoServicio instrumentoServicio;
    @Autowired PromocionServicio promocionServicio;
    @Autowired UsuarioServicio usuarioServicio;
    @Autowired ArtistaServicio artistaServicio;
    @Autowired BiografiaServicio biografiaServicio;
    @Autowired PostServicio postServicio;
    @Autowired ElementoServicio elementoServicio;

    @PostMapping("obtenerBiografia")
    public ResponseEntity<?> obtener (@RequestBody LoginDatos ld){
        try{

            if (promocionServicio.validarTokenUsuario(ld)){
                Usuario u = this.usuarioServicio.obtener(ld.getIdUsuario());

                Artista artista = this.artistaServicio.obtenerPorUsuario(u);

                Biografia b =  this.biografiaServicio.obtener(artista);
                return new ResponseEntity (new Mensaje(b.getBiografiaBasica()), HttpStatus.OK);



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

        JsonObject json = new Gson().fromJson(payload, JsonObject.class);
        try{
            log.info ("siendo: "+payload.toString());

            LoginDatos ld = new Gson().fromJson(json.get("login"), LoginDatos.class);
            String biografia = json.get("biografia").getAsString();

            log.info(" VALIDANDO CREDENCIALES USUARIO " + ld.getNombreUsuario());
            boolean result = this.usuarioServicio.validarTokenUsuario(ld);

            if (result){
                Usuario usuario = this.usuarioServicio.obtener(ld.getIdUsuario());
                Artista artista = this.artistaServicio.obtenerPorUsuario(usuario);
                // ES UN ARTISTA VALIDO
                if (artista.getId()!=null){
                    Biografia bio = this.biografiaServicio.obtener(artista);
                    bio.setBiografiaBasica(biografia);
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

    @PostMapping("obtenerPosts")
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
                    alist.add(p);
                }
                log.info (String.valueOf(arrayPost.size()));

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
                ArrayList<String> nombres = new ArrayList<String>();
            for (Elemento elemento: elementos
                 ) {
                log.info( "EL: "+elemento.getRutaAcceso());
                nombres.add(elemento.getRutaAcceso());

            }

                return new ResponseEntity<ArrayList<String>> (nombres, HttpStatus.OK);


        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
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
                post.setInformacion(formulario.get("informacion").getAsString());

                //bio.addPost(post);
                this.biografiaServicio.guardar(bio);
                this.postServicio.guardar(post);

                // creo el elemento youtube
                String urlYoutube = formulario.get("youtube").getAsString();
                if (urlYoutube.length()>0){

                    Elemento video = new Elemento();
                    video.setTipoRecurso("youtube");
                    video.setPost(post);
                    video.setRutaAcceso(urlYoutube);
                    this.elementoServicio.guardar(video);
                }
                return new ResponseEntity(new Mensaje(String.valueOf(post.getId())), HttpStatus.OK);
            }else{
                return new ResponseEntity(new Mensaje("credenciales no válidas"), HttpStatus.UNAUTHORIZED);
            }




        }catch (Exception e){
            return new ResponseEntity(new Mensaje("no pude crear post"), HttpStatus.OK);
        }
    }

    @PostMapping("subirimagen")
    public ResponseEntity<?> subirimagen (@RequestParam("file") MultipartFile file, @RequestParam("id") String id){
        try{
            // Obtengo en post
            log.info ("arrancamos vamos a buscar");
            Post post = this.postServicio.obtenerPostPorId(Long.parseLong(id));

            // guardo el binario
            String pathFile = "/home/soporte/foto/" + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathFile);
            Files.write(path, bytes);
            // creo el elemento vacio
            Elemento el = new Elemento();

            el.setRutaAcceso(pathFile);
            el.setPost(post);
            el.setTipoRecurso("imglocal");
            this.elementoServicio.guardar(el);
            return new ResponseEntity(new Mensaje("Se guardaron los elementos y el post"), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(new Mensaje("No hay posts"), HttpStatus.OK);
        }
    }


}
