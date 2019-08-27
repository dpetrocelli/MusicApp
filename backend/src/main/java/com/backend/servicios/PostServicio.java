package com.backend.servicios;

import com.backend.entidades.Biografia;
import com.backend.entidades.Instrumento;
import com.backend.entidades.Post;
import com.backend.entidades.Promocion;
import com.backend.repositorios.BiografiaRepositorio;
import com.backend.repositorios.InstrumentoRepositorio;
import com.backend.repositorios.PostRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
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
    PostRepositorio postRepositorio;
    public Optional<Biografia> obtenerPorId(Long id){
        return this.biografiaRepositorio.findById(id);
    }

    public ArrayList<Post> obtenerPosts (Biografia b){
        return this.postRepositorio.findAllByBiografia(b).get();
    }

    public Optional <Post> obtenerPostPorId (Long id) { return this.postRepositorio.findById(id);}
    public boolean guardar (Post post){
        this.postRepositorio.save(post);
        return true;
    }
}