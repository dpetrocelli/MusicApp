package com.backend.repositorios;

import com.backend.entidades.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String nu);

    Boolean existsByUsername (String nu);

}
