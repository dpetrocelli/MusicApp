package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.recursos.LoginDatos;
import com.backend.repositorios.BiografiaRepositorio;
import com.backend.repositorios.ElementoRepositorio;
import com.backend.repositorios.InstrumentoRepositorio;
import com.backend.repositorios.PostRepositorio;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@Transactional
public class PostServicio {


    @Autowired
    BiografiaRepositorio biografiaRepositorio;
    @Autowired
    PostRepositorio postRepositorio;
    @Autowired
    ElementoRepositorio elementoRepositorio;

    public Optional<Biografia> obtenerPorId(Long id) {
        return this.biografiaRepositorio.findById(id);
    }

    public ArrayList<Post> obtenerPosts(Biografia b) {
        return this.postRepositorio.findAllByBiografia(b).get();
    }

    @Autowired
    UsuarioServicio usuarioServicio;

    public Post obtenerPostPorId(Long id) {
        System.out.println(" ID PIOLA: " + id);
        Post p = this.postRepositorio.findById(id).get();
        System.out.println(" INFO " + p.getInformacion());
        return p;
    }

    public boolean guardar(Post post) {
        this.postRepositorio.save(post);
        return true;
    }

    public List<Post> obtenerPostHomeSite(Integer inicio, Integer fin) {
        //List <Post> pag = this.postRepositorio.findAllTop10();
        /* this.postRepositorio.List<User> findFirst10ByLastname(String lastname, Sort sort);


        TopTenByAge();*/
        return null;
    }

    public List<Post> obtenerTodos() {
        //return this.postRepositorio.findAll();
        List<Post> test = this.postRepositorio.findTop10ByOrderByFechaCreacionDesc();
       /* String result;
        for (Post post: test) {
            result = post.getBiografia().getArtista().getUsuario().getUsername();
        }*/
        return test;

    }

    public List<Post> buscarLike(String busqueda) {

        List<Post> test = this.postRepositorio.FindAllLike(busqueda);
        return test;
    }

    public boolean validarTokenUsuario(LoginDatos ld) {
        return this.usuarioServicio.validarTokenUsuario(ld);
    }

    public Boolean borrarPost(Post post) {
        this.postRepositorio.deleteById(post.getId());
        return true;
    }
}
