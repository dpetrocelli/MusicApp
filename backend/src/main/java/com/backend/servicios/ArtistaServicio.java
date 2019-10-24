package com.backend.servicios;

import com.backend.entidades.*;
import com.backend.repositorios.ArtistaRepositorio;
import com.backend.repositorios.BiografiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArtistaServicio {

    @Autowired
    ArtistaRepositorio artistaRepositorio;

    @Autowired
    ZonaGeograficaServicio zonaServicio;

    public Artista obtener (Long id){
        return this.artistaRepositorio.findById(id).get();
    }

    public Artista obtenerPorUsuario (Usuario usuario) {
        if (this.artistaRepositorio.existsByUsuario(usuario)){
            return this.artistaRepositorio.findByUsuario(usuario).get();
        }else{
            return new Artista();
        }

    }

    public Artista obtenerPorNombre (String nombre) {return this.artistaRepositorio.findByNombre(nombre);}

    public List<Artista> obtenerTodos (){
        return this.artistaRepositorio.findAll();
    }
    public boolean guardar (Artista artista) {
        this.artistaRepositorio.save(artista);
        return true;
    }


    public List<Artista> buscarLike(String busqueda, String zona, String instrumento, String genero) {
        //if (zona!=null){
           //Zona zonex =  this.zonaServicio.obtenerPorNombre(zona).get();
           List<Artista> test =  this.artistaRepositorio.FindAllLike(busqueda, genero);
          //  for (Artista art: test) {
             //   art.ge
           // }
           return test;
        //}


    }


}
