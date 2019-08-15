package com.backend.repositorios;

import com.backend.entidades.Comercio;
import com.backend.entidades.TokenUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenUsuarioRepositorio extends JpaRepository<TokenUsuario, Long> {
    boolean existsByIdUsuario(Long id);
    Optional<TokenUsuario> findByIdUsuario(Long id);
    boolean deleteByIdUsuario (Long id);


}
