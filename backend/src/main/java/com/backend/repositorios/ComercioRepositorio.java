package com.backend.repositorios;

import com.backend.entidades.Comercio;
import com.backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComercioRepositorio extends JpaRepository<Comercio, Long> {
    boolean existsByUsuario(Usuario usuario);
    Optional<Comercio> findByUsuario(Usuario usuario);


}
