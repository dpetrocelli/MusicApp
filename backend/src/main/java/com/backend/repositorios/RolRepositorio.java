package com.backend.repositorios;

import com.backend.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepositorio extends JpaRepository<Rol, Long> {

    Rol findByNombre(String nombre);

    Boolean existsByNombre (String nombre);



}
