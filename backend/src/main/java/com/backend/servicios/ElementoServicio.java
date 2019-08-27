package com.backend.servicios;

import com.backend.entidades.Elemento;
import com.backend.entidades.Instrumento;
import com.backend.entidades.Post;
import com.backend.repositorios.ElementoRepositorio;
import com.backend.repositorios.InstrumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ElementoServicio {

    @Autowired
    ElementoRepositorio elementoRepositorio;

    public List<Elemento> obtenerTodos(Post post){
        List<Elemento> elementos = elementoRepositorio.findAllByPost(post);
        return elementos;
    }

    public Optional<Elemento> obtenerPorId(Long id){
        return this.elementoRepositorio.findById(id);
    }

    public void guardar(Elemento elemento){
        this.elementoRepositorio.save(elemento);
    }

    public void borrar(Long id){
        this.elementoRepositorio.deleteById(id);
    }

}
