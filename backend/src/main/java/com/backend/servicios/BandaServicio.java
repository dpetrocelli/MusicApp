package com.backend.servicios;

import com.backend.entidades.Banda;
import com.backend.entidades.Instrumento;
import com.backend.repositorios.BandaRepositorio;
import com.backend.repositorios.InstrumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BandaServicio {

    @Autowired
    BandaRepositorio bandaRepositorio;

    public List<Banda> obtenerTodos() {
        List<Banda> lista = this.bandaRepositorio.findAll();
        return lista;
    }


}
