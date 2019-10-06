package com.backend.servicios;

import com.backend.entidades.Zona;
import com.backend.repositorios.ZonaGeograficaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ZonaGeograficaServicio {

    @Autowired
    ZonaGeograficaRepositorio GeograficaRepositorio;

    public List<Zona> obtenerTodos(){
        List<Zona> list = GeograficaRepositorio.findAll();
        return list;
    }

    public Optional<Zona> obtenerPorId(Long id){
        return GeograficaRepositorio.findById(id);
    }

    public Optional<Zona> obtenerPorNombre(String ni){
        return GeograficaRepositorio.findByNombreZona(ni);
    }

    public void guardar(Zona zona){
        GeograficaRepositorio.save(zona);
    }

    public void borrar(Long id){
        GeograficaRepositorio.deleteById(id);
    }

    public boolean existePorNombre(String ni){
        return GeograficaRepositorio.existsByNombreZona(ni);
    }

    public boolean existePorId(Long id){
        return GeograficaRepositorio.existsById(id);
    }
}
