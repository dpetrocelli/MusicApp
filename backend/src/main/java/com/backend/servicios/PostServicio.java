package com.backend.servicios;

import com.backend.entidades.Biografia;
import com.backend.entidades.Instrumento;
import com.backend.repositorios.BiografiaRepositorio;
import com.backend.repositorios.InstrumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostServicio {

    @Autowired
    BiografiaRepositorio biografiaRepositorio;

    public Optional<Biografia> obtenerPorId(Long id){
        return this.biografiaRepositorio.findById(id);
    }


}
