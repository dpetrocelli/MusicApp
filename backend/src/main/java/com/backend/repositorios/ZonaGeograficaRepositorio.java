package com.backend.repositorios;

import com.backend.entidades.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZonaGeograficaRepositorio extends JpaRepository<Zona, Long> {
    Optional<Zona> findByNombreZona(String ni);
    boolean existsByNombreZona(String ni);
}
