package com.backend.servicios;

import com.backend.entidades.GeneroMusical;
import com.backend.repositorios.GeneroMusicalRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GeneroMusicalServicio {

    @Autowired
    GeneroMusicalRepositorio generoMusicalRepositorio;

    public List<GeneroMusical> obtenerTodos(){
        List<GeneroMusical> generosMusicales = generoMusicalRepositorio.findAll();
        return generosMusicales;
    }

    public Optional<GeneroMusical> obtenerPorId(Long id){
        return generoMusicalRepositorio.findById(id);
    }

    public void guardar(GeneroMusical generoMusical){
        generoMusicalRepositorio.save(generoMusical);
    }

    public void borrar(Long id){
        generoMusicalRepositorio.deleteById(id);
    }

    public boolean existePorNombre(String ngm){
        return generoMusicalRepositorio.existsByNombre(ngm);
    }

    public boolean existePorId(Long id){
        return generoMusicalRepositorio.existsById(id);
    }
}
