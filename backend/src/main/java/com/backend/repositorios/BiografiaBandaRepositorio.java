package com.backend.repositorios;

import com.backend.entidades.Artista;
import com.backend.entidades.Banda;
import com.backend.entidades.Biografia;
import com.backend.entidades.BiografiaBanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BiografiaBandaRepositorio extends JpaRepository<BiografiaBanda, Long> {
    boolean existsByBanda(Banda banda);
    Optional<BiografiaBanda> findByBanda(Banda banda);

}
