package com.backend.servicios;

import com.backend.entidades.Biografia;
import com.backend.entidades.Instrumento;
import com.backend.entidades.Post;
import com.backend.entidades.Promocion;
import com.backend.repositorios.BiografiaRepositorio;
import com.backend.repositorios.InstrumentoRepositorio;
import com.backend.repositorios.PostRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@Transactional
public class PostServicio {


    @Autowired BiografiaRepositorio biografiaRepositorio;
    @Autowired PostRepositorio postRepositorio;
    public Optional<Biografia> obtenerPorId(Long id){
        return this.biografiaRepositorio.findById(id);
    }

    public ArrayList<Post> obtenerPosts (Biografia b){
        return this.postRepositorio.findAllByBiografia(b).get();
    }

    public Post obtenerPostPorId (Long id) {
        System.out.println(" ID PIOLA: "+id);
        Post p = this.postRepositorio.findById(id).get();
        System.out.println(" INFO "+p.getInformacion());
        return p;
        }

    public boolean guardar (Post post){
        this.postRepositorio.save(post);
        return true;
    }

    public List<Post> obtenerPostHomeSite(Integer inicio, Integer fin) {
       /* this.postRepositorio.List<User> findFirst10ByLastname(String lastname, Sort sort);

        List <Post> pag = this.postRepositorio.findAllTop10();
        TopTenByAge();*/
       return null;
    }
}
