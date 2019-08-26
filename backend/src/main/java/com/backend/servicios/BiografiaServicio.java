package com.backend.servicios;

import com.backend.entidades.Artista;
import com.backend.entidades.Biografia;
import com.backend.repositorios.ArtistaRepositorio;
import com.backend.repositorios.BiografiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BiografiaServicio {

    @Autowired
    BiografiaRepositorio biografiaRepositorio;

    public Biografia obtener (Artista artista) {
        if (this.biografiaRepositorio.existsByArtista(artista)){
            return this.biografiaRepositorio.findByArtista(artista).get();
        }else{
            return new Biografia();
        }

    }

    public boolean guardar(Biografia bio) {
        this.biografiaRepositorio.save(bio);
        return true;
    }
}
