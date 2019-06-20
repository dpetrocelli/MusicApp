package com.backend.servicio;

import com.backend.entidad.Instrumento;
import com.backend.repositorio.InstrumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InstrumentoServicio {

    @Autowired
    InstrumentoRepositorio instrumentoRepositorio;

    public List<Instrumento> obtenerTodos(){
        List<Instrumento> lista = instrumentoRepositorio.findAll();
        return lista;
    }

    public Optional<Instrumento> obtenerPorId(Long id){
        return instrumentoRepositorio.findById(id);
    }

    public Optional<Instrumento> obtenerPorNombre(String ni){
        return instrumentoRepositorio.findByNombreInstrumento(ni);
    }
}
