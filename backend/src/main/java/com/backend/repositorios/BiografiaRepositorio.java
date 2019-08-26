package com.backend.repositorios;

import com.backend.entidades.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BiografiaRepositorio extends JpaRepository<Biografia, Long> {
    boolean existsByArtista(Artista artista);
    Optional<Biografia> findByArtista(Artista artista);

}
