package com.backend.servicios;

import com.backend.entidades.Artista;
import com.backend.entidades.Banda;
import com.backend.entidades.Instrumento;
import com.backend.repositorios.BandaRepositorio;
import com.backend.repositorios.InstrumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public ArrayList<Banda> obtenerBandasDeLasQueSoyAdmin (Artista artista){


            return this.bandaRepositorio.findAllByartistaLider(artista).get();


    }


    public boolean verificarSiSoyAdminDeBanda(Artista artista) {
        return this.bandaRepositorio.existsByArtistaLider(artista);
    }
}
