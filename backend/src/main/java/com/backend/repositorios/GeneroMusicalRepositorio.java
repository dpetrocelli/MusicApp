package com.backend.repositorios;

import com.backend.entidades.GeneroMusical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroMusicalRepositorio extends JpaRepository<GeneroMusical, Long> {
    Optional<GeneroMusical> findByNombre(String ngm);
    boolean existsByNombre(String ngm);
}
