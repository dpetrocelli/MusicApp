package com.backend.repositorio;

import com.backend.entidad.Instrumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstrumentoRepositorio extends JpaRepository<Instrumento, Long> {
    Optional<Instrumento> findByNombreInstrumento(String ni);
    boolean existsByNombreInstrumento(String ni);
}
