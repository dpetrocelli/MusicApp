package com.backend.repositorios;

import com.backend.entidades.Banda;
import com.backend.entidades.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BandaRepositorio extends JpaRepository<Banda, Long> {
    Optional<Banda> findByNombreBanda(String ni);
    boolean existsByNombreBanda(String ni);
}
