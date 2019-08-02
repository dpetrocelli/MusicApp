package com.backend.repositorios;

import com.backend.entidades.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstrumentoRepositorio extends JpaRepository<Instrumento, Long> {
    Optional<Instrumento> findByNombreInstrumento(String ni);
    boolean existsByNombreInstrumento(String ni);
}
