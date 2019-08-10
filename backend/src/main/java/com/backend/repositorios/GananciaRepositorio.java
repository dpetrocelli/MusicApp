package com.backend.repositorios;

import com.backend.entidades.MusicApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GananciaRepositorio extends JpaRepository<MusicApp, Long> {



}
