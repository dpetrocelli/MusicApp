package com.backend.repositorios;

import com.backend.entidades.PuntuacionArtista;
import com.backend.entidades.PuntuacionBanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuntuacionRepositorioBanda extends JpaRepository<PuntuacionBanda, Long> {
    List<PuntuacionBanda> findAllByBandaPuntuada(Long id);





}
