package com.backend.repositorios;

import com.backend.entidades.Comercio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComercioRepositorio extends JpaRepository<Comercio, Long> {
    boolean existsByIdComercio(Long id);
    Optional<Comercio> findByIdComercio(Long id);


}
