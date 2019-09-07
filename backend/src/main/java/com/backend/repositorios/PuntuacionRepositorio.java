package com.backend.repositorios;

import com.backend.entidades.Artista;
import com.backend.entidades.Banda;
import com.backend.entidades.PuntuacionArtista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PuntuacionRepositorio extends JpaRepository<PuntuacionArtista, Long> {
    List<PuntuacionArtista> findAllByArtistaPuntuado(Artista artista);
}
