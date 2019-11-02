package com.backend.servicios;

import com.backend.entidades.Artista;
import com.backend.entidades.Banda;
import com.backend.entidades.Biografia;
import com.backend.entidades.BiografiaBanda;
import com.backend.repositorios.BiografiaBandaRepositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BiografiaBandaServicio {

    @Autowired
    BiografiaBandaRepositorio biografiaBandaRepositorio;

    public BiografiaBanda obtener (Banda banda) {
        if (this.biografiaBandaRepositorio.existsByBanda(banda)){
            return this.biografiaBandaRepositorio.findByBanda(banda).get();
        }else{
            return new BiografiaBanda();
        }

    }

    public boolean guardar(BiografiaBanda bio) {
        this.biografiaBandaRepositorio.save(bio);
        return true;
    }
}
