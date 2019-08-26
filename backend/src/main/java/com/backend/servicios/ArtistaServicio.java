package com.backend.servicios;

import com.backend.entidades.Artista;
import com.backend.entidades.Biografia;
import com.backend.repositorios.ArtistaRepositorio;
import com.backend.repositorios.BiografiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ArtistaServicio {

    @Autowired
    ArtistaRepositorio artistaRepositorio;

    public Artista obtener (Long id){
        return this.artistaRepositorio.findById(id).get();
    }

    public boolean guardar (Artista artista) {
        this.artistaRepositorio.save(artista);
        return true;
    }
}
