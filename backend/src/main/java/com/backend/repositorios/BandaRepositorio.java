package com.backend.repositorios;

import com.backend.entidades.Artista;
import com.backend.entidades.Banda;
import com.backend.entidades.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface BandaRepositorio extends JpaRepository<Banda, Long> {
    Optional<Banda> findByNombre(String ni);
    Optional<ArrayList<Banda>> findAllByartistaLider(Artista artista);
    boolean existsByNombre(String ni);
    boolean existsByArtistaLider (Artista artista);

    @Query("SELECT b FROM Banda b WHERE b.nombre like %:status%")
    List<Banda> FindAllLike( @Param("status") String status);

}
