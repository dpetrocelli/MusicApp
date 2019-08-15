package com.backend.repositorios;


import com.backend.entidades.Instrumento;
import com.backend.entidades.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PromocionRepositorio extends JpaRepository<Promocion, Long> {
    Optional<ArrayList<Promocion>> findAllByComercio(Long id);
    Boolean existsByComercio(Long comercio);


}
