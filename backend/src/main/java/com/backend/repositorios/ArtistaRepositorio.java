package com.backend.repositorios;

import com.backend.entidades.Artista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArtistaRepositorio extends JpaRepository<Artista, Long> {
	
}
