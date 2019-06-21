package com.backend.servicios;

import com.backend.entidades.Instrumento;
import com.backend.repositorios.InstrumentoRepositorio;
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

    public void guardar(Instrumento instrumento){
        instrumentoRepositorio.save(instrumento);
    }

    public void borrar(Long id){
        instrumentoRepositorio.deleteById(id);
    }

    public boolean existePorNombre(String ni){
        return instrumentoRepositorio.existsByNombreInstrumento(ni);
    }

    public boolean existePorId(Long id){
        return instrumentoRepositorio.existsById(id);
    }
}
